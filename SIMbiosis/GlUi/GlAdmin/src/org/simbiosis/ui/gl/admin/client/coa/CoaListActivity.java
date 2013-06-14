package org.simbiosis.ui.gl.admin.client.coa;

import java.util.List;
import java.util.Map;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.gl.admin.client.GlAdminFactory;
import org.simbiosis.ui.gl.admin.client.coa.ICoaListForm.Activity;
import org.simbiosis.ui.gl.admin.client.rpc.GlService;
import org.simbiosis.ui.gl.admin.client.rpc.GlServiceAsync;
import org.simbiosis.ui.gl.admin.shared.BranchDv;
import org.simbiosis.ui.gl.admin.shared.CoaDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.web.bindery.event.shared.EventBus;

public class CoaListActivity extends Activity {

	private final GlServiceAsync glService = GWT.create(GlService.class);

	GlAdminFactory appFactory;
	CoaListPlace place;

	public CoaListActivity(CoaListPlace place, GlAdminFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ICoaListForm coaListForm = appFactory.getCoaListForm();
		coaListForm.clearView();
		coaListForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, coaListForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_NEW:
			onNew();
			break;
		case FA_EDIT:
			onEdit();
			break;
		case FA_SAVE:
			onSave();
			break;
		case FA_DELETE:
			onDelete();
			break;
		case FA_BACK:
			onBack();
			break;
		default:
			break;
		}
	}

	private void onBack() {
		ICoaListForm coaListForm = appFactory.getCoaListForm();
		coaListForm.showBackButton(false);
		coaListForm.viewData();
	}

	void onNew() {
		appFactory.getCoaListForm().newData();
	}

	void onEdit() {
		ICoaListForm coaListForm = appFactory.getCoaListForm();
		coaListForm.showBackButton(true);
		appFactory.getCoaListForm().editData();
	}

	void onSave() {
		showLoading();
		CoaDv coa = appFactory.getCoaListForm().getData();
		final boolean isNew = (coa.getId() == 0);
		glService.saveCoa(getKey(), coa, new AsyncCallback<CoaDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : saveCoa");
			}

			@Override
			public void onSuccess(CoaDv result) {
				appFactory.getCoaListForm().saveData(result, isNew);
				hideLoading();
				Window.alert("Data Coa sudah disimpan");
			}
		});
	}

	void onDelete() {
		showLoading();
		final CoaDv coaRemoved = appFactory.getCoaListForm().getSelectedData();
		glService.removeCoa(coaRemoved.getId(), new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : removeCoa");
			}

			@Override
			public void onSuccess(Void result) {
				appFactory.getCoaListForm().removeData(coaRemoved);
				hideLoading();
				Window.alert("Data sudah dihapus");
			}
		});
	}

	@Override
	public void listRootCoa(final ListDataProvider<CoaDv> dataProvider,
			final List<CoaDv> listCoa) {
		showLoading();
		glService.listCoaByParent(getKey(), 0L,
				new AsyncCallback<List<CoaDv>>() {

					@Override
					public void onSuccess(List<CoaDv> result) {
						for (CoaDv coa : result) {
							dataProvider.getList().add(coa);
						}
						listCoaByType(1, listCoa);
						//
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listCoaByParent");
					}
				});
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void listCoaByParent(Long parent,
			final ListDataProvider<CoaDv> dataProvider, final Map nodeMap) {
		showLoading();
		glService.listCoaByParent(getKey(), parent,
				new AsyncCallback<List<CoaDv>>() {

					@SuppressWarnings("unchecked")
					@Override
					public void onSuccess(List<CoaDv> result) {
						for (CoaDv coa : result) {
							ListDataProvider<CoaDv> exist = (ListDataProvider<CoaDv>) nodeMap
									.get(coa.getParent());
							if (exist == null) {
								nodeMap.put(coa.getParent(), dataProvider);
							}
							dataProvider.getList().add(coa);
						}
						hideLoading();
					}

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listCoaByParent");
					}
				});
	}

	@Override
	public void listCoaByType(Integer type, final List<CoaDv> listCoa) {
		showLoading();
		glService.listCoaByType(getKey(), type,
				new AsyncCallback<List<CoaDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listCoaByType");
					}

					@Override
					public void onSuccess(List<CoaDv> result) {
						listCoa.clear();
						listCoa.addAll(result);
						appFactory.getCoaListForm().initEditorWidget();
						hideLoading();
					}
				});
	}

	@Override
	public void loadBranch() {
		glService.listBranch(getKey(), new AsyncCallback<List<BranchDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listBranch");
			}

			@Override
			public void onSuccess(List<BranchDv> result) {
				appFactory.getCoaListForm().setListBranch(result);
			}
		});
	}
}
