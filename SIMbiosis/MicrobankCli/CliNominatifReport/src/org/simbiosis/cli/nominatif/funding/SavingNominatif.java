package org.simbiosis.cli.nominatif.funding;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.microbank.SavingTransInfoDto;
import org.simbiosis.utils.MicrobankCoreClient;
import org.simbiosis.utils.MicrobankReportClient;

public class SavingNominatif {

	String strDate;
	MicrobankCoreClient jsonMain = null;
	MicrobankReportClient jsonReport = null;

	public SavingNominatif(String host, int port, String strDate) {
		super();
		jsonMain = new MicrobankCoreClient(host, port);
		jsonReport = new MicrobankReportClient(host, port);
		this.strDate = strDate;
	}

	public void execute() {
		createSaving();
	}

	private void createSaving() {
		ObjectMapper mapper = new ObjectMapper();
		jsonReport.sendRawData("deleteDailySaving", "2;" + strDate);
		String data = jsonMain.sendRawData("listSavingBallance", "2;0;"
				+ strDate);
		try {
			List<SavingTransInfoDto> objects = mapper.readValue(data,
					TypeFactory.collectionType(ArrayList.class,
							SavingTransInfoDto.class));
			// int i = 1;
			for (SavingTransInfoDto info : objects) {
				// System.out.println(i++ + ". Output : " + info.getId() + "="
				// + info.getCode());
				System.out.print(".");
				StringWriter sw = new StringWriter();
				mapper.writeValue(sw, info);
				jsonReport.sendRawData("createDailySaving", strDate,
						sw.toString());
			}
			System.out.println();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
