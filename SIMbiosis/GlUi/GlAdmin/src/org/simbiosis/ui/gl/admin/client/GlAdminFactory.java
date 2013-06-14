package org.simbiosis.ui.gl.admin.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.gl.admin.client.coa.ICoaListForm;

public interface GlAdminFactory extends KembangClientFactory {
	ICoaListForm getCoaListForm();
}
