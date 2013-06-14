package org.simbiosis.ui.gl.input.client.postlist;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.gl.input.shared.FindTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;

public interface IPost {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void searchTransaction();

	void showSearchResult(FindTransDv data);

	void removeChecked();

	public abstract class Activity extends FormActivity {
		abstract void searchTransaction(Date beginDate, Date endDate, Integer status);

		abstract void unRelease(List<GLTransDv> data);

		abstract void unPost(List<GLTransDv> data);

		abstract void post(List<GLTransDv> data);
	}

}
