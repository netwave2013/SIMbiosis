package org.simbiosis.ui.bprs.reporting.client.savingnominatif;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class SavingNominatifPlace extends Place {
	String token;

	public SavingNominatifPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<SavingNominatifPlace> {

		@Override
		public SavingNominatifPlace getPlace(String token) {
			return new SavingNominatifPlace(token);
		}

		@Override
		public String getToken(SavingNominatifPlace place) {
			return place.getToken();
		}
	}
}
