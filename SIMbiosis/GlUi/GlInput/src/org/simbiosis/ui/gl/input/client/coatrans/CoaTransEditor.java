package org.simbiosis.ui.gl.input.client.coatrans;

import org.simbiosis.ui.gl.input.client.editor.GLTransItemViewerExt;
import org.simbiosis.ui.gl.input.shared.FindTransItemDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class CoaTransEditor extends Composite implements
		Editor<FindTransItemDv> {

	private static CoaTransEditorUiBinder uiBinder = GWT
			.create(CoaTransEditorUiBinder.class);

	interface CoaTransEditorUiBinder extends UiBinder<Widget, CoaTransEditor> {
	}

	interface Driver extends
			SimpleBeanEditorDriver<FindTransItemDv, CoaTransEditor> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	GLTransItemViewerExt items;

	public CoaTransEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
		driver.edit(new FindTransItemDv());
	}

	public void showData(FindTransItemDv data) {
		driver.edit(data);
	}

}
