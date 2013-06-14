package org.simbiosis.ui.bprs.reporting.client.neracaharian;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.shared.BranchDv;
import org.simbiosis.ui.bprs.reporting.client.BprsReportingViewerFactory;
import org.simbiosis.ui.bprs.reporting.client.neracaharian.INeracaHarian.Activity;
import org.simbiosis.ui.bprs.reporting.client.rpc.ReportSrv;
import org.simbiosis.ui.bprs.reporting.client.rpc.ReportSrvAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class NeracaHarianActivity extends Activity {

	private final ReportSrvAsync srv = GWT.create(ReportSrv.class);

	BprsReportingViewerFactory appFactory;
	NeracaHarianPlace place;

	public NeracaHarianActivity(NeracaHarianPlace place,
			BprsReportingViewerFactory clientFactory) {
		setMainFactory(clientFactory);
		this.place = place;
		this.appFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		INeracaHarian form = appFactory.getNeracaHarian();
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
		INeracaHarian form = appFactory.getNeracaHarian();
		form.loadReport();
	}

	@Override
	public void loadCommonDatas() {
		showLoading();
		srv.loadBranch(getKey(), new AsyncCallback<List<BranchDv>>() {

			@Override
			public void onSuccess(List<BranchDv> result) {
				hideLoading();
				INeracaHarian form = appFactory.getNeracaHarian();
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
