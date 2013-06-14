package org.simbiosis.ui.bprs.reporting.client.neracaharian;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class NeracaHarianPlace extends Place {
	String token;

	public NeracaHarianPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<NeracaHarianPlace> {

		@Override
		public NeracaHarianPlace getPlace(String token) {
			return new NeracaHarianPlace(token);
		}

		@Override
		public String getToken(NeracaHarianPlace place) {
			return place.getToken();
		}
	}
}
