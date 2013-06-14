package org.simbiosis.ui.system.user.client.role;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.simbiosis.ui.system.user.client.UserManagementFactory;
import org.simbiosis.ui.system.user.client.role.IRoleList.Activity;
import org.simbiosis.ui.system.user.client.rpc.Service;
import org.simbiosis.ui.system.user.client.rpc.ServiceAsync;
import org.simbiosis.ui.system.user.shared.RoleDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class RoleListActivity extends Activity {
	private final ServiceAsync srv = GWT.create(Service.class);

	RoleListPlace myPlace;
	UserManagementFactory appFactory;

	public RoleListActivity(RoleListPlace myPlace,
			UserManagementFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IRoleList myForm = appFactory.getRoleEditor();
		myForm.setActivity(this, appFactory.getAppStatus());
		if (appFactory.getAppStatus().isLogin()) {
			loadCommonData();
		}
		appFactory.showApplication(panel, myForm.getFormWidget());
	}

	@Override
	public void dispatch(FormActivityType formActivityType) {
		switch (formActivityType) {
	case FA_NEW:
		onNew();
		break;
	case FA_EDIT:
		onEdit();
		break;
	case FA_SAVE:
		onSave();
		break;
	case FA_RELOAD:
		onReload();
		break;
	case FA_BACK:
		onBack();
		break;
	default:
		break;
	}
	}

	private void onNew() {
		IRoleList myForm = appFactory.getRoleEditor();
		myForm.newRole();
	}

	private void onReload() {
		loadCommonData();
		IRoleList myForm = appFactory.getRoleEditor();
		myForm.clearViewer();
	}

	private void onSave() {
		IRoleList myForm = appFactory.getRoleEditor();
		RoleDv dv = myForm.getRole();
			showLoading();
			srv.saveRole(getKey(), dv, new AsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					hideLoading();
					Window.alert("Data role sudah disimpan");
					onReload();
				}

				@Override
				public void onFailure(Throwable caught) {
					hideLoading();
					Window.alert("Error : saveRole");
				}
			});
	}

	private void onBack() {
		IRoleList myForm = appFactory.getRoleEditor();
		myForm.viewSelected();
	}

	private void onEdit() {
		IRoleList myForm = appFactory.getRoleEditor();
		myForm.editSelected();
	}
	
	private void loadCommonData() {
		srv.listRoles(getKey(), new AsyncCallback<List<RoleDv>>() {

			@Override
			public void onSuccess(List<RoleDv> result) {
				IRoleList myForm = appFactory.getRoleEditor();
				myForm.setRoles(result);
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listRoles");
			}
		});
	}

}
