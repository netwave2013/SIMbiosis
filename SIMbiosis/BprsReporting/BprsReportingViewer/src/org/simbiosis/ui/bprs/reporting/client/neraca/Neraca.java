package org.simbiosis.ui.bprs.reporting.client.neraca;

import java.util.ArrayList;
import java.util.List;

import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.BranchDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.DockLayoutPanel;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;

public class Neraca extends FormWidget implements INeraca {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, Neraca> {
	}

	@UiField
	ListBox branch;
	@UiField
	ListBox month;
	@UiField
	ListBox year;
	@UiField
	DockLayoutPanel reportFrame;

	boolean commonListLoaded = false;
	List<BranchDv> branchList = new ArrayList<BranchDv>();

	public Neraca() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasView(true);
		setHasExportXls(true);
		//
		for (int i = 1; i <= 12; i++) {
			month.addItem("" + i);
		}
		for (int i = 2007; i <= 2015; i++) {
			year.addItem("" + i);
		}
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
		String strMonth = "month=";
		strMonth += month.getItemText(month.getSelectedIndex());
		String strYear = "year=";
		strYear += year.getItemText(year.getSelectedIndex());
		String url = "/BprsReportingService/getNeraca?" + strBranch + "&" + strMonth
				+ "&" + strYear;
		Frame frame = new Frame(url);
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
