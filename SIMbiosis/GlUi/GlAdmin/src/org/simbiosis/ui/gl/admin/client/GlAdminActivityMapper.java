package org.simbiosis.ui.gl.admin.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.gl.admin.client.coa.CoaListActivity;
import org.simbiosis.ui.gl.admin.client.coa.CoaListPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class GlAdminActivityMapper extends KembangActivityMapper {

	public GlAdminActivityMapper(GlAdminFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		GlAdminFactory clientFactory = (GlAdminFactory) getClientFactory();
		if (place instanceof CoaListPlace) {
			return new CoaListActivity((CoaListPlace) place, clientFactory);
		}
		return null;
	}

}
