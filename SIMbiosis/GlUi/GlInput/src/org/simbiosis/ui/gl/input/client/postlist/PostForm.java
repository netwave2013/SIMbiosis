package org.simbiosis.ui.gl.input.client.postlist;

import java.util.Date;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.gl.input.shared.FindTransDv;

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

public class PostForm extends FormWidget implements IPost {

	Activity activity;

	private static ReleaseFormUiBinder uiBinder = GWT
			.create(ReleaseFormUiBinder.class);

	interface ReleaseFormUiBinder extends UiBinder<Widget, PostForm> {
	}

	@UiField
	ListBox filterPencarian;
	@UiField
	DateBox beginDate;
	@UiField
	DateBox endDate;
	@UiField
	PostList searchEditor;
	@UiField
	Button btnPost;
	@UiField
	Button btnUnpost;
	@UiField
	Button btnUnrelease;

	CheckBox checkAll;

	DateTimeFormat dtf = DateTimeFormat.getFormat("dd-MM-yyyy");

	public PostForm() {
		initWidget(uiBinder.createAndBindUi(this));
		setHasSave(true);
		setHasSearch(true);
		// init filter pencarian
		filterPencarian.addItem("Belum posting");
		filterPencarian.addItem("Sudah posting");
		filterPencarian.addItem("Semua");
		// init format batasTanggal
		beginDate.setFormat(new DateBox.DefaultFormat(dtf));
		beginDate.setValue(new Date());
		endDate.setFormat(new DateBox.DefaultFormat(dtf));
		endDate.setValue(new Date());
		// format table hasil pencarian
		// format table hasil pencarian
		checkAll = new CheckBox();
		checkAll.addClickHandler(new ClickHandler() {

			@Override
			public void onClick(ClickEvent event) {
				onCheckAll();
			}
		});
		// FIXME: Untuk check box pilih semua belum terimplementasi
		//resultHeader.setTitles(labelsText, 1);
		//resultHeader.setWidget(0, checkAll);
		//resultHeader.setWidths(widthsText);
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void searchTransaction() {
		Integer status=2;
		if (filterPencarian.getSelectedIndex()==1) {
			status=3;
		} else if (filterPencarian.getSelectedIndex()==2) {
			status=4;
		}
		activity.searchTransaction(beginDate.getValue(), endDate.getValue(), status);
	}

	@Override
	public void showSearchResult(FindTransDv data) {
		searchEditor.showData(data);
	}

	@UiHandler("btnUnrelease")
	public void onBtnUnreleaseClick(ClickEvent ce) {
		activity.unRelease(searchEditor.getData());
	}

	@UiHandler("btnPost")
	public void onBtnPostClick(ClickEvent ce) {
		activity.post(searchEditor.getData());
	}

	@UiHandler("btnUnpost")
	public void onBtnUnpostClick(ClickEvent ce) {
		activity.unPost(searchEditor.getData());
	}

	@Override
	public void removeChecked() {
		searchEditor.removeChecked();
	}

	private void onCheckAll() {
		searchEditor.selectAll(checkAll.getValue());
	}


}
