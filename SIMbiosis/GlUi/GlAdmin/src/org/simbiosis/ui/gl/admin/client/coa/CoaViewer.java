package org.simbiosis.ui.gl.admin.client.coa;

import org.simbiosis.ui.gl.admin.shared.CoaDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class CoaViewer extends Composite implements Editor<CoaDv> {

	private static CoaEditorUiBinder uiBinder = GWT
			.create(CoaEditorUiBinder.class);

	interface CoaEditorUiBinder extends UiBinder<Widget, CoaViewer> {
	}

	interface Driver extends SimpleBeanEditorDriver<CoaDv, CoaViewer> {
	}

	static Driver driver = GWT.create(Driver.class);

	@UiField
	Label code;
	@UiField
	Label prefix;
	@UiField
	Label description;
	@UiField
	Label parentCodeDescription;
	@UiField
	Label strBranch;
	@UiField
	Label strExcBranch;
	
	public CoaViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
		driver.edit(new CoaDv());
	}

	public CoaViewer edit(CoaDv coaDv) {
		driver.edit(coaDv);
		return this;
	}
	
}
