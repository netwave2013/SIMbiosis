package org.simbiosis.ui.bprs.reporting.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.IDepositNominatif;
import org.simbiosis.ui.bprs.reporting.client.labarugi.ILabaRugi;
import org.simbiosis.ui.bprs.reporting.client.labarugiharian.ILabaRugiHarian;
import org.simbiosis.ui.bprs.reporting.client.loannominatif.ILoanNominatif;
import org.simbiosis.ui.bprs.reporting.client.loanremedial.ILoanRemedial;
import org.simbiosis.ui.bprs.reporting.client.neraca.INeraca;
import org.simbiosis.ui.bprs.reporting.client.neracaharian.INeracaHarian;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.ISavingNominatif;

public interface BprsReportingViewerFactory extends KembangClientFactory {
	ISavingNominatif getSavingNominatif();

	IDepositNominatif getDepositNominatif();

	ILoanNominatif getLoanNominatif();

	ILoanRemedial getLoanRemedial();

	INeraca getNeraca();

	INeracaHarian getNeracaHarian();

	ILabaRugi getLabaRugi();

	ILabaRugiHarian getLabaRugiHarian();
}
