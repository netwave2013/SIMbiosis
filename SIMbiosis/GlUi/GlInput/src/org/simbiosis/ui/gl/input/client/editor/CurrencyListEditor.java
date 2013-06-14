package org.simbiosis.ui.gl.input.client.editor;

import java.util.Iterator;

import org.kembang.editor.client.ListBoxListEditor;
import org.simbiosis.ui.gl.input.shared.CurrencyDv;

public class CurrencyListEditor extends ListBoxListEditor<Long, CurrencyDv> {

	@Override
	public void setValue(Long value) {
		int index = 0;
		boolean found = false;
		Iterator<CurrencyDv> iter = getList().iterator();
		while (iter.hasNext() && !found) {
			CurrencyDv coa = iter.next();
			if (coa.getId() == value) {
				found = true;
			} else {
				index++;
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
			CurrencyDv coa = getList().get(index);
			return coa.getId();
		}
		return 0L;
	}

}
