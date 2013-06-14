package org.simbiosis.ui.system.user.client.role;

import java.util.ArrayList;
import java.util.List;

import org.kembang.grid.client.GridSelectionHandler;
import org.kembang.module.client.mvp.AppStatus;
import org.kembang.module.client.mvp.FormWidget;
import org.simbiosis.ui.system.user.client.editor.RolesTable;
import org.simbiosis.ui.system.user.shared.RoleDv;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.HorizontalPanel;
import com.google.gwt.user.client.ui.Widget;

public class RoleList extends FormWidget implements IRoleList {

	Activity activity;

	private static MyUiBinder uiBinder = GWT.create(MyUiBinder.class);

	interface MyUiBinder extends UiBinder<Widget, RoleList> {
	}

	List<RoleDv> listRole = new ArrayList<RoleDv>();

	RoleViewer roleViewer = new RoleViewer();
	RoleEditor roleEditor = new RoleEditor();

	@UiField
	RolesTable roles;
	@UiField
	HorizontalPanel roleForm;

	boolean isEditor = false;

	public RoleList() {
		initWidget(uiBinder.createAndBindUi(this));
		//
		setHasNew(true);
		setHasEdit(true);
		setHasSave(false);
		setHasReload(true);
		setHasBack(false);
		//
		roleForm.add(roleViewer);
		//
		roles.setSelectionHandler(new GridSelectionHandler() {

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

	void onSelectionHandler() {
		if (isEditor) {
			setViewerData(roles.getSelectedData());
		} else {
			roleViewer.setRole(roles.getSelectedData());
		}
	}

	@Override
	public void setRoles(List<RoleDv> roles) {
		listRole.clear();
		listRole.addAll(roles);
		this.roles.clear();
		for (RoleDv role : roles) {
			this.roles.addRow(role);
		}
	}

	private void setEditorData(RoleDv role) {
		roleForm.clear();
		roleForm.add(roleEditor);
		roleEditor.setRole(role);
		showBack(true);
		showSave(true);
		isEditor = true;
	}

	@Override
	public void editSelected() {
		setEditorData(roles.getSelectedData());
	}

	@Override
	public void newRole() {
		setEditorData(new RoleDv());
	}

	private void setViewerData(RoleDv role) {
		roleForm.clear();
		roleForm.add(roleViewer);
		roleViewer.setRole(role);
		showBack(false);
		showSave(false);
		isEditor = false;
	}

	@Override
	public void viewSelected() {
		setViewerData(roles.getSelectedData());
	}

	@Override
	public void clearViewer() {
		setViewerData(new RoleDv());
	}

	@Override
	public RoleDv getRole() {
		if (isEditor) {
			return roleEditor.getRole();
		} else {
			return roles.getSelectedData();
		}
	}
}
