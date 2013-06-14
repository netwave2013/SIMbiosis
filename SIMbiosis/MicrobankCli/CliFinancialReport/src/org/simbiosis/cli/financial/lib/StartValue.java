package org.simbiosis.cli.financial.lib;

import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.gl.CoaGroupDto;
import org.simbiosis.gl.GLTransStartDto;
import org.simbiosis.utils.DateUtils;
import org.simbiosis.utils.MicrobankCoreClient;

public class StartValue {
	long company = 2;
	String endDate;
	int year;
	boolean periodStart = true;

	MicrobankCoreClient jsonClient;

	Map<Long, NeracaItem> neracaMap = new HashMap<Long, NeracaItem>();
	List<NeracaItem> itemList = new ArrayList<NeracaItem>();

	public StartValue(String host, int port, int year) {
		jsonClient = new MicrobankCoreClient(host, port);
		// Buat endDate
		this.year = year;
		String beginDate = "01-01-" + year;
		Date myDate = DateUtils.strToDate(beginDate);
		Calendar cal = Calendar.getInstance();
		cal.setTime(myDate);
		cal.add(Calendar.DATE, -1);
		endDate = DateUtils.dateToStr(cal.getTime());
	}

	public void execute() {
		int[] cabangs = { 3, 4 };
		// int[] cabangs = { 4 };
		for (int cabang : cabangs) {
			// createNeraca();
			System.out.println("Buat start value");
			createStartValue(cabang);
			System.out.println("Simpan start value");
			saveStartValue(cabang,year);
		}
	}

	void createStartValue(int branch) {
		// Clear
		neracaMap.clear();
		itemList.clear();
		// Aktiva
		for (CoaGroupDto dto : listCoaGroup(1)) {
			createStartValue(dto, branch);
		}
		// Pasiva
		for (CoaGroupDto dto : listCoaGroup(2)) {
			createStartValue(dto, branch);
		}
		if (!periodStart) {
			// Pendapatan
			for (CoaGroupDto dto : listCoaGroup(3)) {
				createStartValue(dto, branch);
			}
			// Beban
			for (CoaGroupDto dto : listCoaGroup(4)) {
				createStartValue(dto, branch);
			}
		}
	}

	List<CoaGroupDto> listCoaGroup(int group) {
		String data = jsonClient.sendRawData("listCoaGroup", company + ";"
				+ group);
		ObjectMapper mapper = new ObjectMapper();
		List<CoaGroupDto> objects = null;
		try {
			objects = mapper.readValue(data, TypeFactory.collectionType(
					ArrayList.class, CoaGroupDto.class));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return objects;
	}

	void createStartValue(CoaGroupDto dto, int branch) {
		String data = jsonClient.sendRawData("listSumGlTransGroupUntilDate",
				branch + ";" + endDate + ";" + dto.getCode());
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Object[]> objects = mapper
					.readValue(data, TypeFactory.collectionType(
							ArrayList.class, Object[].class));
			for (Object[] rs : objects) {
				long id = Long.parseLong(rs[0].toString());
				String code = rs[1].toString();
				String description = rs[2].toString();
				int direction = Integer.parseInt(rs[3].toString());
				double value = Double.parseDouble(rs[4].toString());
				NeracaItem item = neracaMap.get(id);
				if (item == null) {
					item = new NeracaItem();
					item.setCoa(id);
					item.setGroup(dto.getGroup());
					item.setCode(code);
					item.setDescription(description);
					item.setStartValue(direction == 1 ? value : -value);
					item.setFactor(dto.getFactor());
				} else {
					if (direction == 1) {
						item.setStartValue(item.getStartValue() + value);
					} else {
						item.setStartValue(item.getStartValue() - value);
					}
				}
				neracaMap.put(id, item);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void saveStartValue(int branch, int year) {
		List<GLTransStartDto> toSend = new ArrayList<GLTransStartDto>();
		for (NeracaItem item : neracaMap.values()) {
			// Skip nol
			if (item.getStartValue() > 0.009 || item.getStartValue() < -0.009) {
				GLTransStartDto val = new GLTransStartDto();
				val.setCompany(company);
				val.setBranch(branch);
				val.setGroup(item.getGroup());
				val.setCoa(item.getCoa());
				val.setMonth(1);
				val.setYear(year);
				val.setValue(item.getStartValue() * item.getFactor());
				toSend.add(val);
			}
		}
		System.out.println("Jumlah data " + toSend.size());
		//
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, toSend);
			jsonClient.sendRawData("saveGlTransStart", sw.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
