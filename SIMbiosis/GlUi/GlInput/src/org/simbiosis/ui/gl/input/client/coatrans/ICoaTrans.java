package org.simbiosis.ui.gl.input.client.coatrans;

import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.FindTransItemDv;

public interface ICoaTrans {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	public void showSearchResult(FindTransItemDv data);

	void setListCoa(List<CoaDv> listCoa);

	//
	public Date getBeginDate();

	public Date getEndDate();

	public long getCoa();

	public abstract class Activity extends FormActivity {
		abstract void loadCoa();
	}
}
