package org.simbiosis.ui.gl.admin.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.gl.admin.client.coa.CoaListForm;
import org.simbiosis.ui.gl.admin.client.coa.ICoaListForm;

public class GlAdminFactoryImpl extends KembangClientFactoryImpl implements
		GlAdminFactory {

	static final CoaListForm COA_LIST_FORM = new CoaListForm();

	@Override
	public ICoaListForm getCoaListForm() {
		return COA_LIST_FORM;
	}

}
