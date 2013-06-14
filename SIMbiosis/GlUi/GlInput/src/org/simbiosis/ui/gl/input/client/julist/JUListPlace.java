package org.simbiosis.ui.gl.input.client.julist;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class JUListPlace extends Place {
	String token;

	public JUListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<JUListPlace> {

		@Override
		public JUListPlace getPlace(String token) {
			return new JUListPlace(token);
		}

		@Override
		public String getToken(JUListPlace place) {
			return place.getToken();
		}
	}
}
