package org.simbiosis.ui.system.user.client.user;

import java.util.List;

import org.kembang.module.shared.BranchDv;
import org.kembang.module.shared.UserDv;
import org.simbiosis.ui.system.user.client.editor.BranchListBox;
import org.simbiosis.ui.system.user.client.editor.RoleListBox;
import org.simbiosis.ui.system.user.shared.RoleDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.PasswordTextBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class UserEditor extends Composite implements Editor<UserDv> {

	private static UserViewerUiBinder uiBinder = GWT
			.create(UserViewerUiBinder.class);

	interface UserViewerUiBinder extends UiBinder<Widget, UserEditor> {
	}

	interface Driver extends SimpleBeanEditorDriver<UserDv, UserEditor> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	TextBox name;
	@UiField
	TextBox realName;
	@UiField
	TextBox email;
	@UiField
	RoleListBox role;
	@UiField
	BranchListBox branch;
	@UiField
	CheckBox active;
	@UiField
	CheckBox changePassword;
	@UiField
	PasswordTextBox password;
	@UiField
	PasswordTextBox confirmPassword;

	public UserEditor(List<BranchDv> branchList, List<RoleDv> roleList) {
		initWidget(uiBinder.createAndBindUi(this));
		//
		role.setList(roleList);
		branch.setList(branchList);
		//
		driver.initialize(this);
		driver.edit(new UserDv());
		initChangePassword();
	}

	public void setUser(UserDv user) {
		driver.edit(user);
		initChangePassword();
	}

	public UserDv getUser() {
		return driver.flush();
	}

	@UiHandler("changePassword")
	public void onChangePasswordClick(ClickEvent event) {
		initChangePassword();
	}

	private void initChangePassword() {
		if (changePassword.getValue()) {
			password.setEnabled(true);
			confirmPassword.setEnabled(true);
		} else {
			password.setEnabled(false);
			confirmPassword.setEnabled(false);
		}
	}
}
