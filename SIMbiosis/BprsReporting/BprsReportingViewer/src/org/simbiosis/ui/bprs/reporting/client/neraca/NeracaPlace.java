package org.simbiosis.ui.bprs.reporting.client.neraca;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class NeracaPlace extends Place {
	String token;

	public NeracaPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<NeracaPlace> {

		@Override
		public NeracaPlace getPlace(String token) {
			return new NeracaPlace(token);
		}

		@Override
		public String getToken(NeracaPlace place) {
			return place.getToken();
		}
	}
}
