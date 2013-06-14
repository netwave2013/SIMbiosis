package org.simbiosis.cli.nominatif.lending;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.microbank.LoanTransInfoDto;
import org.simbiosis.utils.MicrobankCoreClient;
import org.simbiosis.utils.MicrobankReportClient;

public class LoanNominatif {

	String strDate;
	MicrobankCoreClient jsonMain = null;
	MicrobankReportClient jsonReport = null;

	public LoanNominatif(MicrobankCoreClient jsonMain,
			MicrobankReportClient jsonReport, String strDate) {
		super();
		this.jsonMain = jsonMain;
		this.jsonReport = jsonReport;
		this.strDate = strDate;
	}

	public void execute() {
		createLoan();
	}

	void createLoan() {
		ObjectMapper mapper = new ObjectMapper();
		jsonReport.sendRawData("deleteDailyLoan", "2;" + strDate);
		String data = jsonMain.sendRawData("listLoanTransactionPayd", "0;"
				+ strDate);
		try {
			List<LoanTransInfoDto> infos = mapper.readValue(data, TypeFactory
					.collectionType(ArrayList.class, LoanTransInfoDto.class));
			// int i = 1;
			for (LoanTransInfoDto info : infos) {
				// System.out.println(i++ + ". Output : " + info.getId() + "="
				// + info.getCode());
				System.out.print(".");
				StringWriter sw = new StringWriter();
				mapper.writeValue(sw, info);
				jsonReport.sendRawData("createDailyLoan", strDate,
						sw.toString());
			}
			System.out.println();
		} catch (IOException e) {
			System.out.println(data);
			e.printStackTrace();
		}
	}

}
