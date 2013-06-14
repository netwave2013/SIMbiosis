package org.simbiosis.cli.bi.lb;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

import org.simbiosis.utils.MicrobankCoreClient;

public class Labul {

//	String host = "192.168.1.101";
	String host = "localhost";
	int port = 8080;

	FileCreator fileCreator;
	LapKeu lapKeu;
	Lending landing;
	Funding funding;
	DecimalFormat df = new DecimalFormat("00");
	String newLine = "\r\n";
	String kodeBank = "620113";
	String cabang = "001";
	int month = 3;
	int year = 2013;
	String[] endMonths = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };
	String bulan = "";
	String tahun = "";
	String endDate = "";
	//
	String buffer = "";

	MicrobankCoreClient jsonClient;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Labul labul = new Labul();
		labul.retrieve();
		labul.create();
		labul.createFile();
		// System.out.println(labul.getBuffer());
	}

	//
	public Labul() {
		jsonClient = new MicrobankCoreClient(host, port);
		bulan = df.format(month);
		tahun = "" + year;
		endDate = endMonths[month] + "-" + tahun;
		fileCreator = new FileCreator(kodeBank, cabang, bulan, tahun, newLine);
		lapKeu = new LapKeu(jsonClient, month, year, newLine);
		funding = new Funding(jsonClient, endDate, newLine);
		landing = new Lending(jsonClient, endDate, newLine);
	}

	String getBuffer() {
		return buffer;
	}

	public void createFile() {
		try {
			FileOutputStream fstream = new FileOutputStream(kodeBank + cabang
					+ bulan + tahun + ".exp");
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

	public void retrieve() {
		System.out.println("Retrieving...");
		System.out.println(" - lapKeu");
		lapKeu.retrieve();
		System.out.println(" - lending");
		landing.retrieve();
		System.out.println(" - funding");
		funding.retrieve();
	}

	public void create() {
		System.out.println("Buat LB");
		System.out.println(" - Buat LB header");
		buffer += fileCreator.createHeader();
		buffer += fileCreator.createHeaderBank();
		System.out.println(" - Buat form 01");
		buffer += lapKeu.create01();
		System.out.println(" - Buat form 02");
		buffer += lapKeu.create02();
		System.out.println(" - Buat form 03");
		buffer += landing.create03();
		System.out.println(" - Buat form 04");
		buffer += landing.create04();
		System.out.println(" - Buat form 07");
		buffer += landing.create07();
		System.out.println(" - Buat form 09");
		buffer += landing.create09();
		System.out.println(" - Buat form 12");
		buffer += funding.create12();
		System.out.println(" - Buat form 13");
		buffer += funding.create13();
		System.out.println(" - Buat form 14");
		buffer += funding.create14();
		System.out.println(" - Buat form 20");
		buffer += landing.create20();
	}

}
