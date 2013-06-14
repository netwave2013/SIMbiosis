package org.simbiosis.ui.gl.input.client.julist;

import java.util.Date;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.gl.input.shared.FindTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class JUListForm extends FormWidget implements IJUList {

	Activity activity;

	private static JUListFormImplUiBinder uiBinder = GWT
			.create(JUListFormImplUiBinder.class);

	interface JUListFormImplUiBinder extends UiBinder<Widget, JUListForm> {
	}

	@UiField
	ListBox filterPencarian;
	@UiField
	DateBox beginDate;
	@UiField
	DateBox endDate;
	@UiField
	JUListEditor searchEditor;
	@UiField
	Button btnRelease;

	CheckBox checkAll;

	String[] labelsText = { "No", "Kode transaksi", "Tanggal", "Deskripsi",
			"Nilai" };
	String[] widthsText = { "30px", "28px", "130px", "80px", "", "150px" };

	public JUListForm() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasNew(true);
		setHasView(true);
		setHasDelete(true);
		setHasSearch(true);
		//
		// init filter pencarian
		filterPencarian.addItem("Belum release");
		filterPencarian.addItem("Sudah release");
		filterPencarian.addItem("Semua");
		// init format batasTanggal
		beginDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		beginDate.setValue(new Date());
		endDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		endDate.setValue(new Date());
		// format table hasil pencarian
		checkAll = new CheckBox();
		checkAll.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onCheckAll();
			}
		});
		// FIXME : Untuk checkbox select all
		//resultHeader.setTitles(labelsText, 1);
		//resultHeader.setWidget(0, checkAll);
		//resultHeader.setWidths(widthsText);
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		if (appStatus.isLogin()) {
			activity.initViewerEditor();
		}
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void clearView() {
		searchEditor.showData(new FindTransDv());
	}

	@Override
	public void showSearchResult(FindTransDv data) {
		searchEditor.showData(data);
	}

	@Override
	public GLTransDv getSelectedData() {
		return searchEditor.getSelectedData();
	}

	@Override
	public Long removeSelectedData() {
		return searchEditor.removeSelectedData();
	}

	@Override
	public void searchTransaction() {
		int status = 1;
		if (filterPencarian.getSelectedIndex() == 1) {
			status = 4;
		} else if (filterPencarian.getSelectedIndex() == 2) {
			status = 0;
		}
		activity.searchTransaction(beginDate.getValue(), endDate.getValue(),
				status);
	}

	private void onCheckAll() {
		searchEditor.selectAll(checkAll.getValue());
	}

	@UiHandler("btnRelease")
	public void onBtnReleaseClick(ClickEvent ce) {
		activity.release(searchEditor.getData());
	}

	@Override
	public void removeChecked() {
		searchEditor.removeChecked();
	}

}
