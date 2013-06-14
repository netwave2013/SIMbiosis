package org.simbiosis.cli.revsharing;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.cli.revsharing.lib.RevSharing;
import org.simbiosis.microbank.RevenueDto;
import org.simbiosis.microbank.RevenueSharingDto;
import org.simbiosis.utils.MicrobankCoreClient;

public class Calculate {

	//
	NumberFormat nf = NumberFormat.getInstance();
	//
//	String host = "192.168.1.101";
	String host = "localhost";
	int port = 8080;
	MicrobankCoreClient coreClient;
	// String url = "http://192.168.1.101:8080/JsonMain/";
	//
	int[] monthDays = { 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	String[] endMonths = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };
	//
	long company = 2;
	int month;
	int year;
	String beginDate;
	String endDate;
	int days;
	//
	RevSharing revSharing = null;

	public static void main(String[] args) {
		Calculate bagiHasil = new Calculate();
		bagiHasil.execute();
	}

	public Calculate() {
		Date now = new Date();
		SimpleDateFormat sMonth = new SimpleDateFormat("MM");
		SimpleDateFormat sYear = new SimpleDateFormat("yyyy");
		//
		month = Integer.parseInt(sMonth.format(now));
		year = Integer.parseInt(sYear.format(now));
		beginDate = "01-" + month + "-" + year;
		endDate = endMonths[month] + "-" + year;
		days = monthDays[month];
		//
		coreClient = new MicrobankCoreClient(host, port);
		revSharing = new RevSharing(coreClient, beginDate, days, endDate);
	}

	public void execute() {
		//
		System.out.println("Process revSharing data...");
		//
		revSharing.execute();
		//
		System.out.println("Create revenue...");
		sendRevenue();
		createRevenueFile();
		//
		System.out.println("Create revenue sharing...");
		sendRevenueSharing();
		createRevenueSharingFile();
	}

	void sendRevenue() {
		for (RevenueDto rev : revSharing.getRevenue()) {
			rev.setCompany(company);
			rev.setMonth(month);
			rev.setYear(year);
			try {
				ObjectMapper mapper = new ObjectMapper();
				StringWriter sw = new StringWriter();
				mapper.writeValue(sw, rev);
				String result = sw.toString();
				coreClient.sendRawData("saveRevenueItem", result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void sendRevenueSharing() {
		for (RevenueSharingDto rev : revSharing.getRevenueSharing()) {
			rev.setCompany(company);
			rev.setMonth(month);
			rev.setYear(year);
			try {
				ObjectMapper mapper = new ObjectMapper();
				StringWriter sw = new StringWriter();
				mapper.writeValue(sw, rev);
				String result = sw.toString();
				coreClient.sendRawData("saveRevenueSharingItem", result);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	void createRevenueFile() {
		int counter = 0;
		String buffer = "";
		for (RevenueDto rev : revSharing.getRevenue()) {
			counter++;
			//
			String line = "" + counter + "\t" + rev.getProductName() + "\t"
					+ nf.format(rev.getValue());
			buffer += line;
			if (!buffer.isEmpty())
				buffer += "\n";
		}
		//
		try {
			FileOutputStream fstream = new FileOutputStream("pendapatan.csv");
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

	void createRevenueSharingFile() {
		int counter = 0;
		String buffer = "";
		for (RevenueSharingDto dto : revSharing.getRevenueSharing()) {
			counter++;
			//
			String line = "" + counter + "\t" + dto.getProduct() + "\t"
					+ dto.getCode() + "\t" + dto.getName() + "\t"
					+ nf.format(dto.getStartValue()) + "\t"
					+ nf.format(dto.getEndValue()) + "\t"
					+ nf.format(dto.getAverageValue()) + "\t"
					+ nf.format(dto.getSharing()) + "\t"
					+ nf.format(dto.getTotalSharing()) + "\t"
					+ nf.format(dto.getCustomerSharing()) + "\t"
					+ nf.format(dto.getZakat()) + "\t"
					+ nf.format(dto.getTax());
			buffer += line;
			if (!buffer.isEmpty())
				buffer += "\n";
		}
		//
		try {
			FileOutputStream fstream = new FileOutputStream("bagihasil.csv");
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
