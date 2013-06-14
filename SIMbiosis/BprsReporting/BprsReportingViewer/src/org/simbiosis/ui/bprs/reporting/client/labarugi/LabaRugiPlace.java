package org.simbiosis.ui.bprs.reporting.client.labarugi;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LabaRugiPlace extends Place {
	String token;

	public LabaRugiPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<LabaRugiPlace> {

		@Override
		public LabaRugiPlace getPlace(String token) {
			return new LabaRugiPlace(token);
		}

		@Override
		public String getToken(LabaRugiPlace place) {
			return place.getToken();
		}
	}
}
