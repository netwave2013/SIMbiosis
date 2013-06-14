package org.simbiosis.ui.system.user.client.user;

import java.util.ArrayList;
import java.util.List;

import org.kembang.grid.client.GridSelectionHandler;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.kembang.module.shared.BranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.ui.system.user.client.editor.UsersTable;
import org.simbiosis.ui.system.user.shared.RoleDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class UserList extends FormWidget implements IUserList {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, UserList> {
	}

	List<BranchDv> listBranch = new ArrayList<BranchDv>();
	List<RoleDv> listRole = new ArrayList<RoleDv>();
	List<UserDv> listUser = new ArrayList<UserDv>();

	UserViewer userViewer = new UserViewer();
	UserEditor userEditor;
	Boolean isEditor = false;

	@UiField
	UsersTable users;
	@UiField
	HorizontalPanel userForm;

	public UserList() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasNew(true);
		setHasEdit(true);
		setHasSave(false);
		setHasReload(true);
		setHasBack(false);
		//
		userForm.add(userViewer);
		//
		users.setSelectionHandler(new GridSelectionHandler() {

			@Override
			public void onSelection() {
				onSelectionHandler();
			}
		});
	}

	@Override
	public void setActivity(Activity activity, AppStatus appStatus) {
		this.activity = activity;
		setFormActivity(activity);
		setAppStatus(appStatus);
	}

	@Override
	public FormWidget getFormWidget() {
		return this;
	}

	@Override
	public void setBranches(List<BranchDv> branches) {
		listBranch.clear();
		listBranch.addAll(branches);
	}

	@Override
	public void setRoles(List<RoleDv> roles) {
		listRole.clear();
		listRole.addAll(roles);
		userEditor = new UserEditor(listBranch, listRole);
	}

	@Override
	public void setUsers(List<UserDv> users) {
		listUser.clear();
		listUser.addAll(users);
		this.users.clear();
		for (UserDv user : users) {
			this.users.addRow(user);
		}
	}

	void onSelectionHandler() {
		if (isEditor){
			setViewerData(users.getSelectedData());
		} else {
			userViewer.setUser(users.getSelectedData());
		}
	}

	private void setViewerData(UserDv user) {
		userForm.clear();
		userForm.add(userViewer);
		userViewer.setUser(user);
		showBack(false);
		showSave(false);
		isEditor = false;
	}

	private void setEditorData(UserDv user) {
		userForm.clear();
		userForm.add(userEditor);
		userEditor.setUser(user);
		showBack(true);
		showSave(true);
		isEditor = true;
	}

	@Override
	public void editSelected() {
		setEditorData(users.getSelectedData());
	}

	@Override
	public void newUser() {
		setEditorData(new UserDv());
	}

	@Override
	public void viewSelected() {
		setViewerData(users.getSelectedData());
	}

	@Override
	public void clearViewer() {
		setViewerData(new UserDv());
	}

	@Override
	public UserDv getUser() {
		if (isEditor) {
			return userEditor.getUser();
		} else {
			return users.getSelectedData();
		}
	}

}
