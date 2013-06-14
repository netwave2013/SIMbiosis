package org.simbiosis.ui.system.user.server;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.kembang.module.shared.BranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.RoleDto;
import org.simbiosis.system.UserDto;
import org.simbiosis.ui.system.user.client.rpc.Service;
import org.simbiosis.ui.system.user.shared.RoleDv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ServiceImpl extends RemoteServiceServlet implements Service {

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp iSystem;

	public ServiceImpl() {
	}

	@Override
	public List<UserDv> listUsers(String key) throws IllegalArgumentException {
		List<UserDv> result = new ArrayList<UserDv>();
		List<UserDto> users = iSystem.listUsers(key);
		int nr = 1;
		for (UserDto user : users) {
			UserDv dv = new UserDv();
			dv.setNr(nr++);
			dv.setId(user.getId());
			dv.setBranch(user.getBranch());
			if (dv.getBranch() == 0) {
				dv.setBranchName("SEMUA CABANG");
			} else {
				dv.setBranchName(user.getBranchName());
			}
			dv.setName(user.getName());
			dv.setRealName(user.getRealName());
			dv.setEmail(user.getEmail());
			dv.setRole(user.getRole());
			dv.setRoleName(user.getRoleName());
			dv.setActive(user.getActive() == 1);
			dv.setStrActive(user.getActive() == 1 ? "AKTIF" : "NON AKTIF");
			dv.setChangePassword(false);
			dv.setPassword("");
			dv.setConfirmPassword("");
			result.add(dv);
		}
		return result;
	}

	@Override
	public List<RoleDv> listRoles(String key) throws IllegalArgumentException {
		List<RoleDv> result = new ArrayList<RoleDv>();
		List<RoleDto> users = iSystem.listRoles(key);
		int nr = 1;
		for (RoleDto user : users) {
			RoleDv dv = new RoleDv();
			dv.setNr(nr++);
			dv.setId(user.getId());
			dv.setName(user.getName());
			dv.setDescription(user.getDescription());
			result.add(dv);
		}
		return result;
	}

	@Override
	public void saveUser(String key, UserDv dv) throws IllegalArgumentException {
		UserDto dto = new UserDto();
		dto.setId(dv.getId());
		dto.setBranch(dv.getBranch());
		dto.setName(dv.getName());
		dto.setRealName(dv.getRealName());
		dto.setEmail(dv.getEmail());
		dto.setRole(dv.getRole());
		dto.setActive(dv.isActive() ? 1 : 0);
		dto.setChangePassword(dv.isChangePassword());
		dto.setPassword(dv.getPassword());
		iSystem.saveUser(key, dto);
	}

	@Override
	public List<BranchDv> listBranches(String key)
			throws IllegalArgumentException {
		List<BranchDv> result = new ArrayList<BranchDv>();
		List<BranchDto> branches = iSystem.listBranch(key);
		for (BranchDto branch : branches) {
			BranchDv dv = new BranchDv();
			dv.setId(branch.getId());
			dv.setName(branch.getName());
			result.add(dv);
		}
		return result;
	}

	@Override
	public void saveRole(String key, RoleDv role)
			throws IllegalArgumentException {
		RoleDto dto = new RoleDto();
		dto.setId(role.getId());
		dto.setName(role.getName());
		dto.setDescription(role.getDescription());
		iSystem.saveRole(key, dto);
	}

}
