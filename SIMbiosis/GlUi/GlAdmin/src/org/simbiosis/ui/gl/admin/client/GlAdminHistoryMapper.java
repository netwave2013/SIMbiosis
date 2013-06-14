package org.simbiosis.ui.gl.admin.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.ui.gl.admin.client.coa.CoaListPlace;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ CoaListPlace.Tokenizer.class })
public interface GlAdminHistoryMapper extends KembangHistoryMapper {

}
