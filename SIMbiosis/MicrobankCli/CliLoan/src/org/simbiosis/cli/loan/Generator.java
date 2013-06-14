package org.simbiosis.cli.loan;

import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.cli.loan.lib.LoanBillingDv;
import org.simbiosis.microbank.LoanInformationDto;
import org.simbiosis.microbank.LoanScheduleDto;
import org.simbiosis.microbank.LoanTransactionDto;
import org.simbiosis.utils.MicrobankCoreClient;

public class Generator {

	String dateBegin = "";
	String dateEnd = "";

	//
	Object[] data1 = { "0014200204", 600000 };
	Object[] data2 = { "0014200215", 500000 };
	Object[] data3 = { "0034200008", 150000 };
	Object[] data4 = { "0014300481", 300000 };
	Object[] data5 = { "0014300492", 650000 };
	Object[] data6 = { "0014300612", 300000 };
	Object[] data7 = { "0014300624", 800000 };
	Object[] data8 = { "0014300625", 155000 };
	Object[] data9 = { "0014300636", 1111000 };
	Object[] data10 = { "0014300657", 400000 };
	Object[] data11 = { "0017100012", 1000000 };
	Object[] data12 = { "0024300026", 500000 };
	Object[] data13 = { "0024300035", 1750000 };
	Object[] data14 = { "0029000001", 675000 };
	Object[] data15 = { "0034300002", 2500000 };
	Object[] data16 = { "0034300040", 500000 };
	Object[] data17 = { "0034300041", 500000 };
	Object[] data18 = { "0034300043", 800000 };
	Object[] data19 = { "0034300044", 500000 };
	Object[] data20 = { "0034300047", 517000 };
	Object[] data21 = { "0034300055", 1523000 };
	Object[] data22 = { "0044300001", 400000 };
	Object[] data23 = { "0044300002", 300000 };
	Object[] data24 = { "0024200107", 2500000 };
	Object[] data25 = { "0014100501", 250000 };
	Object[] data26 = { "0024100018", 1750000 };
	Object[] data27 = { "0014300700", 550000 };
	Object[] data28 = { "0014300701", 550000 };
	Object[] data29 = { "0014300706", 300000 };
	Object[] data30 = { "0014300710", 500000 };
	Object[] data31 = { "0024100021", 2000000 };
	Object[] data32 = { "0024100022", 1750000 };
	Object[] data33 = { "0034300112", 225000 };
	Object[] data34 = { "0024300126", 1500000 };
	Object[] data35 = { "0034300117", 850000 };
	Object[] data36 = { "0034200040", 350000 };
	Object[] data37 = { "0014300740", 550000 };
	Object[] data38 = { "0014300743", 550000 };
	Object[] data39 = { "0034300126", 500000 };

	Object[] datas = { data1, data2, data3, data4, data5, data6, data7, data8,
			data9, data10, data11, data12, data13, data14, data15, data16,
			data17, data18, data19, data20, data21, data22, data23, data24,
			data25, data26, data27, data28, data29, data30, data31, data32,
			data33, data34, data35, data36, data37, data38, data39 };

	PayBilling payBilling = null;
	MicrobankCoreClient jsonMain;

	Map<Long, Double> savingMap = new HashMap<Long, Double>();
	Map<Long, LoanBillingDv> loanMap = null;
	Map<String, Integer> blockirMap = new HashMap<String, Integer>();

	public Generator(MicrobankCoreClient jsonMain) {
		this.jsonMain = jsonMain;
		loanMap = new HashMap<Long, LoanBillingDv>();
		payBilling = new PayBilling(jsonMain);
	}

	public Generator(MicrobankCoreClient jsonMain,
			Map<Long, LoanBillingDv> loanBilling) {
		this.jsonMain = jsonMain;
		this.loanMap = loanBilling;
		payBilling = new PayBilling(jsonMain);
	}

	public void execute(String beginDate, String endDate) {
		this.dateBegin = beginDate;
		this.dateEnd = endDate;
		//
		createBlockir();
		//
		fillLoanBilling();
		listLoanSchedule();
		markHasPayd();
		//
		System.out.println("Jumlah data : " + loanMap.values().size());
	}

	void createBlockir() {
		for (Object data : datas) {
			Object[] item = (Object[]) data;
			String loan = (String) item[0];
			Integer value = (Integer) item[1];
			blockirMap.put(loan, value);
		}
	}

	double getBlockir(String code) {
		Integer blockir = blockirMap.get(code);
		return (blockir != null) ? blockir : 0;
	}

	public void payBilling() {
		int counter = 1;
		NumberFormat nf = NumberFormat.getInstance();
		for (LoanBillingDv billing : loanMap.values()) {
			Double val = savingMap.get(billing.getSaving());
			Double blockir = getBlockir(billing.getCode());
			billing.setBillable((val > billing.getBill() + blockir));
			if (billing.isBillable() && billing.getPrincipal() > 0.1
					&& billing.getMargin() > 0.1) {
				counter++;
				//
				//payBilling.sendPay(billing);
				//
				String line = "" + counter + "\t" + billing.getProduct() + "\t"
						+ billing.getCode() + "\t" + billing.getName() + "\t"
						+ nf.format(billing.getPrincipal()) + "\t"
						+ nf.format(billing.getMargin()) + "\t"
						+ nf.format(billing.getBill()) + "\t"
						+ nf.format(billing.getSavingBallance());
				System.out.println(line);
				//
				val -= (billing.getBill() + blockir);
				savingMap.put(billing.getSaving(), val);
			}
		}
	}

	public String createText() {
		NumberFormat nf = NumberFormat.getInstance();
		int counter = 0;
		String buffer = "";
		for (LoanBillingDv billing : loanMap.values()) {
			if (billing.isBillable() && billing.getPrincipal() > 0.1
					&& billing.getMargin() > 0.1) {
				counter++;
				//
				String line = "" + counter + "\t" + billing.getProduct() + "\t"
						+ billing.getCode() + "\t" + billing.getName() + "\t"
						+ billing.getCount() + "\t"
						+ nf.format(billing.getPrincipal()) + "\t"
						+ nf.format(billing.getMargin()) + "\t"
						+ nf.format(billing.getBill()) + "\t"
						+ nf.format(billing.getSavingBallance());
				buffer += line;
				if (!buffer.isEmpty())
					buffer += "\n";
			}
		}
		return buffer;
	}

	void markHasPayd() {
		String param = "2;" + dateBegin + ";" + dateEnd;
		String data = jsonMain.sendRawData("listLoanTransactionByRange", param);
		ObjectMapper mapper = new ObjectMapper();
		List<LoanTransactionDto> scheds = new ArrayList<LoanTransactionDto>();
		try {
			scheds = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, LoanTransactionDto.class));
			for (LoanTransactionDto sched : scheds) {
				LoanBillingDv billing = loanMap.get(sched.getLoan());
				if (billing != null)
					billing.setBillable(false);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void listLoanSchedule() {
		String param = "2;" + dateEnd;
		// String data = sendRawData("listLoanScheduleByRange", param);
		String data = jsonMain.sendRawData("listLoanBill", param);
		ObjectMapper mapper = new ObjectMapper();
		List<LoanScheduleDto> scheds = new ArrayList<LoanScheduleDto>();
		try {
			scheds = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, LoanScheduleDto.class));
			for (LoanScheduleDto sched : scheds) {
				LoanBillingDv billing = loanMap.get(sched.getLoan());
				if (billing != null && billing.isBillable()) {
					if ((billing.getBill() > 0)
							&& (billing.getBill() + sched.getTotal() <= billing
									.getSavingBallance())) {
						billing.setCount(billing.getCount() + 1);
						billing.setBill(billing.getBill() + sched.getTotal());
						billing.setPrincipal(billing.getPrincipal()
								+ sched.getPrincipal());
						billing.setMargin(billing.getMargin()
								+ sched.getMargin());
					}
					if (billing.getBill() <= 0) {
						billing.setCount(1);
						billing.setBill(sched.getTotal());
						billing.setPrincipal(sched.getPrincipal());
						billing.setMargin(sched.getMargin());
						billing.setBillable(billing.getBill() <= billing
								.getSavingBallance());
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	LoanInformationDto getLoanInfo(String id) {
		ObjectMapper mapper = new ObjectMapper();
		String strInfo = jsonMain.sendRawData("getLoanInfo", id);
		LoanInformationDto info = null;
		try {
			info = mapper.readValue(strInfo, LoanInformationDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	LoanTransactionDto getLoanTransInfo(String id, String date) {
		ObjectMapper mapper = new ObjectMapper();
		String strLoanTrans = jsonMain.sendRawData("getSumLoanTransaction", id
				+ ";" + date);
		LoanTransactionDto loanTransDto = null;
		try {
			loanTransDto = mapper.readValue(strLoanTrans,
					LoanTransactionDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return loanTransDto;
	}

	double getWithdrawalSavingBallance(long id, String date) {
		String strBallance = jsonMain.sendRawData(
				"getWithdrawalSavingBallance", id + ";" + date);
		Double ballance = Double.parseDouble(strBallance);
		return ballance == null ? 0 : ballance;
	}

	void fillLoanBilling() {
		String data = jsonMain.sendRawData("listLoanId", dateBegin);
		String[] ids = data.split(";");
		for (String id : ids) {
			if (!id.isEmpty()) {
				LoanInformationDto info = getLoanInfo(id);
				LoanTransactionDto trans = getLoanTransInfo(id, dateEnd);
				LoanBillingDv loanBilling = new LoanBillingDv();
				loanBilling.setId(info.getId());
				loanBilling.setCode(info.getCode());
				loanBilling.setSchema(info.getSchema());
				loanBilling.setProduct(info.getProductName());
				loanBilling.setName(info.getName());
				loanBilling.setSaving(info.getSaving());
				double blockir = getBlockir(info.getCode());
				double sb = getWithdrawalSavingBallance(info.getSaving(),
						dateEnd);
				Double savingBallance = savingMap.get(info.getSaving());
				if (savingBallance == null) {
					savingMap.put(info.getSaving(), sb);
				}
				loanBilling.setSavingBallance(sb - blockir);
				loanBilling.setPrincipalPayd(trans.getPrincipal());
				loanBilling.setMarginPayd(trans.getMargin());
				loanBilling.setBillable(trans.getPrincipal() < info
						.getPrincipal());
				loanMap.put(info.getId(), loanBilling);
			}
		}
	}

}
