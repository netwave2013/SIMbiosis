package org.simbiosis.cli.nominatif.creator;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.simbiosis.cli.nominatif.lending.LoanNominatif;
import org.simbiosis.utils.MicrobankCoreClient;
import org.simbiosis.utils.MicrobankReportClient;

public class CreatorOs {

//	String host = "192.168.1.101";
	String host = "localhost";
	int port = 8080;
	String strDate;

	MicrobankCoreClient coreClient;
	MicrobankReportClient reportClient;

	String[] endMonths = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };

	public static void main(String[] args) {
		CreatorOs creator = new CreatorOs();
		creator.execute();
	}

	public CreatorOs() {
		Date now = new Date();
		SimpleDateFormat sMonth = new SimpleDateFormat("MM");
		SimpleDateFormat sYear = new SimpleDateFormat("yyyy");
		int month = Integer.parseInt(sMonth.format(now));
		int year = Integer.parseInt(sYear.format(now));
		strDate = endMonths[month] + "-" + year;
		coreClient = new MicrobankCoreClient(host, port);
		reportClient = new MicrobankReportClient(host, port);
	}

	public void execute() {
		System.out.println("Tanggal : " + strDate);
		System.out.println("Generate loan remedial...");
		LoanNominatif loanReport = new LoanNominatif(coreClient, reportClient,
				strDate);
		loanReport.execute();
	}

}
