package org.simbiosis.ui.bprs.reporting.client.labarugiharian;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LabaRugiHarianPlace extends Place {
	String token;

	public LabaRugiHarianPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<LabaRugiHarianPlace> {

		@Override
		public LabaRugiHarianPlace getPlace(String token) {
			return new LabaRugiHarianPlace(token);
		}

		@Override
		public String getToken(LabaRugiHarianPlace place) {
			return place.getToken();
		}
	}
}
