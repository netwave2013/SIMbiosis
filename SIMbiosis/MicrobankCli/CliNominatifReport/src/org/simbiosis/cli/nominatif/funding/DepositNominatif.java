package org.simbiosis.cli.nominatif.funding;

import org.simbiosis.utils.MicrobankCoreClient;
import org.simbiosis.utils.MicrobankReportClient;

public class DepositNominatif {

	String strDate;
	MicrobankCoreClient jsonMain = null;
	MicrobankReportClient jsonReport = null;

	public DepositNominatif(String host, int port, String strDate) {
		super();
		jsonMain = new MicrobankCoreClient(host, port);
		jsonReport = new MicrobankReportClient(host, port);
		this.strDate = strDate;
	}

	public void execute() {
		createDeposit();
	}

	private void createDeposit() {
		jsonReport.sendRawData("deleteDailyDeposit", "2;" + strDate);
		String data = jsonMain.sendRawData("listDepositId", strDate);
		String[] strIds = data.split(";");
		// int i = 1;
		for (String strId : strIds) {
			// System.out.println(i++ + ". Output : " + strId);
			System.out.print(".");
			jsonReport.sendRawData("createDailyDeposit", strId + ";" + strDate);
		}
		System.out.println();
	}

}
