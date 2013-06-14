package org.simbiosis.ui.gl.admin.client.editor;

import java.util.Iterator;

import org.kembang.editor.client.ListBoxListEditor;
import org.simbiosis.ui.gl.admin.shared.BranchDv;

public class BranchListEditor extends ListBoxListEditor<Long, BranchDv> {

	@Override
	public void setValue(Long value) {
		int index = 0;
		boolean found = false;
		if (getList() != null) {
			Iterator<BranchDv> iter = getList().iterator();
			while (iter.hasNext() && !found) {
				BranchDv coa = iter.next();
				if (coa.getId() == value) {
					found = true;
				} else {
					index++;
				}
			}
		}
		if (!found) {
			index = -1;
		}
		setSelectedIndex(index);
	}

	@Override
	public Long getValue() {
		int index = getSelectedIndex();
		if (index > -1) {
			if (getList() != null) {
				BranchDv coa = getList().get(index);
				return coa.getId();
			} else {
				return 0L;
			}
		}
		return 0L;
	}

}
