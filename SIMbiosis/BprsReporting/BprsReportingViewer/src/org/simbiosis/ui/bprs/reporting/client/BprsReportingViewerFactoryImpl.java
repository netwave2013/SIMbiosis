package org.simbiosis.ui.bprs.reporting.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.DepositNominatif;
import org.simbiosis.ui.bprs.reporting.client.depositnominatif.IDepositNominatif;
import org.simbiosis.ui.bprs.reporting.client.labarugi.ILabaRugi;
import org.simbiosis.ui.bprs.reporting.client.labarugi.LabaRugi;
import org.simbiosis.ui.bprs.reporting.client.labarugiharian.ILabaRugiHarian;
import org.simbiosis.ui.bprs.reporting.client.labarugiharian.LabaRugiHarian;
import org.simbiosis.ui.bprs.reporting.client.loannominatif.ILoanNominatif;
import org.simbiosis.ui.bprs.reporting.client.loannominatif.LoanNominatif;
import org.simbiosis.ui.bprs.reporting.client.loanremedial.ILoanRemedial;
import org.simbiosis.ui.bprs.reporting.client.loanremedial.LoanRemedial;
import org.simbiosis.ui.bprs.reporting.client.neraca.INeraca;
import org.simbiosis.ui.bprs.reporting.client.neraca.Neraca;
import org.simbiosis.ui.bprs.reporting.client.neracaharian.INeracaHarian;
import org.simbiosis.ui.bprs.reporting.client.neracaharian.NeracaHarian;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.ISavingNominatif;
import org.simbiosis.ui.bprs.reporting.client.savingnominatif.SavingNominatif;

public class BprsReportingViewerFactoryImpl extends KembangClientFactoryImpl
		implements BprsReportingViewerFactory {

	static final SavingNominatif SAVING_NOMINATIF = new SavingNominatif();
	static final DepositNominatif DEPOSIT_NOMINATIF = new DepositNominatif();
	static final LoanNominatif LOAN_NOMINATIF = new LoanNominatif();
	static final LoanRemedial LOAN_REMEDIAL = new LoanRemedial();
	static final Neraca NERACA = new Neraca();
	static final NeracaHarian NERACA_HARIAN = new NeracaHarian();
	static final LabaRugi LABA_RUGI = new LabaRugi();
	static final LabaRugiHarian LABA_RUGI_HARIAN = new LabaRugiHarian();

	@Override
	public ISavingNominatif getSavingNominatif() {
		return SAVING_NOMINATIF;
	}

	@Override
	public IDepositNominatif getDepositNominatif() {
		return DEPOSIT_NOMINATIF;
	}

	@Override
	public INeraca getNeraca() {
		return NERACA;
	}

	@Override
	public ILabaRugi getLabaRugi() {
		return LABA_RUGI;
	}

	@Override
	public ILoanNominatif getLoanNominatif() {
		return LOAN_NOMINATIF;
	}

	@Override
	public INeracaHarian getNeracaHarian() {
		return NERACA_HARIAN;
	}

	@Override
	public ILabaRugiHarian getLabaRugiHarian() {
		return LABA_RUGI_HARIAN;
	}

	@Override
	public ILoanRemedial getLoanRemedial() {
		return LOAN_REMEDIAL;
	}

}
