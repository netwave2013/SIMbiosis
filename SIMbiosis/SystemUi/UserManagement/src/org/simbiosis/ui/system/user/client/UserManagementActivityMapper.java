package org.simbiosis.ui.system.user.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.system.user.client.role.RoleListActivity;
import org.simbiosis.ui.system.user.client.role.RoleListPlace;
import org.simbiosis.ui.system.user.client.user.UserListActivity;
import org.simbiosis.ui.system.user.client.user.UserListPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class UserManagementActivityMapper extends KembangActivityMapper {

	public UserManagementActivityMapper(UserManagementFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		UserManagementFactory clientFactory = (UserManagementFactory) getClientFactory();
		if (place instanceof UserListPlace) {
			return new UserListActivity((UserListPlace) place, clientFactory);
		} else if (place instanceof RoleListPlace) {
			return new RoleListActivity((RoleListPlace) place, clientFactory);
		}
		return null;
	}

}
