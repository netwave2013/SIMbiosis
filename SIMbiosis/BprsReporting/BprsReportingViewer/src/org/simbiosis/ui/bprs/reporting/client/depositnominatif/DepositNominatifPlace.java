package org.simbiosis.ui.bprs.reporting.client.depositnominatif;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class DepositNominatifPlace extends Place {
	String token;

	public DepositNominatifPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<DepositNominatifPlace> {

		@Override
		public DepositNominatifPlace getPlace(String token) {
			return new DepositNominatifPlace(token);
		}

		@Override
		public String getToken(DepositNominatifPlace place) {
			return place.getToken();
		}
	}
}
