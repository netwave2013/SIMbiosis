package org.simbiosis.ui.system.user.client.role;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.system.user.shared.RoleDv;

public interface IRoleList {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setRoles(List<RoleDv> roles);

	void editSelected();
	
	void newRole();
	
	void viewSelected();
	
	void clearViewer();
	
	RoleDv getRole();

	public abstract class Activity extends FormActivity {
	}
}
