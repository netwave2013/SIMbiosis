package org.simbiosis.ui.system.user.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.system.user.client.role.IRoleList;
import org.simbiosis.ui.system.user.client.user.IUserList;

public interface UserManagementFactory extends KembangClientFactory {
	IUserList getUserEditor();

	IRoleList getRoleEditor();
}
