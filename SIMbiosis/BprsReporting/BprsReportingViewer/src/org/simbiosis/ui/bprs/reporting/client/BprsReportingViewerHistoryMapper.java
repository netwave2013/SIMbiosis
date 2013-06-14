package org.simbiosis.ui.bprs.reporting.client;

import org.kembang.module.client.mvp.KembangHistoryMapper;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.DepositNominatifPlace;
import org.simbiosis.ui.bprs.reporting.client.labarugi.LabaRugiPlace;
import org.simbiosis.ui.bprs.reporting.client.labarugiharian.LabaRugiHarianPlace;
import org.simbiosis.ui.bprs.reporting.client.loannominatif.LoanNominatifPlace;
import org.simbiosis.ui.bprs.reporting.client.loanremedial.LoanRemedialPlace;
import org.simbiosis.ui.bprs.reporting.client.neraca.NeracaPlace;
import org.simbiosis.ui.bprs.reporting.client.neracaharian.NeracaHarianPlace;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.SavingNominatifPlace;

import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ SavingNominatifPlace.Tokenizer.class,
		DepositNominatifPlace.Tokenizer.class,
		LoanNominatifPlace.Tokenizer.class, LoanRemedialPlace.Tokenizer.class,
		NeracaPlace.Tokenizer.class, NeracaHarianPlace.Tokenizer.class,
		LabaRugiPlace.Tokenizer.class, LabaRugiHarianPlace.Tokenizer.class })
public interface BprsReportingViewerHistoryMapper extends KembangHistoryMapper {

}
