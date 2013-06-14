package org.simbiosis.ui.gl.input.client.juinput;

import java.util.Iterator;
import java.util.List;

import org.kembang.grid.client.FlexTableHelper;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.gl.input.client.editor.GLTransItemViewer;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class JUViewerImpl extends FormWidget implements IJUForm,
		Editor<GLTransDv> {

	Activity activity;

	private static JUInputFormImplUiBinder uiBinder = GWT
			.create(JUInputFormImplUiBinder.class);

	interface JUInputFormImplUiBinder extends UiBinder<Widget, JUViewerImpl> {
	}

	interface Driver extends SimpleBeanEditorDriver<GLTransDv, JUViewerImpl> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label strDate;
	@UiField
	Label code;
	@UiField
	Label description;
	@UiField
	FlexTable transactionFooter;
	@UiField
	Label strCurrency;
	@UiField
	Label strRate;
	@UiField
	GLTransItemViewer items;

	String[] footerText = { "", "Jumlah", "", "0", "0" };
	String[] widthsText = { "28px", "250px", "", "150px", "150px" };
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");

	public JUViewerImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasEdit(true);
		setHasBack(true);
		//
		FlexTableHelper.setupHeader(transactionFooter, 5, footerText,
				widthsText);
		//
		driver.initialize(this);
		driver.edit(new GLTransDv());
		//
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
	public void editData(GLTransDv data) {
		Double debit = 0.00, kredit = 0.00;
		// Hitung debit kredit
		Iterator<GLTransItemDv> item = data.getItems().iterator();
		while (item.hasNext()) {
			GLTransItemDv dataitem = item.next();
			debit += (dataitem.getDebit() == null) ? 0 : dataitem.getDebit();
			kredit += (dataitem.getCredit() == null) ? 0 : dataitem.getCredit();
		}
		transactionFooter.getCellFormatter().setHorizontalAlignment(0, 3,
				HasHorizontalAlignment.ALIGN_RIGHT);
		FlexTableHelper.setTextFooter(transactionFooter, 3,
				numberFormat.format(debit));
		transactionFooter.getCellFormatter().setHorizontalAlignment(0, 4,
				HasHorizontalAlignment.ALIGN_RIGHT);
		FlexTableHelper.setTextFooter(transactionFooter, 4,
				numberFormat.format(kredit));
		driver.edit(data);
	}

	@Override
	public void setListCoa(List<CoaDv> listCoa) {
	}

	@Override
	public GLTransDv getData() {
		return driver.flush();
	}

	@Override
	public boolean isDCEqual() {
		// TODO Auto-generated method stub
		return false;
	}

}
