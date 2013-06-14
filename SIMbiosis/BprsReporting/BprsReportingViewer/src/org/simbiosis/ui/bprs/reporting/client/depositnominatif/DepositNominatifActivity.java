package org.simbiosis.ui.bprs.reporting.client.depositnominatif;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.shared.BranchDv;
import org.simbiosis.ui.bprs.reporting.client.BprsReportingViewerFactory;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.IDepositNominatif.Activity;
import org.simbiosis.ui.bprs.reporting.client.rpc.ReportSrv;
import org.simbiosis.ui.bprs.reporting.client.rpc.ReportSrvAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class DepositNominatifActivity extends Activity {
	private final ReportSrvAsync srv = GWT.create(ReportSrv.class);

	BprsReportingViewerFactory appFactory;
	DepositNominatifPlace place;

	public DepositNominatifActivity(DepositNominatifPlace place,
			BprsReportingViewerFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IDepositNominatif form = appFactory.getDepositNominatif();
		form.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, form.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_VIEW:
			onView();
			break;
		default:
			break;
		}
	}

	void onView() {
		IDepositNominatif form = appFactory.getDepositNominatif();
		form.loadReport();
	}

	@Override
	public void loadCommonDatas() {
		showLoading();
		srv.loadBranch(getKey(), new AsyncCallback<List<BranchDv>>() {

			@Override
			public void onSuccess(List<BranchDv> result) {
				hideLoading();
				IDepositNominatif form = appFactory.getDepositNominatif();
				form.addBranchList(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : loadBranch");
			}
		});
	}

}
