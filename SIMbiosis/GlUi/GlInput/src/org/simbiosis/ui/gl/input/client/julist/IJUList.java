package org.simbiosis.ui.gl.input.client.julist;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.gl.input.shared.FindTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;

public interface IJUList {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	public void searchTransaction();

	public void showSearchResult(FindTransDv data);

	public GLTransDv getSelectedData();

	public Long removeSelectedData();

	public void removeChecked();

	public abstract class Activity extends FormActivity {
		abstract void initViewerEditor();

		abstract void searchTransaction(Date begin, Date end, int status);

		abstract void release(List<GLTransDv> data);
	}
}
