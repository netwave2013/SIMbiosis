package org.simbiosis.ui.gl.input.client.postlist;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.gl.input.client.GlInputFactory;
import org.simbiosis.ui.gl.input.client.rpc.GlService;
import org.simbiosis.ui.gl.input.client.rpc.GlServiceAsync;
import org.simbiosis.ui.gl.input.shared.FindTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class PostActivity extends IPost.Activity {
	private final GlServiceAsync glSrv = GWT.create(GlService.class);

	GlInputFactory appFactory;

	public PostActivity(Place place, GlInputFactory appFactory) {
		setMainFactory(appFactory);
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IPost postForm = appFactory.getPostForm();
		postForm.setActivity(this, appFactory.getAppStatus());
		appFactory.showApplication(panel, postForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_SEARCH:
			onSearch();
			break;
		default:
			break;
		}
	}

	void onSearch() {
		showLoading();
		appFactory.getPostForm().searchTransaction();
	}

	@Override
	public void searchTransaction(Date begin, Date end, Integer status) {
		glSrv.listGLTransByDate(getKey(), begin, end, status,
				new AsyncCallback<List<GLTransDv>>() {

					@Override
					public void onFailure(Throwable caught) {
						Window.alert("Error : listGLTransByDate");
					}

					@Override
					public void onSuccess(List<GLTransDv> result) {
						FindTransDv searchResultDv = new FindTransDv();
						searchResultDv.getResultTable().addAll(result);
						appFactory.getPostForm().showSearchResult(
								searchResultDv);
						hideLoading();
					}
				});
	}

	@Override
	void unRelease(List<GLTransDv> data) {
		// Kumpulkan
		// List<GLTransDv> juList = new ArrayList<GLTransDv>();

		// List<GLTransDv> cbList = new ArrayList<GLTransDv>();

	}

	@Override
	void unPost(List<GLTransDv> data) {
		processPost(data, false);
	}

	@Override
	void post(List<GLTransDv> data) {
		processPost(data, true);
	}

	void processPost(List<GLTransDv> data, boolean posted) {
		showLoading();
		List<Long> idList = new ArrayList<Long>();
		for (GLTransDv myData : data) {
			if (myData.getChecked()) {
				idList.add(myData.getId());
			}
		}
		glSrv.postGLTrans(getKey(), idList, posted, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : postGLTrans");
			}

			@Override
			public void onSuccess(Void result) {
				appFactory.getPostForm().removeChecked();
				hideLoading();
				Window.alert("Transaksi sudah di posting");
			}
		});
	}

}
