package org.simbiosis.ui.gl.input.client.juinput;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kembang.grid.client.ButtonGridHandler;
import org.kembang.grid.client.FlexTableHelper;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.gl.input.client.coadlg.CoaListDlg;
import org.simbiosis.ui.gl.input.client.editor.CurrencyListEditor;
import org.simbiosis.ui.gl.input.client.editor.GLTransItemEditor;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.CurrencyDv;
import org.simbiosis.ui.gl.input.shared.FindCoaDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class JUEditorImpl extends FormWidget implements IJUForm,
		Editor<GLTransDv> {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, JUEditorImpl> {
	}

	interface Driver extends SimpleBeanEditorDriver<GLTransDv, JUEditorImpl> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Button addItem;
	@UiField
	Button delItem;
	@UiField
	DateBox date;
	@UiField
	TextBox code;
	@UiField
	TextBox description;
	@UiField
	FlexTable transactionFooter;
	@UiField
	CurrencyListEditor currencyId;
	@UiField
	TextBox strRate;
	@UiField
	GLTransItemEditor items;

	List<CoaDv> listCoa = new ArrayList<CoaDv>();
	Boolean listCoaLoaded = false;

	String[] footerText = { "", "Jumlah", "", "0", "0" };
	String[] widthsText = { "28px", "250px", "", "200px", "200px" };
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");

	public JUEditorImpl() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasSave(true);
		setHasBack(true);
		//
		FlexTableHelper.setupFooter(transactionFooter, 5, footerText,
				widthsText);
		//
		date.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		//
		// Load data pembantu
		List<CurrencyDv> dataCurrency = new ArrayList<CurrencyDv>();
		CurrencyDv curr = new CurrencyDv();
		curr.setId(1L);
		curr.setCode("IDR");
		curr.setDescription("Rupiah");
		dataCurrency.add(curr);
		curr = new CurrencyDv();
		curr.setId(2L);
		curr.setCode("USD");
		curr.setDescription("Dollar Amerika");
		dataCurrency.add(curr);
		//
		items.addCoaClickHandler(new ButtonGridHandler() {

			@Override
			public void onClick() {
				FindCoaDv findCoaDv = new FindCoaDv(listCoa);
				CoaListDlg coaListDlg = new CoaListDlg(findCoaDv, items, 1);
				coaListDlg.show();
			}

		});
		items.setFooter(transactionFooter);
		//
		driver.initialize(this);
		currencyId.setList(dataCurrency);
		items.setListCoa(listCoa);
		driver.edit(new GLTransDv());
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		if (appStatus.isLogin() && !listCoaLoaded) {
			activity.loadCoa();
		}
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
		FlexTableHelper.setTextFooter(transactionFooter, 3,
				numberFormat.format(debit));
		transactionFooter.getCellFormatter().setHorizontalAlignment(0, 3,
				HasHorizontalAlignment.ALIGN_RIGHT);
		FlexTableHelper.setTextFooter(transactionFooter, 4,
				numberFormat.format(kredit));
		transactionFooter.getCellFormatter().setHorizontalAlignment(0, 4,
				HasHorizontalAlignment.ALIGN_RIGHT);
		driver.edit(data);
	}

	@Override
	public void setListCoa(List<CoaDv> listCoa) {
		this.listCoa.clear();
		this.listCoa.addAll(listCoa);
		//
		items.setListCoa(this.listCoa);
		//
		listCoaLoaded = true;
	}

	@Override
	public GLTransDv getData() {
		return driver.flush();
	}

	@UiHandler("addItem")
	void onAddItem(ClickEvent event) {
		GLTransItemDv newItem = new GLTransItemDv();
		newItem.setId(0L);
		newItem.setNr(items.getRowCount() + 1);
		newItem.setDescription(description.getText());
		items.addRow(newItem);
	}

	@UiHandler("delItem")
	void onDelItem(ClickEvent event) {
		items.removeSelected();
	}

	@Override
	public boolean isDCEqual() {
		Label debit = FlexTableHelper.getFooterLabel(transactionFooter, 3);
		Label credit = FlexTableHelper.getFooterLabel(transactionFooter, 4);
		if (debit.getText().equalsIgnoreCase(credit.getText())) {
			return true;
		}
		return false;
	}

}
