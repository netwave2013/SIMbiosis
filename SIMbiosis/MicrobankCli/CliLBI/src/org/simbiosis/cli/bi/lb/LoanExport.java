package org.simbiosis.cli.bi.lb;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import org.simbiosis.microbank.LoanTransInfoDto;
import org.simbiosis.utils.MicrobankCoreClient;

public class LoanExport {

//	String host = "192.168.1.101";
	 String host = "localhost";
	int port = 8080;

	DecimalFormat df = new DecimalFormat("#,##0.00");
	Lending landing;

	String newLine = "\r\n";
	String kodeBank = "620113";
	String cabang = "001";
	int month = 10;
	int year = 2012;
	String[] endMonths = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };
	String bulan = "0" + month;
	String tahun = "" + year;
	String endDate = endMonths[month] + "-" + tahun;
	//
	String buffer = "";
	//
	MicrobankCoreClient jsonClient;

	public static void main(String[] args) {
		LoanExport le = new LoanExport();
		le.execute();
		le.createFile();
	}

	public LoanExport() {
		jsonClient = new MicrobankCoreClient(host, port);
		landing = new Lending(jsonClient, endDate, newLine);
	}

	String createLine(LoanTransInfoDto dto) {
		String line = dto.getCode() + "\t" + dto.getName() + "\t"
				+ df.format(dto.getOsPrincipal()) + "\t"
				+ df.format(dto.getOsMargin()) + "\t" + dto.getQuality() + "\t"
				+ df.format(dto.getGuarantee()) + "\t"
				+ df.format(landing.createPpap(dto));
		return line;
	}

	public void execute() {
		System.out.println("Retrieve...");
		landing.retrieve();
		//
		System.out.println("Create file...");
		for (LoanTransInfoDto dto : landing.getListMbh()) {
			buffer += createLine(dto) + newLine;
		}
		for (LoanTransInfoDto dto : landing.getListPby()) {
			buffer += createLine(dto) + newLine;
		}
		for (LoanTransInfoDto dto : landing.getListQdh()) {
			buffer += createLine(dto) + newLine;
		}
		for (LoanTransInfoDto dto : landing.getListMjs()) {
			buffer += createLine(dto) + newLine;
		}
	}

	public void createFile() {
		try {
			FileOutputStream fstream = new FileOutputStream("pembiayaan.csv");
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(
					fstream));
			writer.write(buffer);
			writer.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
