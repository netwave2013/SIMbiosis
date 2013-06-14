package org.simbiosis.cli.bi.lb;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.microbank.DepositInformationDto;
import org.simbiosis.microbank.SavingInformationDto;
import org.simbiosis.microbank.SavingTransInfoDto;
import org.simbiosis.utils.MicrobankCoreClient;
import org.simbiosis.utils.StrUtils;

public class Funding {

	MicrobankCoreClient jsonClient = null;
	String endDate = "";
	String newLine = "";
	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

	List<SavingTransInfoDto> listWad = new ArrayList<SavingTransInfoDto>();
	List<SavingTransInfoDto> listMudh = new ArrayList<SavingTransInfoDto>();
	List<DepositInformationDto> listDep = new ArrayList<DepositInformationDto>();

	public Funding(MicrobankCoreClient jsonClient, String endDate, String newLine) {
		this.jsonClient = jsonClient;
		this.endDate = endDate;
		this.newLine = newLine;
	}

	void retrieve() {
		listAllSaving();
		listAllDeposit();
	}

	SavingInformationDto getSavingInfo(String id) {
		ObjectMapper mapper = new ObjectMapper();
		String strInfo = jsonClient.sendRawData("getSavingInformation", id);
		SavingInformationDto info = null;
		try {
			info = mapper.readValue(strInfo, SavingInformationDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	void listAllSaving() {
		ObjectMapper mapper = new ObjectMapper();
		String data = jsonClient.sendRawData("listSavingBallance", "2;0;"
				+ endDate);
		try {
			List<SavingTransInfoDto> ballances = mapper.readValue(data,
					TypeFactory.collectionType(ArrayList.class,
							SavingTransInfoDto.class));
			for (SavingTransInfoDto ballance : ballances) {
				switch (ballance.getSchema()) {
				case 1:
					listWad.add(ballance);
					break;
				case 2:
					listMudh.add(ballance);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	DepositInformationDto getDepositInfo(String id) {
		ObjectMapper mapper = new ObjectMapper();
		String strInfo = jsonClient.sendRawData("getDepositInformation", id);
		DepositInformationDto info = null;
		try {
			info = mapper.readValue(strInfo, DepositInformationDto.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return info;
	}

	void listAllDeposit() {
		String data = jsonClient.sendRawData("listDepositId", endDate);
		String[] ids = data.split(";");
		for (String id : ids) {
			DepositInformationDto info = getDepositInfo(id);
			listDep.add(info);
		}
	}

	String create12() {
		String buffer = "";
		int line = 1;
		for (SavingTransInfoDto info : listWad) {
			buffer += create12Text(info, line++) + newLine;
		}
		return buffer;
	}

	String create12Text(SavingTransInfoDto info, int line) {
		String code = "BS12";
		String accCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String kodeKhusus = "1       ";
		String hubDenganBank = "2";
		String lokasi = "1291";
		String tingkatImbalan = "0036";
		Double dvalue = info.getValue() / 1000;
		Integer ivalue = dvalue.intValue();
		String jumlah = StrUtils.lpadded(ivalue.toString(), 12, '0');
		String golNasabah = "876";
		String strLine = StrUtils.lpadded("" + line, 5, '0');
		return code + accCode + kodeKhusus + hubDenganBank + lokasi
				+ tingkatImbalan + jumlah + golNasabah + strLine;
	}

	String create13() {
		String buffer = "";
		int line = 1;
		for (SavingTransInfoDto info : listMudh) {
			buffer += create13Text(info, line++) + newLine;
		}
		return buffer;
	}

	String create13Text(SavingTransInfoDto info, int line) {
		String code = "BS13";
		String accCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String kodeKhusus = "1       ";
		String hubDenganBank = "2";
		String lokasi = "1291";
		String tingkatImbalan = "0439";
		Double dvalue = info.getValue() / 1000;
		Integer ivalue = dvalue.intValue();
		String jumlah = StrUtils.lpadded(ivalue.toString(), 12, '0');
		String metodeBasil = "2";
		String golNasabah = "876";
		String mulai = df.format(info.getBegin());
		String tempo = df.format(info.getEnd());
		String strLine = StrUtils.lpadded("" + line, 5, '0');
		return code + accCode + kodeKhusus + hubDenganBank + lokasi
				+ tingkatImbalan + jumlah + metodeBasil + golNasabah + mulai
				+ tempo + strLine;
	}

	String create14() {
		String buffer = "";
		int line = 1;
		for (DepositInformationDto info : listDep) {
			buffer += create14Text(info, line++) + newLine;
		}
		return buffer;
	}

	String create14Text(DepositInformationDto info, int line) {
		String code = "BS14";
		String accCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String kodeKhusus = "1       ";
		String jenis = "21";
		String hubDenganBank = "2";
		String lokasi = "1291";
		String mulai = df.format(info.getBegin());
		String tempo = null;
		if (info.getEnd() == null) {
			System.out.println("Data gak bagus " + accCode);
			tempo = df.format(info.getBegin());
		} else {
			tempo = df.format(info.getEnd());
		}
		String tingkatImbalan = "0439";
		Double dvalue = info.getValue() / 1000;
		Integer ivalue = dvalue.intValue();
		String jumlah = StrUtils.lpadded(ivalue.toString(), 12, '0');
		String metodeBasil = "2";
		String golNasabah = "876";
		String strLine = StrUtils.lpadded("" + line, 5, '0');
		return code + accCode + kodeKhusus + jenis + hubDenganBank + lokasi
				+ mulai + tempo + tingkatImbalan + jumlah + metodeBasil
				+ golNasabah + strLine;
	}
}
