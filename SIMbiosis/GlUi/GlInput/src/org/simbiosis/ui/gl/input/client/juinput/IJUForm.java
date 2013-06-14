package org.simbiosis.ui.gl.input.client.juinput;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;

public interface IJUForm {
	void setActivity(Activity activity,AppStatus appStatus);

	FormWidget getFormWidget();

	void editData(GLTransDv data);
	
	GLTransDv getData();

	void setListCoa(List<CoaDv> listCoa);
	
	boolean isDCEqual();
	
	public abstract class Activity extends FormActivity {
		abstract void loadCoa();
	}
}
