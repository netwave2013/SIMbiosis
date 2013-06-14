package org.simbiosis.ui.bprs.reporting.client.labarugi;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.shared.BranchDv;
import org.simbiosis.ui.bprs.reporting.client.BprsReportingViewerFactory;
import org.simbiosis.ui.bprs.reporting.client.labarugi.ILabaRugi.Activity;
import org.simbiosis.ui.bprs.reporting.client.rpc.ReportSrv;
import org.simbiosis.ui.bprs.reporting.client.rpc.ReportSrvAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class LabaRugiActivity extends Activity {

	private final ReportSrvAsync srv = GWT.create(ReportSrv.class);

	BprsReportingViewerFactory appFactory;
	LabaRugiPlace place;

	public LabaRugiActivity(LabaRugiPlace place,
			BprsReportingViewerFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ILabaRugi form = appFactory.getLabaRugi();
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
		ILabaRugi form = appFactory.getLabaRugi();
		form.loadReport();
	}

	@Override
	public void loadCommonDatas() {
		showLoading();
		srv.loadBranch(getKey(), new AsyncCallback<List<BranchDv>>() {

			@Override
			public void onSuccess(List<BranchDv> result) {
				hideLoading();
				ILabaRugi form = appFactory.getLabaRugi();
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
