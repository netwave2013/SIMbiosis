package org.simbiosis.ui.system.user.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class UserManagement implements EntryPoint {
	UserManagementEntryPoint appEntryPoint = new UserManagementEntryPoint();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		appEntryPoint.start();
	}

}
