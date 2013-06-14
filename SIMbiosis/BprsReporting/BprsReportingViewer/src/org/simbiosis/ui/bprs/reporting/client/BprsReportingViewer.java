package org.simbiosis.ui.bprs.reporting.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class BprsReportingViewer implements EntryPoint {
	BprsReportingEntryPoint appEntryPoint = new BprsReportingEntryPoint();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//
		Resources.INSTANCE.css().ensureInjected();
		//
		appEntryPoint.start();
	}
}
