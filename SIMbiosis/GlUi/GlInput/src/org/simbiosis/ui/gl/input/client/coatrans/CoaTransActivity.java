package org.simbiosis.ui.gl.input.client.coatrans;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.gl.input.client.GlInputFactory;
import org.simbiosis.ui.gl.input.client.coatrans.ICoaTrans.Activity;
import org.simbiosis.ui.gl.input.client.rpc.GlService;
import org.simbiosis.ui.gl.input.client.rpc.GlServiceAsync;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.FindTransItemDv;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class CoaTransActivity extends Activity {
	GlInputFactory appFactory;
	CoaTransPlace place;
	private final GlServiceAsync accService = GWT.create(GlService.class);

	public CoaTransActivity(CoaTransPlace place, GlInputFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		ICoaTrans myForm = appFactory.getCoaTrans();
		myForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_VIEW:
			onView();
			break;
		case FA_EXPORTPDF:
			onExportPdf();
			break;
		default:
			break;
		}
	}

	private void onExportPdf() {
		DateTimeFormat fmt = DateTimeFormat.getFormat("dd-MM-yyyy");
		ICoaTrans myForm = appFactory.getCoaTrans();
		Window.Location
				.replace("/BprsReportingService/getGlTransPdf?beginDate="
						+ fmt.format(myForm.getBeginDate()) + "&endDate="
						+ fmt.format(myForm.getEndDate()) + "&coa="
						+ myForm.getCoa());
	}

	private void onView() {
		showLoading();
		ICoaTrans myForm = appFactory.getCoaTrans();
		accService.listGLTransCoa(getKey(), myForm.getBeginDate(),
				myForm.getEndDate(), myForm.getCoa(),
				new AsyncCallback<List<GLTransItemDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listGLTransCoa");
					}

					@Override
					public void onSuccess(List<GLTransItemDv> result) {
						hideLoading();
						FindTransItemDv findDv = new FindTransItemDv();
						findDv.setItems(result);
						ICoaTrans myForm = appFactory.getCoaTrans();
						myForm.showSearchResult(findDv);
					}
				});
	}

	@Override
	public void loadCoa() {
		showLoading();
		accService.listCoaForTransaction(getKey(),
				new AsyncCallback<List<CoaDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listCoaByType");
					}

					@Override
					public void onSuccess(List<CoaDv> result) {
						hideLoading();
						ICoaTrans myForm = appFactory.getCoaTrans();
						myForm.setListCoa(result);
					}
				});
	}
}
