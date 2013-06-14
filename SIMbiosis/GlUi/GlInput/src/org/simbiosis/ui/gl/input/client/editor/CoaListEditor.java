package org.simbiosis.ui.gl.input.client.editor;

import java.util.Iterator;

import org.kembang.editor.client.ListBoxListEditor;
import org.simbiosis.ui.gl.input.shared.CoaDv;

public class CoaListEditor extends ListBoxListEditor<Long, CoaDv> {

	@Override
	public void setValue(Long value) {
		int index = 0;
		boolean found = false;
		if (getList() != null) {
			Iterator<CoaDv> iter = getList().iterator();
			while (iter.hasNext() && !found) {
				CoaDv coa = iter.next();
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
				CoaDv coa = getList().get(index);
				return coa.getId();
			} else {
				return 0L;
			}
		}
		return 0L;
	}

}
