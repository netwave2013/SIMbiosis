package org.simbiosis.ui.gl.input.client.coadlg;

import org.kembang.grid.client.AdvancedGrid;
import org.simbiosis.ui.gl.input.client.editor.CoaListTable;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.FindCoaDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.DialogBox;
import com.google.gwt.user.client.ui.Widget;

public class CoaListDlg extends Composite implements Editor<FindCoaDv> {

	private static final Binder binder = GWT.create(Binder.class);

	interface Binder extends UiBinder<Widget, CoaListDlg> {
	}

	interface Driver extends SimpleBeanEditorDriver<FindCoaDv, CoaListDlg> {
	}

	private Driver driver = GWT.create(Driver.class);
	
	@UiField
	CoaListTable resultTable;
	@UiField
	Button cancel;
	@UiField
	Button ok;
	@UiField
	DialogBox mainPanel;

	FindCoaDv findCoaDv;
	Widget table;
	int col;

	public CoaListDlg(FindCoaDv findCoaDv, Widget table, int col) {
		initWidget(binder.createAndBindUi(this));
		this.findCoaDv = findCoaDv;
		this.table = table;
		this.col = col;
		//
		driver.initialize(this);
		driver.edit(findCoaDv);
	}

	public void show() {
		driver.edit(findCoaDv);
		mainPanel.center();
	}

	@UiHandler("cancel")
	void onCancelClick(ClickEvent e) {
		mainPanel.hide();
	}

	@SuppressWarnings("rawtypes")
	@UiHandler("ok")
	void onOkClick(ClickEvent e) {
		CoaDv coaDv = resultTable.getSelectedData();
		String strCoa = coaDv.getCode() + " - " + coaDv.getDescription();
		((AdvancedGrid) table).setTextSuggest(col, strCoa);
		mainPanel.hide();
	}
}
