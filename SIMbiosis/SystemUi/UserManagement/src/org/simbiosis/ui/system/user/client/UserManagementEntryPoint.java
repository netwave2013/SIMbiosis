package org.simbiosis.ui.system.user.client;

import org.kembang.module.client.mvp.KembangEntryPoint;

import com.google.gwt.core.client.GWT;

public class UserManagementEntryPoint extends KembangEntryPoint {

	public UserManagementEntryPoint() {
		super();
	}

	@Override
	public void initComponent() {
		UserManagementFactory clientFactory = GWT.create(UserManagementFactory.class);
		UserManagementHistoryMapper historyMapper = GWT
				.create(UserManagementHistoryMapper.class);
		setHistoryMapper(historyMapper);
		setClientFactory(clientFactory);
		setActivityMapper(new UserManagementActivityMapper(clientFactory));
	}

}
