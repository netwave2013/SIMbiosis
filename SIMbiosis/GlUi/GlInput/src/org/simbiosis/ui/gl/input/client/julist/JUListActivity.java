package org.simbiosis.ui.gl.input.client.julist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.gl.input.client.GlInputFactory;
import org.simbiosis.ui.gl.input.client.juinput.IJUForm;
import org.simbiosis.ui.gl.input.client.juinput.JUActivity;
import org.simbiosis.ui.gl.input.client.julist.IJUList.Activity;
import org.simbiosis.ui.gl.input.client.rpc.GlService;
import org.simbiosis.ui.gl.input.client.rpc.GlServiceAsync;
import org.simbiosis.ui.gl.input.shared.FindTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class JUListActivity extends Activity {

	private final GlServiceAsync accSrv = GWT.create(GlService.class);

	GlInputFactory appFactory;
	JUListPlace place;
	JUActivity juActivity = null;

	public JUListActivity(JUListPlace place, GlInputFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
	}

	private JUActivity getJUActivity() {
		if (juActivity == null) {
			juActivity = new JUActivity(new JUListPlace(""), appFactory);
		}
		return juActivity;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IJUList juListForm = appFactory.getJUListForm();
		juListForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, juListForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_NEW:
			onNew();
			break;
		case FA_VIEW:
			onView();
			break;
		case FA_DELETE:
			onDelete();
			break;
		case FA_SEARCH:
			onSearch();
			break;
		default:
			break;
		}
	}

	void onNew() {
		//
		IJUForm juEditor = appFactory.getJUEditor();
		GLTransDv newTrans = new GLTransDv();
		newTrans.setDate(new Date());
		newTrans.setCurrencyId(1L);
		newTrans.setStrRate("1");
		juEditor.editData(newTrans);
		appFactory.showApplication(null, juEditor.getFormWidget());
	}

	void onView() {
		showLoading();
		GLTransDv data = appFactory.getJUListForm().getSelectedData();
		getJUActivity().showData(data.getId());
	}

	void onDelete() {
		showLoading();
		Long id = appFactory.getJUListForm().removeSelectedData();
		accSrv.removeGLTrans(id, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : removeGLTrans");
			}

			@Override
			public void onSuccess(Void result) {
				hideLoading();
				Window.alert("Data transaksi sudah di hapus");
			}
		});
	}

	void onSearch() {
		showLoading();
		appFactory.getJUListForm().searchTransaction();
	}

	@Override
	public void searchTransaction(Date begin, Date end, int status) {
		accSrv.listGLTransByDate(getKey(), begin, end, status,
				new AsyncCallback<List<GLTransDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : listGLTransByDate");
					}

					@Override
					public void onSuccess(List<GLTransDv> result) {
						FindTransDv searchResultDv = new FindTransDv();
						searchResultDv.getResultTable().addAll(result);
						appFactory.getJUListForm().showSearchResult(
								searchResultDv);
						hideLoading();
					}
				});
	}

	@Override
	void release(List<GLTransDv> data) {
		showLoading();
		List<Long> idList = new ArrayList<Long>();
		for (GLTransDv myData : data) {
			if (myData.getChecked()) {
				idList.add(myData.getId());
			}
		}
		accSrv.releaseGLTrans(getKey(), idList, true,
				new AsyncCallback<Void>() {

					@Override
					public void onFailure(Throwable caught) {
						hideLoading();
						Window.alert("Error : releaseGLTrans");
					}

					@Override
					public void onSuccess(Void result) {
						appFactory.getJUListForm().removeChecked();
						hideLoading();
						Window.alert("Transaksi sudah di release");
					}
				});
	}

	@Override
	void initViewerEditor() {
		IJUForm juViewer = appFactory.getJUViewer();
		juViewer.setActivity(getJUActivity(), appFactory.getAppStatus());
		IJUForm juEditor = appFactory.getJUEditor();
		juEditor.setActivity(getJUActivity(), appFactory.getAppStatus());
	}

}
