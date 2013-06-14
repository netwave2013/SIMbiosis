package org.simbiosis.ui.gl.input.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.ui.gl.input.client.coatrans.CoaTransPlace;
import org.simbiosis.ui.gl.input.client.julist.JUListPlace;
import org.simbiosis.ui.gl.input.client.postlist.PostPlace;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ JUListPlace.Tokenizer.class, PostPlace.Tokenizer.class,
		CoaTransPlace.Tokenizer.class })
public interface GlInputHistoryMapper extends KembangHistoryMapper {

}
