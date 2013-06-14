package org.simbiosis.ui.gl.input.client.postlist;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class PostPlace extends Place {
	String token;

	public PostPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<PostPlace> {

		@Override
		public PostPlace getPlace(String token) {
			return new PostPlace(token);
		}

		@Override
		public String getToken(PostPlace place) {
			return place.getToken();
		}
	}

}
