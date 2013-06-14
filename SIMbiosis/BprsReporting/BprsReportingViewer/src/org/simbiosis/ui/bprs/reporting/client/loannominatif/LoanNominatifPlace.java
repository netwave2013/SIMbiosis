package org.simbiosis.ui.bprs.reporting.client.loannominatif;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoanNominatifPlace extends Place {
	String token;

	public LoanNominatifPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<LoanNominatifPlace> {

		@Override
		public LoanNominatifPlace getPlace(String token) {
			return new LoanNominatifPlace(token);
		}

		@Override
		public String getToken(LoanNominatifPlace place) {
			return place.getToken();
		}
	}
}
