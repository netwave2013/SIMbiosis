package org.simbiosis.cli.bi.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.gl.FinReportDto;
import org.simbiosis.utils.MicrobankCoreClient;

public class Konversi {
	Object[] data1 = { 1, "100", "101.00.00", 1 };
	Object[] data2 = { 1, "230", "173.00.00", 1 };
	Object[] data3 = { 1, "230", "185.00.00", 1 };
	Object[] data4 = { 1, "210", "195.90.01", 3 };
	Object[] data5 = { 1, "302", "201.00.00", 1 };
	Object[] data6 = { 1, "301", "210.00.00", 1 };
	Object[] data7 = { 1, "400", "230.00.00", 1 };
	Object[] data8 = { 1, "366", "240.40.03", 3 };
	Object[] data9 = { 1, "400", "298.00.00", 1 };
	Object[] data10 = { 1, "421", "401.01.00", 2 };
	Object[] data11 = { 1, "434", "405.00.00", 1 };
	Object[] data12 = { 1, "451", "420.00.00", 1 };
	Object[] data13 = { 1, "465", "430.00.00", 1 };
	Object[] data14 = { 1, "465", "499.99.00", 2 };
	Object[] data15 = { 2, "102", "501.00.00", 1 };
	Object[] data16 = { 2, "107", "515.00.00", 1 };
	Object[] data17 = { 2, "106", "525.00.00", 1 };
	Object[] data18 = { 2, "109", "530.00.00", 1 };
	Object[] data19 = { 2, "146", "570.00.00", 1 };
	Object[] data20 = { 2, "419", "596.00.00", 1 };
	Object[] data21 = { 2, "429", "790.00.00", 1 };
	Object[] data22 = { 1, "199", "110.90.00", 2 };
	Object[] data23 = { 1, "130", "115.01.00", 2 };
	Object[] data24 = { 1, "150", "125.01.00", 2 };
	Object[] data25 = { 1, "151", "125.05.00", 2 };
	Object[] data26 = { 1, "186", "136.01.00", 2 };
	Object[] data27 = { 1, "187", "136.05.00", 2 };
	Object[] data28 = { 1, "199", "136.90.00", 2 };
	Object[] data29 = { 1, "160", "140.01.00", 2 };
	Object[] data30 = { 1, "199", "140.90.00", 2 };
	Object[] data31 = { 1, "190", "160.01.00", 2 };
	Object[] data32 = { 1, "190", "160.02.00", 2 };
	Object[] data33 = { 1, "199", "160.90.00", 2 };
	Object[] data34 = { 1, "215", "180.20.00", 2 };
	Object[] data35 = { 1, "216", "180.21.00", 2 };
	Object[] data36 = { 1, "215", "180.30.00", 2 };
	Object[] data37 = { 1, "216", "180.31.00", 2 };
	Object[] data38 = { 1, "215", "180.40.00", 2 };
	Object[] data39 = { 1, "216", "180.41.00", 2 };
	Object[] data40 = { 1, "215", "180.50.00", 2 };
	Object[] data41 = { 1, "216", "180.51.00", 2 };
	Object[] data42 = { 1, "301", "280.30.00", 2 };
	Object[] data43 = { 1, "301", "280.40.00", 2 };
	Object[] data44 = { 1, "301", "280.90.00", 2 };
	Object[] data45 = { 1, "321", "301.01.00", 2 };
	Object[] data46 = { 1, "322", "301.05.00", 2 };
	Object[] data47 = { 2, "146", "585.01.00", 2 };
	Object[] data48 = { 2, "149", "590.05.00", 2 };
	Object[] data49 = { 2, "149", "590.10.00", 2 };
	Object[] data50 = { 2, "138", "590.15.00", 2 };
	Object[] data51 = { 2, "149", "595.05.00", 2 };
	Object[] data52 = { 2, "151", "601.01.00", 2 };
	Object[] data53 = { 2, "152", "601.05.00", 2 };
	Object[] data54 = { 2, "301", "701.05.00", 2 };
	Object[] data55 = { 2, "309", "701.10.00", 2 };
	Object[] data56 = { 2, "309", "701.15.00", 2 };
	Object[] data57 = { 2, "309", "701.90.00", 2 };
	Object[] data58 = { 2, "398", "720.10.00", 2 };
	Object[] data59 = { 2, "360", "720.20.00", 2 };
	Object[] data60 = { 2, "398", "720.50.00", 2 };
	Object[] data61 = { 2, "330", "720.60.00", 2 };
	Object[] data62 = { 2, "375", "750.01.00", 2 };
	Object[] data63 = { 2, "375", "750.02.00", 2 };
	Object[] data64 = { 2, "378", "750.07.00", 2 };
	Object[] data65 = { 2, "379", "750.08.00", 2 };
	Object[] data66 = { 2, "389", "750.09.00", 2 };
	Object[] data67 = { 2, "340", "770.01.00", 2 };
	Object[] data68 = { 2, "399", "770.02.00", 2 };
	Object[] data69 = { 2, "279", "770.03.00", 2 };
	Object[] data70 = { 2, "350", "770.04.00", 2 };
	Object[] data71 = { 2, "272", "770.05.00", 2 };
	Object[] data72 = { 2, "186", "770.20.00", 2 };
	Object[] data73 = { 2, "320", "780.01.01", 3 };
	Object[] data74 = { 2, "399", "780.05.00", 2 };
	Object[] data75 = { 2, "399", "780.10.00", 2 };
	Object[] data76 = { 2, "399", "780.15.00", 2 };
	Object[] data77 = { 1, "130", "115.85.00", 2 };
	Object[] data78 = { 1, "199", "125.90.01", 3 };
	Object[] data79 = { 1, "200", "125.90.05", 3 };
	Object[] data80 = { 2, "115", "540.05.01", 3 };
	Object[] data81 = { 2, "116", "540.05.02", 3 };
	Object[] data82 = { 2, "302", "701.01.01", 3 };
	Object[] data83 = { 2, "301", "701.02.02", 3 };
	Object[] data84 = { 2, "371", "720.40.02", 3 };
	Object[] data85 = { 2, "371", "720.40.03", 3 };
	Object[] data86 = { 2, "371", "720.40.04", 3 };
	Object[] data87 = { 2, "371", "720.40.05", 3 };
	Object[] data88 = { 2, "385", "720.40.06", 3 };
	Object[] data89 = { 2, "310", "780.01.06", 3 };
	Object[] data90 = { 1, "400", "240.40.01", 3 };
	Object[] data91 = { 1, "400", "240.40.02", 3 };
	Object[] data92 = { 1, "230", "195.01.00", 2 };
	Object[] data93 = { 1, "230", "195.10.00", 2 };
	Object[] data94 = { 1, "230", "195.30.00", 2 };
	Object[] data95 = { 1, "230", "195.90.03", 3 };
	Object[] data98 = { 1, "161", "145.01.00", 2 };
	Object[] data99 = { 1, "199", "145.90.00", 2 };

	Object[] datas = { data1, data2, data3, data4, data5, data6, data7, data8,
			data9, data10, data11, data12, data13, data14, data15, data16,
			data17, data18, data19, data20, data21, data22, data23, data24,
			data25, data26, data27, data28, data29, data30, data31, data32,
			data33, data34, data35, data36, data37, data38, data39, data40,
			data41, data42, data43, data44, data45, data46, data47, data48,
			data49, data50, data51, data52, data53, data54, data55, data56,
			data57, data58, data59, data60, data61, data62, data63, data64,
			data65, data66, data67, data68, data69, data70, data71, data72,
			data73, data74, data75, data76, data77, data78, data79, data80,
			data81, data82, data83, data84, data85, data86, data87, data88,
			data89, data90, data91, data92, data93, data94, data95, data98, data99 };

	MicrobankCoreClient jsonClient = null;

	Map<String, LapKeuDv> level1 = new HashMap<String, LapKeuDv>();
	Map<String, LapKeuDv> level2 = new HashMap<String, LapKeuDv>();
	Map<String, LapKeuDv> level3 = new HashMap<String, LapKeuDv>();

	List<FinReportDto> finReportList = new ArrayList<FinReportDto>();

	Map<String, LapKeuDv> bi1LapKeuMap = new HashMap<String, LapKeuDv>();
	Map<String, LapKeuDv> bi2LapKeuMap = new HashMap<String, LapKeuDv>();
	List<LapKeuDv> bi1LapKeu = new ArrayList<LapKeuDv>();
	List<LapKeuDv> bi2LapKeu = new ArrayList<LapKeuDv>();
	int month;
	int year;

	String[] endMonths = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };

	public Konversi(MicrobankCoreClient jsonClient, int month, int year) {
		this.jsonClient = jsonClient;
		this.month = month;
		this.year = year;
	}

	public List<LapKeuDv> getBi1LapKeu() {
		return bi1LapKeu;
	}

	public List<LapKeuDv> getBi2LapKeu() {
		return bi2LapKeu;
	}

	public void retrieve() {
		finReportList.addAll(listFinReport());
		//
		createInternalList();
		//
		createBIList();
	}

	double getOriValue(String coa, int level) {
		LapKeuDv lap = null;
		switch (level) {
		case 1:
			lap = level1.get(coa);
			break;
		case 2:
			lap = level2.get(coa);
			break;
		case 3:
			lap = level3.get(coa);
			break;
		}
		return (lap == null) ? 0 : lap.getOriValue();
	}

	void createBIList() {
		for (Object data : datas) {
			Object[] items = (Object[]) data;
			int type = (Integer) items[0];
			String kode = (String) items[1];
			String coa = (String) items[2];
			int level = (Integer) items[3];
			if (type == 1) {
				LapKeuDv lap = bi1LapKeuMap.get(kode);
				if (lap == null) {
					lap = new LapKeuDv();
					lap.setCoa(kode);
					lap.setOriValue(getOriValue(coa, level));
				} else {
					lap.setOriValue(lap.getOriValue() + getOriValue(coa, level));
				}
				bi1LapKeuMap.put(kode, lap);
			} else {
				LapKeuDv lap = bi2LapKeuMap.get(kode);
				if (lap == null) {
					lap = new LapKeuDv();
					lap.setCoa(kode);
					lap.setOriValue(getOriValue(coa, level));
				} else {
					lap.setOriValue(lap.getOriValue() + getOriValue(coa, level));
				}
				bi2LapKeuMap.put(kode, lap);
			}
		}
		//
		for (LapKeuDv lap : bi1LapKeuMap.values()) {
			Double value = lap.getOriValue() / 1000;
			lap.setValue(Math.abs(value.intValue()));
			bi1LapKeu.add(lap);
		}
		for (LapKeuDv lap : bi2LapKeuMap.values()) {
			Double value = lap.getOriValue() / 1000;
			lap.setValue(Math.abs(value.intValue()));
			bi2LapKeu.add(lap);
		}
	}

	void createInternalList() {
		for (FinReportDto dto : finReportList) {
			LapKeuDv lap1 = level1.get(dto.getGrandParentCode());
			if (lap1 == null) {
				lap1 = new LapKeuDv();
				if (dto.getGroup() == 1 || dto.getGroup() == 2)
					lap1.setType(1);
				else
					lap1.setType(2);
				lap1.setCoa(dto.getGrandParentCode());
				lap1.setOriValue(dto.getEndValue());
			} else {
				lap1.setOriValue(lap1.getOriValue() + dto.getEndValue());
			}
			level1.put(dto.getGrandParentCode(), lap1);
			LapKeuDv lap2 = level2.get(dto.getParentCode());
			if (lap2 == null) {
				lap2 = new LapKeuDv();
				if (dto.getGroup() == 1 || dto.getGroup() == 2)
					lap2.setType(1);
				else
					lap2.setType(2);
				lap2.setCoa(dto.getParentCode());
				lap2.setOriValue(dto.getEndValue());
			} else {
				lap2.setOriValue(lap2.getOriValue() + dto.getEndValue());
			}
			level2.put(dto.getParentCode(), lap2);
			LapKeuDv lap3 = level3.get(dto.getCode());
			if (lap3 == null) {
				lap3 = new LapKeuDv();
				if (dto.getGroup() == 1 || dto.getGroup() == 2)
					lap3.setType(1);
				else
					lap3.setType(2);
				lap3.setCoa(dto.getCode());
				lap3.setOriValue(dto.getEndValue());
			} else {
				lap3.setOriValue(lap3.getOriValue() + dto.getEndValue());
			}
			level3.put(dto.getCode(), lap3);
		}
	}

	public List<FinReportDto> listFinReport() {
		List<FinReportDto> result = new ArrayList<FinReportDto>();
		String data = jsonClient.sendRawData("listDailyFinReport", "0;"
				+ endMonths[month] + "-" + year);
		ObjectMapper mapper = new ObjectMapper();
		try {
			result = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, FinReportDto.class));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

}
