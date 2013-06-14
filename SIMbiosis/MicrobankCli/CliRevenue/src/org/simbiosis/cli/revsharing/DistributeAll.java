package org.simbiosis.cli.revsharing;

import java.io.IOException;
import java.io.StringWriter;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.DepositProductDto;
import org.simbiosis.microbank.DepositRevSharingDto;
import org.simbiosis.microbank.RevenueSharingDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.utils.DateUtils;
import org.simbiosis.utils.MicrobankCoreClient;

public class DistributeAll {

	//
//	String host = "192.168.1.101";
	String host = "localhost";
	int port = 8080;
	MicrobankCoreClient coreClient;
	//
	NumberFormat nf = NumberFormat.getInstance();
	//
	String[] endMonths = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };
	//
	int month;
	int nextMonth;
	int year;
	String endDate;
	Date date = null;
	//
	String period;
	String ref = "BHS";
	//
	//
	Map<Long, SavingProductDto> spMap = new HashMap<Long, SavingProductDto>();
	Map<Long, DepositProductDto> dpMap = new HashMap<Long, DepositProductDto>();

	public static void main(String[] args) {
		DistributeAll distribusi = new DistributeAll();
		distribusi.execute();
	}

	public DistributeAll() {
		Date now = new Date();
		SimpleDateFormat sMonth = new SimpleDateFormat("MM");
		SimpleDateFormat sYear = new SimpleDateFormat("yyyy");
		//
		month = Integer.parseInt(sMonth.format(now));
		year = Integer.parseInt(sYear.format(now));
		nextMonth = month + 1;
		endDate = endMonths[month] + "-" + year;
		//
		period = month + "/" + year + " - ";
		ref += month + "" + year;
		//
		date = DateUtils.strToDate(endDate);
		//
		coreClient = new MicrobankCoreClient(host, port);
		coreClient.login("akunpus", "akunpus");
	}

	void execute() {
		//
		String data = coreClient.sendRawData("listRevenueSharing", month + ";"
				+ year + ";0");
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<RevenueSharingDto> result = mapper.readValue(data, TypeFactory
					.collectionType(ArrayList.class, RevenueSharingDto.class));
			for (RevenueSharingDto rs : result) {
				System.out.println(rs.getId());
				if (rs.getType() == 1) {
					if (rs.getCustomerSharing() > 0)
						saveSavingTrans(rs);
				} else {
					saveDepositRevSharing(rs);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			coreClient.logout();
		}
	}

	SavingTransactionDto createSavingTrans(long saving, double value,
			String ref, String description, int direction) {
		SavingTransactionDto transDto = new SavingTransactionDto();
		transDto.setDate(date);
		transDto.setTimestamp(new Date());
		transDto.setSaving(saving);
		transDto.setRefCode(ref);
		transDto.setDescription(description);
		transDto.setValue(value);
		transDto.setDirection(direction);
		return transDto;
	}

	SavingInformationDto getSavingInformation(long id) {
		String data = coreClient.sendRawData("getSavingInformation", "" + id);
		try {
			//
			ObjectMapper mapper = new ObjectMapper();
			SavingInformationDto si = mapper.readValue(data,
					SavingInformationDto.class);
			return si;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	void saveSavingTrans(RevenueSharingDto rs) {
		try {
			SavingInformationDto si = getSavingInformation(rs.getSaving());
			//
			SavingTransactionDto transBasil = createSavingTrans(rs.getSaving(),
					rs.getCustomerSharing(), ref, "BAHAS/BONUS " + period, 1);
			long coa = si.getCoa2();
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, transBasil);
			System.out.println(sw.toString());
			sendRawData("saveSavingJournalTrans", "" + coa, sw.toString());
			//
			if (rs.getZakat() > 0) {
				SavingTransactionDto transZakat = createSavingTrans(
						rs.getSaving(), rs.getZakat(), ref, "ZAKAT " + period,
						2);
				mapper = new ObjectMapper();
				sw = new StringWriter();
				mapper.writeValue(sw, transZakat);
				System.out.println(sw.toString());
				sendRawData("saveSavingJournalTrans", "680", sw.toString());
			}
			//
			if (rs.getTax() > 0) {
				SavingTransactionDto transPajak = createSavingTrans(
						rs.getSaving(), rs.getTax(), ref, "PPH " + period, 2);
				mapper = new ObjectMapper();
				sw = new StringWriter();
				mapper.writeValue(sw, transPajak);
				System.out.println(sw.toString());
				sendRawData("saveSavingJournalTrans", "309", sw.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void saveDepositRevSharing(RevenueSharingDto rs) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			//
			String strDi = coreClient.sendRawData("getDepositInformation", ""
					+ rs.getAccount());
			DepositInformationDto di = mapper.readValue(strDi,
					DepositInformationDto.class);
			//
			SimpleDateFormat sdf = new SimpleDateFormat("dd");
			String day = sdf.format(di.getBegin());
			if (Integer.parseInt(day) > 30)
				day = "30";
			DepositRevSharingDto drs = new DepositRevSharingDto();
			drs.setCompany(rs.getCompany());
			drs.setDeposit(rs.getAccount());
			drs.setDate(DateUtils.strToDate(day + "-" + nextMonth + "-" + year));
			drs.setCustomerSharing(rs.getCustomerSharing());
			drs.setZakat(rs.getZakat());
			drs.setTax(rs.getTax());
			drs.setSaving(rs.getSaving());
			drs.setStatus(0);
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, drs);
			coreClient.sendRawData("saveDepositRevSharing", sw.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	void sendRawData(String action, String id, String data) {
		coreClient.sendRawData(action, id, "", data);
	}

}
