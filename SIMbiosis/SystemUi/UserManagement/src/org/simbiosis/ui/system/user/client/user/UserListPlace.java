package org.simbiosis.ui.system.user.client.user;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class UserListPlace extends Place {
	String token;

	public UserListPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<UserListPlace> {

		@Override
		public UserListPlace getPlace(String token) {
			return new UserListPlace(token);
		}

		@Override
		public String getToken(UserListPlace place) {
			return place.getToken();
		}

	}
}
