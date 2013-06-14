package org.simbiosis.ui.system.user.client.role;

import org.simbiosis.ui.system.user.shared.RoleDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.editor.client.Editor;
import com.google.gwt.editor.client.SimpleBeanEditorDriver;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;

public class RoleEditor extends Composite implements Editor<RoleDv> {

	private static UserViewerUiBinder uiBinder = GWT
			.create(UserViewerUiBinder.class);

	interface UserViewerUiBinder extends UiBinder<Widget, RoleEditor> {
	}

	interface Driver extends SimpleBeanEditorDriver<RoleDv, RoleEditor> {
	}

	private Driver driver = GWT.create(Driver.class);

	@UiField
	TextBox name;
	@UiField
	TextBox description;

	public RoleEditor() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		driver.initialize(this);
		driver.edit(new RoleDv());
	}

	public void setRole(RoleDv role) {
		driver.edit(role);
	}

	public RoleDv getRole() {
		return driver.flush();
	}
}
