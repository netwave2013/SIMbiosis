package org.simbiosis.ui.system.user.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.system.user.client.role.IRoleList;
import org.simbiosis.ui.system.user.client.role.RoleList;
import org.simbiosis.ui.system.user.client.user.IUserList;
import org.simbiosis.ui.system.user.client.user.UserList;

public class UserManagementFactoryImpl extends KembangClientFactoryImpl
		implements UserManagementFactory {

	static final UserList USER_EDITOR = new UserList();
	static final RoleList ROLE_LIST = new RoleList();

	@Override
	public IUserList getUserEditor() {
		return USER_EDITOR;
	}

	@Override
	public IRoleList getRoleEditor() {
		return ROLE_LIST;
	}

}
