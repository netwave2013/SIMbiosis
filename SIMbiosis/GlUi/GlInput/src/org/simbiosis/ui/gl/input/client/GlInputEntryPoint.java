package org.simbiosis.ui.gl.input.client;

import org.kembang.module.client.mvp.KembangEntryPoint;

import com.google.gwt.core.client.GWT;

public class GlInputEntryPoint extends KembangEntryPoint {

	public GlInputEntryPoint() {
		super();
	}

	@Override
	public void initComponent() {
		GlInputFactory clientFactory = GWT.create(GlInputFactory.class);
		GlInputHistoryMapper historyMapper = GWT.create(GlInputHistoryMapper.class);
		setHistoryMapper(historyMapper);
		setClientFactory(clientFactory);
		setActivityMapper(new GlInputActivityMapper(clientFactory));
	}

}
