package org.simbiosis.ui.gl.admin.client.coa;

import java.util.List;
import java.util.Map;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.gl.admin.shared.BranchDv;
import org.simbiosis.ui.gl.admin.shared.CoaDv;

import com.google.gwt.view.client.ListDataProvider;

public interface ICoaListForm {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void clearView();

	void showBackButton(boolean show);

	void editData();

	void viewData();

	void newData();

	void saveData(CoaDv coaDv, boolean isNew);

	void removeData(CoaDv coaDv);

	CoaDv getData();

	CoaDv getSelectedData();

	void initEditorWidget();

	List<BranchDv> getListBranch();

	void setListBranch(List<BranchDv> listBranch);

	public abstract class Activity extends FormActivity {
		public abstract void loadBranch();

		public abstract void listRootCoa(
				final ListDataProvider<CoaDv> dataProvider, List<CoaDv> listCoa);

		@SuppressWarnings("rawtypes")
		public abstract void listCoaByParent(Long parent,
				final ListDataProvider<CoaDv> dataProvider, final Map nodeMap);

		public abstract void listCoaByType(Integer type,
				final List<CoaDv> listCoa);
	}

}
