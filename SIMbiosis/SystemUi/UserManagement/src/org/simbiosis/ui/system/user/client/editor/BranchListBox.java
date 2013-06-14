package org.simbiosis.ui.system.user.client.editor;

import java.util.Iterator;

import org.kembang.editor.client.ListBoxListEditor;
import org.kembang.module.shared.BranchDv;

public class BranchListBox extends ListBoxListEditor<Long, BranchDv> {

	@Override
	public void setValue(Long value) {
		boolean found = false;
		int index = 0;
		Iterator<BranchDv> iter = getList().iterator();
		while (iter.hasNext() && !found) {
			BranchDv data = iter.next();
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
			BranchDv data = getList().get(getSelectedIndex());
			return data.getId();
		}
		return 0L;
	}

}
