package org.simbiosis.ui.system.user.client.role;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class RoleListPlace extends Place {
	String token;

	public RoleListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<RoleListPlace> {

		@Override
		public RoleListPlace getPlace(String token) {
			return new RoleListPlace(token);
		}

		@Override
		public String getToken(RoleListPlace place) {
			return place.getToken();
		}

	}
}
