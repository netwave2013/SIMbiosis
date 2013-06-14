package org.simbiosis.ui.gl.input.client.coatrans;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class CoaTransPlace extends Place {
	String token;

	public CoaTransPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<CoaTransPlace> {

		@Override
		public CoaTransPlace getPlace(String token) {
			return new CoaTransPlace(token);
		}

		@Override
		public String getToken(CoaTransPlace place) {
			return place.getToken();
		}
	}

}
