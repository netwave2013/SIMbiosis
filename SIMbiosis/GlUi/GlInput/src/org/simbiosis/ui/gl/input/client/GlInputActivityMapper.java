package org.simbiosis.ui.gl.input.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.gl.input.client.coatrans.CoaTransActivity;
import org.simbiosis.ui.gl.input.client.coatrans.CoaTransPlace;
import org.simbiosis.ui.gl.input.client.julist.JUListActivity;
import org.simbiosis.ui.gl.input.client.julist.JUListPlace;
import org.simbiosis.ui.gl.input.client.postlist.PostActivity;
import org.simbiosis.ui.gl.input.client.postlist.PostPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class GlInputActivityMapper extends KembangActivityMapper {

	public GlInputActivityMapper(GlInputFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		GlInputFactory clientFactory = (GlInputFactory) getClientFactory();
		if (place instanceof JUListPlace) {
			return new JUListActivity((JUListPlace) place, clientFactory);
		} else if (place instanceof PostPlace) {
			return new PostActivity((PostPlace) place, clientFactory);
		} else if (place instanceof CoaTransPlace) {
			return new CoaTransActivity((CoaTransPlace) place, clientFactory);
		}
		return null;
	}

}
