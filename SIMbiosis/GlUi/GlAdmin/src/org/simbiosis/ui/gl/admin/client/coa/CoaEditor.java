package org.simbiosis.ui.gl.admin.client.coa;

import java.util.List;

import org.simbiosis.ui.gl.admin.client.editor.BranchListEditor;
import org.simbiosis.ui.gl.admin.client.editor.CoaListEditor;
import org.simbiosis.ui.gl.admin.shared.BranchDv;
import org.simbiosis.ui.gl.admin.shared.CoaDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class CoaEditor extends Composite implements Editor<CoaDv> {

	private static CoaEditorWriterUiBinder uiBinder = GWT
			.create(CoaEditorWriterUiBinder.class);

	interface CoaEditorWriterUiBinder extends UiBinder<Widget, CoaEditor> {
	}

	interface Driver extends SimpleBeanEditorDriver<CoaDv, CoaEditor> {
	}

	static Driver driver = GWT.create(Driver.class);

	@UiField
	TextBox code;
	@UiField
	TextBox prefix;
	@UiField
	TextBox description;
	@UiField
	CoaListEditor parent;
	@UiField
	BranchListEditor branch;
	@UiField
	BranchListEditor excBranch;

	public CoaEditor(List<CoaDv> listData) {
		initWidget(uiBinder.createAndBindUi(this));
		driver.initialize(this);
		//
		parent.setList(listData);
		driver.edit(new CoaDv());
	}

	public CoaEditor edit(CoaDv coaDv) {
		driver.edit(coaDv);
		return this;
	}

	public CoaDv flush() {
		return driver.flush();
	}

	public void updateParent(List<CoaDv> listData) {
		parent.setList(listData);
	}
	
	public void setListBranch(List<BranchDv> listBranch){
		branch.setList(listBranch);
		excBranch.setList(listBranch);
	}
}
