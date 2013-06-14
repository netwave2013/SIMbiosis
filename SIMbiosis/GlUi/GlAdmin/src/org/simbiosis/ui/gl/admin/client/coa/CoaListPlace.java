package org.simbiosis.ui.gl.admin.client.coa;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CoaListPlace extends Place {
	String token;

	public CoaListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<CoaListPlace> {

		@Override
		public CoaListPlace getPlace(String token) {
			return new CoaListPlace(token);
		}

		@Override
		public String getToken(CoaListPlace place) {
			return place.getToken();
		}

	}
}
