package org.simbiosis.ui.bprs.reporting.client.neraca;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.shared.BranchDv;
import org.simbiosis.ui.bprs.reporting.client.BprsReportingViewerFactory;
import org.simbiosis.ui.bprs.reporting.client.neraca.INeraca.Activity;
import org.simbiosis.ui.bprs.reporting.client.rpc.ReportSrv;
import org.simbiosis.ui.bprs.reporting.client.rpc.ReportSrvAsync;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class NeracaActivity extends Activity {

	private final ReportSrvAsync srv = GWT.create(ReportSrv.class);

	BprsReportingViewerFactory appFactory;
	NeracaPlace place;

	public NeracaActivity(NeracaPlace place,
			BprsReportingViewerFactory clientFactory) {
		setMainFactory(clientFactory);
		this.place = place;
		this.appFactory = clientFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		INeraca form = appFactory.getNeraca();
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
		INeraca form = appFactory.getNeraca();
		form.loadReport();
	}

	@Override
	public void loadCommonDatas() {
		showLoading();
		srv.loadBranch(getKey(), new AsyncCallback<List<BranchDv>>() {

			@Override
			public void onSuccess(List<BranchDv> result) {
				hideLoading();
				INeraca form = appFactory.getNeraca();
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
