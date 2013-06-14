package org.simbiosis.ui.system.user.client.rpc;

import java.util.List;

import org.kembang.module.shared.BranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.ui.system.user.shared.RoleDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("service")
public interface Service extends RemoteService {

	List<BranchDv> listBranches(String key) throws IllegalArgumentException;

	List<RoleDv> listRoles(String key) throws IllegalArgumentException;

	List<UserDv> listUsers(String key) throws IllegalArgumentException;

	void saveUser(String key, UserDv user) throws IllegalArgumentException;

	void saveRole(String key, RoleDv user) throws IllegalArgumentException;

}
