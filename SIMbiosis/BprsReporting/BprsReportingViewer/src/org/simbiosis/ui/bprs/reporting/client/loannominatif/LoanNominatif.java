package org.simbiosis.ui.bprs.reporting.client.loannominatif;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.BranchDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.user.datepicker.client.DateBox;

public class LoanNominatif extends FormWidget implements ILoanNominatif {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, LoanNominatif> {
	}

	@UiField
	ListBox branch;
	@UiField
	DateBox date;
	@UiField
	DockLayoutPanel reportFrame;

	boolean commonListLoaded = false;
	List<BranchDv> branchList = new ArrayList<BranchDv>();

	public LoanNominatif() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasView(true);
		setHasExportXls(true);
		// init format batasTanggal
		date.setFormat(new DateBox.DefaultFormat(DateTimeFormat
				.getFormat("dd-MM-yyyy")));
		date.setValue(new Date());
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
		if (appStatus.isLogin() && !commonListLoaded) {
			activity.loadCommonDatas();
			commonListLoaded = true;
		}
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void loadReport() {
		Long id = branchList.get(branch.getSelectedIndex()).getId();
		String strBranch = "branch=";
		strBranch += id.toString();
		String strDate = "date=" + date.getTextBox().getText();
		Frame frame = new Frame("/BprsReportingService/getLoanNominatif?" + strBranch
				+ "&" + strDate);
		frame.setSize("100%", "100%");
		reportFrame.clear();
		reportFrame.add(frame);
	}

	@Override
	public void addBranchList(List<BranchDv> branchList) {
		this.branchList.addAll(branchList);
		for (BranchDv dv : branchList) {
			branch.addItem(dv.getName());
		}
	}

}
