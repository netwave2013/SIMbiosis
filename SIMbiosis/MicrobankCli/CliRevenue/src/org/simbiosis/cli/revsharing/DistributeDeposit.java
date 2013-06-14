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
import org.simbiosis.microbank.DepositDto;
import org.simbiosis.microbank.DepositProductDto;
import org.simbiosis.microbank.DepositRevSharingDto;
import org.simbiosis.microbank.SavingProductDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.utils.DateUtils;
import org.simbiosis.utils.MicrobankCoreClient;

public class DistributeDeposit {

	//
//	String host = "192.168.1.101";
	 String host = "localhost";
	int port = 8080;
	MicrobankCoreClient coreClient;
	//
	SimpleDateFormat sdf = new SimpleDateFormat("MM;yyyy");
	NumberFormat nf = NumberFormat.getInstance();
	//
	String period = "";
	String ref = "";
	String strDate = "";
	//
	Map<Long, SavingProductDto> spMap = new HashMap<Long, SavingProductDto>();
	Map<Long, DepositProductDto> dpMap = new HashMap<Long, DepositProductDto>();

	public static void main(String[] args) {
		DistributeDeposit distribusi = new DistributeDeposit();
		distribusi.execute();
	}

	public DistributeDeposit() {
		Date now = new Date();
		String strPer = sdf.format(now);
		String[] arrPer = strPer.split(";");
		if (Integer.parseInt(arrPer[0]) == 1) {
			int year = Integer.parseInt(arrPer[1]);
			year--;
			period = "12/" + year;
			ref = "BHS12" + year;
		} else {
			period = arrPer[0] + "/" + arrPer[1];
			ref = "BHS" + arrPer[0] + arrPer[1];
		}
		//
		strDate = DateUtils.dateToStr(now);
		//
		coreClient = new MicrobankCoreClient(host, port);
		coreClient.login("akunpus", "akunpus");
	}

	void execute() {
		//
		String data = coreClient.sendRawData("listDepositRevSharing", strDate);
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<DepositRevSharingDto> result = mapper.readValue(data,
					TypeFactory.collectionType(ArrayList.class,
							DepositRevSharingDto.class));
			for (DepositRevSharingDto rs : result) {
				System.out.println("data = " + rs.getDeposit() + "=="
						+ rs.getDate() + "==" + rs.getSaving() + "=="
						+ rs.getCustomerSharing());
				saveSavingTrans(rs);
				saveDepositRevSharingStatus(rs.getId(), 1);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			coreClient.logout();
		}
	}

	SavingTransactionDto createSavingTrans(Date date, long saving,
			double value, String ref, String description, int direction) {
		SavingTransactionDto transDto = new SavingTransactionDto();
		transDto.setDate(date);
		transDto.setSaving(saving);
		transDto.setRefCode(ref);
		transDto.setDescription(description);
		transDto.setValue(value);
		transDto.setDirection(direction);
		return transDto;
	}

	void saveDepositRevSharingStatus(long id, int status) {
		coreClient.sendRawData("saveDepositRevSharingStatus", "" + id + ";"
				+ status);
	}

	DepositDto getDeposit(long id) {
		String data = coreClient.sendRawData("getDeposit", "" + id);
		try {
			//
			ObjectMapper mapper = new ObjectMapper();
			DepositDto si = mapper.readValue(data, DepositDto.class);
			return si;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	void saveSavingTrans(DepositRevSharingDto rs) {
		try {
			DepositDto deposit = getDeposit(rs.getDeposit());
			//
			String coaCadBaghas = "727";
			String coaZakat = "680";
			String coaPPHDeposit = "307";
			//
			SavingTransactionDto transBasil = createSavingTrans(rs.getDate(),
					rs.getSaving(), rs.getCustomerSharing(), ref,
					"BAHAS/BONUS " + period, 1);
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, transBasil);
			sendRawData("saveSavingJournalTrans", coaCadBaghas, sw.toString());
			//
			if (deposit.getZakat() == 1) {
				SavingTransactionDto transZakat = createSavingTrans(
						rs.getDate(), rs.getSaving(), rs.getZakat(), ref,
						"ZAKAT " + period, 2);
				mapper = new ObjectMapper();
				sw = new StringWriter();
				mapper.writeValue(sw, transZakat);
				sendRawData("saveSavingJournalTrans", coaZakat, sw.toString());
			}
			//
			if (rs.getTax() > 0) {
				SavingTransactionDto transPajak = createSavingTrans(
						rs.getDate(), rs.getSaving(), rs.getTax(), ref, "PPH "
								+ period, 2);
				mapper = new ObjectMapper();
				sw = new StringWriter();
				mapper.writeValue(sw, transPajak);
				sendRawData("saveSavingJournalTrans", coaPPHDeposit,
						sw.toString());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	void sendRawData(String action, String id, String data) {
		coreClient.sendRawData(action, id, "", data);
	}
}
