package org.simbiosis.ui.system.user.client.rpc;

import java.util.List;

import org.kembang.module.shared.BranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.ui.system.user.shared.RoleDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ServiceAsync {

	void listRoles(String key, AsyncCallback<List<RoleDv>> callback);

	void listUsers(String key, AsyncCallback<List<UserDv>> callback);

	void saveUser(String key, UserDv user, AsyncCallback<Void> callback);

	void listBranches(String key, AsyncCallback<List<BranchDv>> callback);

	void saveRole(String key, RoleDv user, AsyncCallback<Void> callback);

}
