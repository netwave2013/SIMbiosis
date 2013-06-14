package org.simbiosis.cli.nominatif.creator;

import org.simbiosis.cli.nominatif.funding.DepositNominatif;
import org.simbiosis.cli.nominatif.funding.SavingNominatif;
import org.simbiosis.cli.nominatif.lending.LoanNominatif;
import org.simbiosis.utils.MicrobankCoreClient;
import org.simbiosis.utils.MicrobankReportClient;

public class Creator {

//	String host = "192.168.1.101";
	 String host = "localhost";
	int port = 8080;
	String strDate;

	MicrobankCoreClient coreClient;
	MicrobankReportClient reportClient;

	public Creator(String strDate) {
		this.strDate = strDate;
		coreClient = new MicrobankCoreClient(host, port);
		reportClient = new MicrobankReportClient(host, port);
	}

	public void execute() {
		System.out.println("Tanggal : " + strDate);
		System.out.println("Generate saving nominatif...");
		SavingNominatif savingReport = new SavingNominatif(host, port, strDate);
		savingReport.execute();
		System.out.println("Generate deposit nominatif...");
		DepositNominatif depositReport = new DepositNominatif(host, port,
				strDate);
		depositReport.execute();
		System.out.println("Generate loan nominatif...");
		LoanNominatif loanReport = new LoanNominatif(coreClient, reportClient,
				strDate);
		loanReport.execute();
	}

}
