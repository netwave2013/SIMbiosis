package org.simbiosis.ui.system.user.client.user;

import org.kembang.module.shared.UserDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

public class UserViewer extends Composite implements Editor<UserDv> {

	private static UserViewerUiBinder uiBinder = GWT
			.create(UserViewerUiBinder.class);

	interface UserViewerUiBinder extends UiBinder<Widget, UserViewer> {
	}

	interface Driver extends SimpleBeanEditorDriver<UserDv, UserViewer> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	Label name;
	@UiField
	Label realName;
	@UiField
	Label email;
	@UiField
	Label roleName;
	@UiField
	Label branchName;
	@UiField
	Label strActive;
	
	public UserViewer() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
		driver.edit(new UserDv());
	}

	public void setUser(UserDv user){
		driver.edit(user);
	}
}
