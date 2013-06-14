package org.simbiosis.ui.system.user.client.user;

import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormActivity;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.BranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.ui.system.user.shared.RoleDv;

public interface IUserList {
	void setActivity(Activity activity, AppStatus appStatus);

	FormWidget getFormWidget();

	void setBranches(List<BranchDv> branches);

	void setRoles(List<RoleDv> roles);

	void setUsers(List<UserDv> users);

	void editSelected();
	
	void newUser();

	void viewSelected();
	
	void clearViewer();

	UserDv getUser();

	public abstract class Activity extends FormActivity {
	}

}
