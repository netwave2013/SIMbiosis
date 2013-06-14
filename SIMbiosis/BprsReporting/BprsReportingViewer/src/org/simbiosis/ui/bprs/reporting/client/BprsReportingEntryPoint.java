package org.simbiosis.ui.bprs.reporting.client;

import org.kembang.module.client.mvp.KembangEntryPoint;

import com.google.gwt.core.client.GWT;

public class BprsReportingEntryPoint extends KembangEntryPoint {

	public BprsReportingEntryPoint() {
		super();
	}

	@Override
	public void initComponent() {
		BprsReportingViewerFactory clientFactory = GWT.create(BprsReportingViewerFactory.class);
		BprsReportingViewerHistoryMapper historyMapper = GWT.create(BprsReportingViewerHistoryMapper.class);
		setHistoryMapper(historyMapper);
		setClientFactory(clientFactory);
		setActivityMapper(new BprsReportingViewerActivityMapper(clientFactory));
	}

}
