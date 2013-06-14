package org.simbiosis.ui.system.user.client.user;

import java.util.List;

import org.kembang.module.client.mvp.FormActivityType;
import org.kembang.module.shared.BranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.ui.system.user.client.UserManagementFactory;
import org.simbiosis.ui.system.user.client.rpc.Service;
import org.simbiosis.ui.system.user.client.rpc.ServiceAsync;
import org.simbiosis.ui.system.user.client.user.IUserList.Activity;
import org.simbiosis.ui.system.user.shared.RoleDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.web.bindery.event.shared.EventBus;

public class UserListActivity extends Activity {

	private final ServiceAsync srv = GWT.create(Service.class);

	UserListPlace myPlace;
	UserManagementFactory appFactory;

	public UserListActivity(UserListPlace myPlace,
			UserManagementFactory appFactory) {
		setMainFactory(appFactory);
		this.myPlace = myPlace;
		this.appFactory = appFactory;
	}

	@Override
	public void start(AcceptsOneWidget panel, EventBus eventBus) {
		IUserList myForm = appFactory.getUserEditor();
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
		IUserList myForm = appFactory.getUserEditor();
		myForm.newUser();
	}

	private void onReload() {
		loadUsers();
		IUserList myForm = appFactory.getUserEditor();
		myForm.clearViewer();
	}

	private void onSave() {
		boolean isSavingAllowed = false;
		IUserList myForm = appFactory.getUserEditor();
		UserDv dv = myForm.getUser();
		if (dv.isChangePassword()) {
			if (dv.getPassword().equals(dv.getConfirmPassword())) {
				isSavingAllowed = true;
			} else {
				Window.alert("Kata kunci konfirmasi tidak sama dengan kata kunci");
			}
		} else {
			isSavingAllowed = true;
		}
		if (isSavingAllowed) {
			showLoading();
			srv.saveUser(getKey(), dv, new AsyncCallback<Void>() {

				@Override
				public void onSuccess(Void result) {
					hideLoading();
					Window.alert("Data user sudah disimpan");
					onReload();
				}

				@Override
				public void onFailure(Throwable caught) {
					hideLoading();
					Window.alert("Error : saveUser");
				}
			});
		}
	}

	private void onBack() {
		IUserList myForm = appFactory.getUserEditor();
		myForm.viewSelected();
	}

	private void onEdit() {
		IUserList myForm = appFactory.getUserEditor();
		myForm.editSelected();
	}

	private void loadCommonData() {
		showLoading();
		srv.listBranches(getKey(), new AsyncCallback<List<BranchDv>>() {

			@Override
			public void onSuccess(List<BranchDv> result) {
				IUserList myForm = appFactory.getUserEditor();
				// Tambahin untuk yang semua
				BranchDv all = new BranchDv();
				all.setId(0L);
				all.setName("SEMUA CABANG");
				result.add(0, all);
				myForm.setBranches(result);
				loadRoles();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listBranches");
			}
		});
	}

	private void loadRoles() {
		srv.listRoles(getKey(), new AsyncCallback<List<RoleDv>>() {

			@Override
			public void onSuccess(List<RoleDv> result) {
				IUserList myForm = appFactory.getUserEditor();
				myForm.setRoles(result);
				loadUsers();
			}

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listRoles");
			}
		});
	}

	private void loadUsers() {
		srv.listUsers(getKey(), new AsyncCallback<List<UserDv>>() {

			@Override
			public void onFailure(Throwable caught) {
				hideLoading();
				Window.alert("Error : listRoles");
			}

			@Override
			public void onSuccess(List<UserDv> result) {
				IUserList myForm = appFactory.getUserEditor();
				myForm.setUsers(result);
				hideLoading();
			}
		});
	}
}
