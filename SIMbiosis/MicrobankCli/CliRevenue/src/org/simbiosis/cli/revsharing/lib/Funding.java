package org.simbiosis.cli.revsharing.lib;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.RevenueSharingDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.utils.MicrobankCoreClient;

public class Funding {

	String beginDate;
	String endDate;
	SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
	int days;

	Map<String, RevenueSharingDto> revSharingMap = null;
	Map<Long, Double> taxMap = null;
	double totalAverageBallance = 0;
	double totalWadiahAverageBallance = 0;

	MicrobankCoreClient jsonClient;

	public Funding(MicrobankCoreClient jsonClient, String beginDate, String endDate,
			int days, Map<String, RevenueSharingDto> revSharingMap,
			Map<Long, Double> taxMap) {
		this.revSharingMap = revSharingMap;
		this.taxMap = taxMap;
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.days = days;
		this.jsonClient = jsonClient;
	}

	public void execute() {
		System.out.println("- initBallances");
		initBallances();
		System.out.println("- fillSavingData");
		fillSavingData();
		System.out.println("- fillDepositData");
		fillDepositData();
	}

	public double getTotalAverageBallance() {
		return totalAverageBallance;
	}

	public double getTotalWadiahAverageBallance() {
		return totalWadiahAverageBallance;
	}

	Double[] ballances = new Double[32];

	void initBallances() {
		for (int i = 0; i <= days; i++)
			ballances[i] = 0D;
	}

	void setBallances(int beginDay, double ballance) {
		for (int i = beginDay; i <= days; i++) {
			ballances[i] = ballance;
		}
	}

	double calcTotalBallance(int endDay) {
		double totalBallance = 0;
		for (int i = endDay; i >= 1; i--) {
			totalBallance += ballances[i];
		}
		return totalBallance;
	}

	double createSavingAverageBallance(long id, double startBallance) {
		//
		setBallances(1, startBallance);
		//
		String param = "" + id + ";" + beginDate + ";" + endDate;
		String data = jsonClient.sendRawData("listSavingTransRange", param);
		ObjectMapper mapper = new ObjectMapper();
		List<SavingTransactionDto> scheds = new ArrayList<SavingTransactionDto>();
		try {
			scheds = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, SavingTransactionDto.class));
			for (SavingTransactionDto sched : scheds) {
				int day = Integer.parseInt(dayFormat.format(sched.getDate()));
				double nextBallance = ballances[day];
				if (sched.getDirection() == 1) {
					nextBallance += sched.getValue();
				} else {
					nextBallance -= sched.getValue();
				}
				setBallances(day, nextBallance);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return calcTotalBallance(days) / days;
	}

	SavingInformationDto getSavingInformation(String id) {
		String data = jsonClient.sendRawData("getSavingInformation", id);
		ObjectMapper mapper = new ObjectMapper();
		try {
			SavingInformationDto info = mapper.readValue(data,
					SavingInformationDto.class);
			return info;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new SavingInformationDto();
	}

	DepositInformationDto getDepositInformation(long id) {
		String param = "" + id;
		String data = jsonClient.sendRawData("getDepositInformation", param);
		ObjectMapper mapper = new ObjectMapper();
		try {
			DepositInformationDto info = mapper.readValue(data,
					DepositInformationDto.class);
			return info;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new DepositInformationDto();
	}

	void fillSavingData() {
		String data = jsonClient.sendRawData("listSavingId", beginDate);
		String[] ids = data.split(";");
		for (String id : ids) {
			long savingId = Long.parseLong(id);
			//
			String ballance = jsonClient.sendRawData("getSavingBallance", id
					+ ";" + beginDate);
			double startBallance = Double.parseDouble(ballance);
			//
			double averageBallance = createSavingAverageBallance(savingId,
					startBallance);
			double endBallance = ballances[days];
			//
			SavingInformationDto info = getSavingInformation(id);
			//
			RevenueSharingDto item = new RevenueSharingDto();
			item.setAccount(savingId);
			item.setSaving(savingId);
			item.setType(1);
			item.setStartValue(startBallance);
			item.setEndValue(endBallance);
			item.setAverageValue(averageBallance);
			item.setCode(info.getCode());
			item.setCustomer(info.getCustomer());
			item.setName(info.getName());
			item.setProduct(info.getProductName());
			item.setSharing(info.getSharing());
			item.setHasShare(info.getHasShare());
			// Pajak
			if (item.getAverageValue() > 0) {
				Double value = taxMap.get(item.getCustomer());
				if (value != null) {
					value += item.getEndValue();
				} else {
					value = item.getEndValue();
				}
				taxMap.put(item.getCustomer(), value);
				totalAverageBallance += item.getAverageValue();
				if (info.getSchema() == 1 && info.getHasShare() == 1) {
					totalWadiahAverageBallance += item.getAverageValue();
				}
			}
			// Masukkan
			revSharingMap.put("1;" + savingId, item);
		}

	}

	void fillDepositData() {
		String data = jsonClient.sendRawData("listDepositId", endDate);
		String[] ids = data.split(";");
		for (String id : ids) {
			long depositId = Long.parseLong(id);
			//
			DepositInformationDto info = getDepositInformation(depositId);
			//
			RevenueSharingDto item = new RevenueSharingDto();
			item.setAccount(depositId);
			item.setType(2);
			item.setStartValue(info.getValue());
			item.setEndValue(info.getValue());
			item.setAverageValue(info.getValue());
			item.setCode(info.getCode());
			item.setCustomer(info.getCustomer());
			item.setName(info.getName());
			item.setProduct(info.getProductName());
			item.setSharing(info.getSharing());
			item.setHasShare(1);
			item.setSaving(info.getSaving());
			//
			Double value = taxMap.get(item.getCustomer());
			if (value != null) {
				value += item.getEndValue();
			} else {
				value = item.getEndValue();
			}
			taxMap.put(item.getCustomer(), value);
			//
			totalAverageBallance += item.getAverageValue();
			// Masukkan
			revSharingMap.put("2;" + depositId, item);
		}

	}

}
