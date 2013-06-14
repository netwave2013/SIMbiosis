package org.simbiosis.ui.gl.input.client.julist;

import java.util.List;

import org.simbiosis.ui.gl.input.client.editor.GLTransListTable;
import org.simbiosis.ui.gl.input.shared.FindTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class JUListEditor extends Composite implements Editor<FindTransDv> {

	private static JUListEditorUiBinder uiBinder = GWT
			.create(JUListEditorUiBinder.class);

	interface JUListEditorUiBinder extends UiBinder<Widget, JUListEditor> {
	}

	interface Driver extends SimpleBeanEditorDriver<FindTransDv, JUListEditor> {
	}

	private Driver editorDriver = GWT.create(Driver.class);

	@UiField
	GLTransListTable resultTable;

	public JUListEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		editorDriver.initialize(this);
		editorDriver.edit(new FindTransDv());
	}

	public void showData(FindTransDv data) {
		editorDriver.edit(data);
	}

	public void addData(GLTransDv data) {
		resultTable.addRow(data);
	}

	public Long removeSelectedData() {
		Long id = resultTable.getSelectedData().getId();
		resultTable.removeSelected();
		return id;
	}

	public GLTransDv getSelectedData() {
		return resultTable.getSelectedData();
	}

	public void selectAll(boolean checked) {
		for (GLTransDv data : resultTable.getValue()) {
			data.setChecked(checked);
		}
		resultTable.refreshColumn(0);
	}
	
	List<GLTransDv> getData() {
		return resultTable.getValue();
	}

	public void removeChecked() {
		for (int i=resultTable.getRowCount()-1;i>=0;i--) {
			CheckBox cb = (CheckBox) resultTable.getGridWidget(i, 0);
			if (cb.getValue()) {
				resultTable.removeRow(i);
			}
		}
	}

}
