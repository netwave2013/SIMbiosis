package org.simbiosis.ui.gl.input.client;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class GlInput implements EntryPoint {
	GlInputEntryPoint appEntryPoint = new GlInputEntryPoint();

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		appEntryPoint.start();
	}

}
