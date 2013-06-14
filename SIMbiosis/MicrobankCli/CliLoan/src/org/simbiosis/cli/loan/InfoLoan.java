package org.simbiosis.cli.loan;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.microbanking.reporting.LoanRpt;
import org.simbiosis.utils.MicrobankReportClient;

public class InfoLoan {

//	String host = "192.168.1.101";
	 String host = "localhost";
	int port = 8080;
	SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");

	MicrobankReportClient jsonClient;
	Date now = new Date();

	public static void main(String[] args) {
		InfoLoan cb = new InfoLoan();
		cb.execute();
	}

	public InfoLoan() {
		jsonClient = new MicrobankReportClient(host, port);
		//
	}

	public void execute() {
		double totalPrincipal = 0;
		double totalPrincipalNett = 0;
		double totalPrincipalGross = 0;
		List<LoanRpt> loans = listLoan();
		// Hitung nilai2
		System.out.println("Hitung nilai2...(" + loans.size() + " pembiayaan)");
		for (LoanRpt loan : loans) {
			totalPrincipal += loan.getOsPrincipal();
			if (loan.getQuality() > 1) {
				double jaminan = (0.5 * loan.getGuarantee());
				totalPrincipalNett += (loan.getOsPrincipal() - ((jaminan > loan
						.getOsPrincipal()) ? 0 : jaminan));
				totalPrincipalGross += loan.getOsPrincipal();
			}
		}
		// Hitung semua
		System.out.println("Hitung npf...");
		System.out.println("- Nett : "
				+ (totalPrincipalNett * 100 / totalPrincipal));
		System.out.println("- Gross : "
				+ (totalPrincipalGross * 100 / totalPrincipal));
	}

	List<LoanRpt> listLoan() {
		List<LoanRpt> loans = new ArrayList<LoanRpt>();
		String data = jsonClient.sendRawData("listDailyLoan",
				"2;0;" + sdf.format(now));
		ObjectMapper mapper = new ObjectMapper();
		try {
			loans = mapper.readValue(data,
					TypeFactory.collectionType(ArrayList.class, LoanRpt.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return loans;
	}

}
