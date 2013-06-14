package org.simbiosis.ui.bprs.reporting.client;

import org.kembang.module.client.mvp.KembangActivityMapper;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.DepositNominatifActivity;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.DepositNominatifPlace;
import org.simbiosis.ui.bprs.reporting.client.labarugiharian.LabaRugiHarianActivity;
import org.simbiosis.ui.bprs.reporting.client.labarugiharian.LabaRugiHarianPlace;
import org.simbiosis.ui.bprs.reporting.client.loannominatif.LoanNominatifActivity;
import org.simbiosis.ui.bprs.reporting.client.loannominatif.LoanNominatifPlace;
import org.simbiosis.ui.bprs.reporting.client.loanremedial.LoanRemedialActivity;
import org.simbiosis.ui.bprs.reporting.client.loanremedial.LoanRemedialPlace;
import org.simbiosis.ui.bprs.reporting.client.neraca.NeracaActivity;
import org.simbiosis.ui.bprs.reporting.client.neraca.NeracaPlace;
import org.simbiosis.ui.bprs.reporting.client.neracaharian.NeracaHarianActivity;
import org.simbiosis.ui.bprs.reporting.client.neracaharian.NeracaHarianPlace;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.SavingNominatifActivity;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.SavingNominatifPlace;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.place.shared.Place;

public class BprsReportingViewerActivityMapper extends KembangActivityMapper {

	public BprsReportingViewerActivityMapper(
			BprsReportingViewerFactory clientFactory) {
		super(clientFactory);
	}

	@Override
	public Activity createActivity(Place place) {
		BprsReportingViewerFactory clientFactory = (BprsReportingViewerFactory) getClientFactory();
		if (place instanceof SavingNominatifPlace) {
			return new SavingNominatifActivity((SavingNominatifPlace) place,
					clientFactory);
		} else if (place instanceof DepositNominatifPlace) {
			return new DepositNominatifActivity((DepositNominatifPlace) place,
					clientFactory);
		} else if (place instanceof LoanNominatifPlace) {
			return new LoanNominatifActivity((LoanNominatifPlace) place,
					clientFactory);
		} else if (place instanceof LoanRemedialPlace) {
			return new LoanRemedialActivity((LoanRemedialPlace) place,
					clientFactory);
		} else if (place instanceof NeracaPlace) {
			return new NeracaActivity((NeracaPlace) place, clientFactory);
		} else if (place instanceof NeracaHarianPlace) {
			return new NeracaHarianActivity((NeracaHarianPlace) place,
					clientFactory);
		} else if (place instanceof LabaRugiHarianPlace) {
			return new LabaRugiHarianActivity((LabaRugiHarianPlace) place,
					clientFactory);
		}
		return null;
	}

}
