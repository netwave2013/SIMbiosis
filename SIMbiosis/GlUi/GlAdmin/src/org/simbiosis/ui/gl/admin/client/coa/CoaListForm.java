package org.simbiosis.ui.gl.admin.client.coa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.gl.admin.shared.BranchDv;
import org.simbiosis.ui.gl.admin.shared.CoaDv;

import com.google.gwt.cell.client.AbstractCell;
import com.google.gwt.cell.client.Cell;
import com.google.gwt.core.client.GWT;
import com.google.gwt.safehtml.shared.SafeHtmlBuilder;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.cellview.client.CellTree;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.SelectionChangeEvent;
import com.google.gwt.view.client.SingleSelectionModel;
import com.google.gwt.view.client.TreeViewModel;

public class CoaListForm extends FormWidget implements ICoaListForm {

	Activity activity = null;
	boolean initialized = false;

	private static CoaListFormImplUiBinder uiBinder = GWT
			.create(CoaListFormImplUiBinder.class);

	interface CoaListFormImplUiBinder extends UiBinder<Widget, CoaListForm> {
	}

	@UiField(provided = true)
	CellTree listTable;

	@UiField
	VerticalPanel coaEditor;

	CoaDv dataSelected = null;
	List<CoaDv> listCoa = new ArrayList<CoaDv>();

	List<BranchDv> listBranch = new ArrayList<BranchDv>();

	CoaViewer coaViewerWidget;
	CoaEditor coaEditorWidget;
	Map<Long, ListDataProvider<CoaDv>> nodeMap = new HashMap<Long, ListDataProvider<CoaDv>>();

	private class CoaTreeModel implements TreeViewModel {

		SingleSelectionModel<CoaDv> selectionModel = new SingleSelectionModel<CoaDv>();
		Long parent = 0L;

		public CoaTreeModel() {
			//
			selectionModel
					.addSelectionChangeHandler(new SelectionChangeEvent.Handler() {

						@Override
						public void onSelectionChange(SelectionChangeEvent event) {
							dataSelected = selectionModel.getSelectedObject();
							coaEditor.clear();
							if (dataSelected.getLevel() > 0) {
								viewData();
							}
						}
					});

		}

		/**
		 * Get the {@link NodeInfo} that provides the children of the specified
		 * value.
		 */
		public <T> NodeInfo<?> getNodeInfo(T value) {
			ListDataProvider<CoaDv> dataProvider = new ListDataProvider<CoaDv>();
			if (activity == null) {
				Cell<CoaDv> cell = new AbstractCell<CoaDv>() {

					@Override
					public void render(Context context, CoaDv value,
							SafeHtmlBuilder sb) {
						if (!initialized) {
							sb.appendEscaped("No COA");
						} else {
							sb.appendEscaped(value.getCode() + " - "
									+ value.getDescription());
						}
					}
				};

				CoaDv coa = new CoaDv();
				coa.setId(0L);
				coa.setParent(0L);
				nodeMap.put(coa.getParent(), dataProvider);
				dataProvider.getList().add(coa);
				// Return a node info that pairs the data with a cell.
				return new DefaultNodeInfo<CoaDv>(dataProvider, cell,
						selectionModel, null);
			} else {
				Cell<CoaDv> cell = new AbstractCell<CoaDv>() {

					@Override
					public void render(Context context, CoaDv value,
							SafeHtmlBuilder sb) {
						sb.appendEscaped(value.getCode() + " - "
								+ value.getDescription());
					}
				};
				activity.listCoaByParent(parent, dataProvider, nodeMap);
				return new DefaultNodeInfo<CoaDv>(dataProvider, cell,
						selectionModel, null);
			}
		}

		/**
		 * Check if the specified value represents a leaf node. Leaf nodes
		 * cannot be opened.
		 */
		public boolean isLeaf(Object value) {
			if (value == null)
				return false;
			CoaDv coa = (CoaDv) value;
			parent = coa.getId();
			return !coa.isHasChildren();
		}
	}

	public CoaListForm() {
		listTable = new CellTree(new CoaTreeModel(), null);
		//
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasNew(true);
		setHasEdit(true);
		setHasSave(true);
		setHasDelete(true);
		setHasBack(false);
		//
		coaViewerWidget = null;
		coaEditorWidget = null;

	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		//
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		//
		if (appStatus.isLogin()) {
			//
			activity.loadBranch();
			//
			ListDataProvider<CoaDv> dataProvider = nodeMap.get(0L);
			activity.listRootCoa(dataProvider, listCoa);
		}
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void clearView() {
		ListDataProvider<CoaDv> dataProvider = nodeMap.get(0L);
		nodeMap.clear();
		nodeMap.put(0L, dataProvider);
		dataProvider.getList().clear();
		initialized = true;
		dataProvider.refresh();
	}

	@Override
	public CoaDv getData() {
		return coaEditorWidget.flush();
	}

	@Override
	public void editData() {
		if (dataSelected.getLevel() > 0) {
			coaEditor.clear();
			coaEditor.add(coaEditorWidget.edit(dataSelected));
		}
	}

	@Override
	public void initEditorWidget() {
		if (coaViewerWidget == null) {
			coaViewerWidget = new CoaViewer();
		}
		if (coaEditorWidget == null) {
			coaEditorWidget = new CoaEditor(listCoa);
			coaEditorWidget.setListBranch(getListBranch());
		}
	}

	@Override
	public void newData() {
		if (dataSelected != null) {
			if (dataSelected.getLevel() < 3) {
				CoaDv newCoa = new CoaDv();
				//
				String parentCode = new String("");
				CoaDv parentDv = getCoaById(dataSelected.getId());
				if (parentDv != null) {
					parentCode = parentDv.getCode();
					String codes[] = parentCode.split("\\.");
					parentCode = "";
					for (int i = 0; i < parentDv.getLevel(); i++) {
						parentCode += codes[i] + ".";
					}
				}
				//
				newCoa.setId(0L);
				newCoa.setCode(parentCode);
				newCoa.setDescription("");
				newCoa.setParent(dataSelected.getId());
				newCoa.setBranch(0);
				//
				coaEditor.clear();
				coaEditor.add(coaEditorWidget.edit(newCoa));
			} else {
				Window.alert("Coa level paling bawah tidak dapat memiliki anak");
			}
		}
	}

	@Override
	public CoaDv getSelectedData() {
		return dataSelected;
	}

	@Override
	public void saveData(CoaDv coaDv, boolean isNew) {
		//
		ListDataProvider<CoaDv> dataProvider = nodeMap.get(coaDv.getParent());
		if (isNew) {
			// Kalo data baru maka
			if (dataProvider == null) {
				dataProvider = nodeMap.get(coaDv.getGrandParent());
				//
				Iterator<CoaDv> iterCoa = dataProvider.getList().iterator();
				boolean found = false;
				while (iterCoa.hasNext() && !found) {
					CoaDv coa = iterCoa.next();
					if (coa.getId() == coaDv.getParent()) {
						found = true;
						coa.setHasChildren(true);
					}
				}
			} else {
				dataProvider.getList().add(coaDv);
			}
			if (coaDv.getLevel() < 3) {
				listCoa.add(coaDv);
			}
		} else {
			// Kalo data lama maka
			CoaDv coa = getCoaById(coaDv.getId());
			if (coa != null) {
				coa.setCode(coaDv.getCode());
				coa.setDescription(coaDv.getDescription());
				coa.setBranch(coaDv.getBranch());
				coa.setStrBranch(coaDv.getStrBranch());
			}
			// Perbiki tree
			ListDataProvider<CoaDv> childDataProvider = nodeMap.get(coaDv
					.getId());
			if (childDataProvider != null) {
				Iterator<CoaDv> iterChildCoa = childDataProvider.getList()
						.iterator();
				while (iterChildCoa.hasNext()) {
					CoaDv child = iterChildCoa.next();
					child.setParentCodeDescription(coaDv.getCode() + " - "
							+ coaDv.getDescription());
				}
			}
			boolean found = false;
			Iterator<CoaDv> iterParentCoa = dataProvider.getList().iterator();
			while (iterParentCoa.hasNext() && !found) {
				CoaDv child = iterParentCoa.next();
				if (child.getId() == coaDv.getId()) {
					found = true;
					child.setBranch(coaDv.getBranch());
					child.setStrBranch(coaDv.getStrBranch());
				}
			}
		}
		coaEditorWidget.updateParent(listCoa);
		dataProvider.refresh();
		// Update tampilan editor
		coaEditor.clear();
		coaEditor.add(coaViewerWidget.edit(coaDv));
	}

	@Override
	public void removeData(CoaDv coaDv) {
		ListDataProvider<CoaDv> dataProvider = nodeMap.get(coaDv.getParent());
		Iterator<CoaDv> iterCoa = dataProvider.getList().iterator();
		int index = 0;
		boolean coaFound = false;
		while (iterCoa.hasNext() && !coaFound) {
			CoaDv coa = iterCoa.next();
			if (coa.getId() == coaDv.getId()) {
				coaFound = true;
			} else {
				index++;
			}
		}
		if (coaFound) {
			dataProvider.getList().remove(index);
			dataProvider.refresh();
		}
	}

	private CoaDv getCoaById(long id) {
		Iterator<CoaDv> iter = listCoa.iterator();
		while (iter.hasNext()) {
			CoaDv parent = iter.next();
			if (parent.getId() == id) {
				return parent;
			}
		}
		return null;
	}

	@Override
	public void showBackButton(boolean show) {
		showBack(show);
	}

	@Override
	public void viewData() {
		coaEditor.clear();
		coaEditor.add(coaViewerWidget.edit(dataSelected));
	}

	@Override
	public List<BranchDv> getListBranch() {
		return listBranch;
	}

	@Override
	public void setListBranch(List<BranchDv> listBranch) {
		this.listBranch.clear();
		this.listBranch.addAll(listBranch);
	}
}
