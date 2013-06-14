package org.simbiosis.cli.loan;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.microbank.LoanTransInfoDto;
import org.simbiosis.microbank.LoanTransactionDto;
import org.simbiosis.utils.DateUtils;
import org.simbiosis.utils.MicrobankCoreClient;

public class CorrectBill {

//	String host = "192.168.1.101";
	String host = "localhost";
	int port = 8080;

	String strEndDate = "";
	SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");

	MicrobankCoreClient jsonClient;
	Map<Long, Long> schedMaps = new HashMap<Long, Long>();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		CorrectBill cb = new CorrectBill();
		cb.execute();
	}

	public CorrectBill() {
		jsonClient = new MicrobankCoreClient(host, port);
		//
		Date now = new Date();
		strEndDate = DateUtils.dateToStr(now);
	}

	public void execute() {
		// Ambil semua id pembiayaan
		System.out.println("List loan transaction payd");
		List<LoanTransInfoDto> payds = listLoanTransactionPayd(strEndDate);
		System.out.println("Hitung sisa pembayaran");
		for (LoanTransInfoDto trans : payds) {
			Long maxSched = 0L;
			// ambil angsuran yang sudah dibayar
			if (trans.getPaydMargin() + trans.getPaydPrincipal() > 0.01) {
				double sisaPokok = trans.getPaydPrincipal();
				// double sisaMargin = trans.getMargin();
				// ambil semua schedule
				List<LoanScheduleDto> scheds = listLoanSchedule(trans.getId());
				String schedIds = "";
				for (LoanScheduleDto sched : scheds) {
					if (sisaPokok >= sched.getPrincipal()) {
						if (sched.getId() > maxSched) {
							maxSched = sched.getId();
							//
							if (!schedIds.isEmpty())
								schedIds += ";";
							schedIds += maxSched;
						}
						sisaPokok -= sched.getPrincipal();
					}
				}
			}
			schedMaps.put(trans.getId(), maxSched);
		}
		//
		System.out.println("Update jadwal");
		updateScheds();
	}

	void updateScheds() {
		String data = "";
		for (Long idLoan : schedMaps.keySet()) {
			// System.out.println(data);
			Long maxScheds = schedMaps.get(idLoan);
			if (!data.isEmpty())
				data += ";";
			data += idLoan + ";" + maxScheds;
		}
		jsonClient.sendRawData("updatePaydSchedule", data);
		// System.out.println(data);
	}

	LoanTransactionDto getSumLoanTransaction(String id) {
		LoanTransactionDto result = null;
		String data = jsonClient.sendRawData("getSumLoanTransaction", id + ";"
				+ strEndDate);
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.readValue(data, LoanTransactionDto.class);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	List<LoanScheduleDto> listLoanSchedule(Long id) {
		List<LoanScheduleDto> scheds = new ArrayList<LoanScheduleDto>();
		String data = jsonClient.sendRawData("listLoanSchedule", id.toString());
		ObjectMapper mapper = new ObjectMapper();
		try {
			scheds = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, LoanScheduleDto.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scheds;
	}

	List<LoanTransInfoDto> listLoanTransactionPayd(String date) {
		List<LoanTransInfoDto> scheds = new ArrayList<LoanTransInfoDto>();
		String data = jsonClient.sendRawData("listLoanTransactionPayd", "0;"
				+ date);
		ObjectMapper mapper = new ObjectMapper();
		try {
			scheds = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, LoanTransInfoDto.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return scheds;
	}

}
