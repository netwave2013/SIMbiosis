package org.simbiosis.ui.bprs.reporting.client.loanremedial;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;

public class LoanRemedialPlace extends Place {
	String token;

	public LoanRemedialPlace(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public static class Tokenizer implements PlaceTokenizer<LoanRemedialPlace> {

		@Override
		public LoanRemedialPlace getPlace(String token) {
			return new LoanRemedialPlace(token);
		}

		@Override
		public String getToken(LoanRemedialPlace place) {
			return place.getToken();
		}
	}
}
