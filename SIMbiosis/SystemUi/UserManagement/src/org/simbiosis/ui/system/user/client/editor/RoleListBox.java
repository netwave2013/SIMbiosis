package org.simbiosis.ui.system.user.client.editor;

import java.util.Iterator;

import org.kembang.editor.client.ListBoxListEditor;
import org.simbiosis.ui.system.user.shared.RoleDv;

public class RoleListBox extends ListBoxListEditor<Long, RoleDv> {

	@Override
	public void setValue(Long value) {
		boolean found = false;
		int index = 0;
		Iterator<RoleDv> iter = getList().iterator();
		while (iter.hasNext() && !found) {
			RoleDv data = iter.next();
			if (data.getId().compareTo(value) == 0) {
				found = true;
			} else {
				index++;
			}
		}
		if (found) {
			setSelectedIndex(index);
		}
	}

	@Override
	public Long getValue() {
		if ((getList() != null) && (getSelectedIndex() > -1)) {
			RoleDv data = getList().get(getSelectedIndex());
			return data.getId();
		}
		return 0L;
	}

}
