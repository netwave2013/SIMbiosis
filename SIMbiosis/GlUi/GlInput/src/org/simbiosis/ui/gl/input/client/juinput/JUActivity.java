package org.simbiosis.ui.gl.input.client.juinput;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.gl.input.client.GlInputFactory;
import org.simbiosis.ui.gl.input.client.juinput.IJUForm.Activity;
import org.simbiosis.ui.gl.input.client.rpc.GlService;
import org.simbiosis.ui.gl.input.client.rpc.GlServiceAsync;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class JUActivity extends Activity {

	private final GlServiceAsync glSrv = GWT.create(GlService.class);

	GlInputFactory appFactory;
	Place place;
	Activity activity;

	List<CoaDv> listCoa = new ArrayList<CoaDv>();

	public JUActivity(Place place, GlInputFactory appFactory) {
		setMainFactory(appFactory);
		this.place = place;
		this.appFactory = appFactory;
		this.activity = this;
	}

	public void showData(long id) {
		glSrv.getGLTrans(id, new AsyncCallback<GLTransDv>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : getGLTrans");
			}

			@Override
			public void onSuccess(GLTransDv result) {
				hideLoading();
				IJUForm juViewer = appFactory.getJUViewer();
				juViewer.editData(result);
				appFactory.showApplication(null, juViewer.getFormWidget());
			}
		});
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
		case FA_EDIT:
			onEdit();
			break;
		case FA_SAVE:
			onSave();
			break;
		case FA_BACK:
			onBack();
			break;
		default:
			break;
		}
	}

	void onEdit() {
		IJUForm juViewer = appFactory.getJUViewer();
		IJUForm juEditor = appFactory.getJUEditor();
		juEditor.editData(juViewer.getData());
		appFactory.showApplication(null, juEditor.getFormWidget());
	}

	boolean validate() {
		IJUForm myForm = appFactory.getJUEditor();
		if (myForm.isDCEqual()) {
			GLTransDv transDv = myForm.getData();
			boolean found = false;
			int itemNr = 0;
			int errorNr = 0;
			Iterator<GLTransItemDv> iter = transDv.getItems().iterator();
			while (iter.hasNext() && !found) {
				GLTransItemDv item = iter.next();
				if ((item.getCredit() == 0 && item.getDebit() == 0)
						|| (item.getCredit() != 0 && item.getDebit() != 0)) {
					found = true;
					errorNr = 1;
				}
				if (item.getCoa() == null || item.getCoa() == 0) {
					found = true;
					errorNr = 2;
				}
				if (found) {
					itemNr = item.getNr();
				}
			}
			if (found) {
				String msg = "Item nomor";
				switch (errorNr) {
				case 1:
					msg += itemNr
							+ " memiliki nilai debet dan kredit salah (isi salah satu saja)";
					break;
				case 2:
					msg += itemNr + " memiliki COA yang salah";
					break;
				}
				Window.alert(msg);
			} else {
				return true;
			}
		} else {
			Window.alert("Jumlah debit dan kredit tidak sama");
		}
		return false;
	}

	void onSave() {
		if (validate()) {
			IJUForm myForm = appFactory.getJUEditor();
			GLTransDv transDv = myForm.getData();
			saveGLTrans(transDv);
		}
	}

	void saveGLTrans(GLTransDv transDv) {
		glSrv.saveGLTrans(getKey(), transDv, new AsyncCallback<Long>() {

			@Override
			public void onFailure(Throwable caught) {
				Window.alert("Error : saveGLTrans");
			}

			@Override
			public void onSuccess(Long result) {
				Window.alert("Data sudah berhasil di simpan");
				IJUForm juViewer = appFactory.getJUViewer();
				showData(result);
				appFactory.showApplication(null, juViewer.getFormWidget());
			}
		});
	}

	void onBack() {
		appFactory.getPlaceController().goTo(place);
	}

	@Override
	public void loadCoa() {
		showLoading();
		glSrv.listCoaForTransaction(getKey(), new AsyncCallback<List<CoaDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listCoaByType");
			}

			@Override
			public void onSuccess(List<CoaDv> result) {
				hideLoading();
				IJUForm juEditor = appFactory.getJUEditor();
				juEditor.setListCoa(result);
			}
		});
	}

}
