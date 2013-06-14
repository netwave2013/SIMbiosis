package org.simbiosis.ui.gl.admin.client;

import org.kembang.module.client.mvp.KembangEntryPoint;

import com.google.gwt.core.client.GWT;

public class GlAdminEntryPoint extends KembangEntryPoint {

	public GlAdminEntryPoint() {
		super();
	}

	@Override
	public void initComponent() {
		GlAdminFactory clientFactory = GWT.create(GlAdminFactory.class);
		GlAdminHistoryMapper historyMapper = GWT
				.create(GlAdminHistoryMapper.class);
		setHistoryMapper(historyMapper);
		setClientFactory(clientFactory);
		setActivityMapper(new GlAdminActivityMapper(clientFactory));
	}

}
