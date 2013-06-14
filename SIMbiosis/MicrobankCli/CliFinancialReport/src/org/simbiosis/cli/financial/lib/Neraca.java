package org.simbiosis.cli.financial.lib;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.gl.CoaGroupDto;
import org.simbiosis.gl.FinReportDto;
import org.simbiosis.gl.GLTransStartDto;
import org.simbiosis.utils.DateUtils;
import org.simbiosis.utils.MicrobankCoreClient;

public class Neraca {
	String host = "192.168.1.101";
	//String host = "localhost";
	int port = 8080; 

	String beginDate = "01-01-";
	long coaLRTL = 613;
	long coaLR = 614;
	double[] values = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	long company = 2;
	long branch;

	int type = 0;
	Date date;
	int month;
	int year;
	String[] endMonths = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };
	String endDate;
	boolean coaLRAda = false;

	MicrobankCoreClient jsonClient;

	Map<Long, NeracaItem> neracaMap = new HashMap<Long, NeracaItem>();
	List<NeracaItem> itemList = new ArrayList<NeracaItem>();

	public static void main(String[] args) {
		long[] branches = { 0, 3, 4 };
		for (int j = 0; j < 3; j++) {
			long branch = branches[j];
			Neraca neraca = new Neraca(branch, 9, 2012);
			neraca.createNeracaLR();
			// neraca.checkBallance();
			System.out.println("Simpan data");
			neraca.saveFinReport();
		}
		// System.out.println("Buat file");
		// neraca.createFile();
	}

	public Neraca(long branch, int month, int year) {
		jsonClient = new MicrobankCoreClient(host,port);
		type = 1;
		this.branch = branch;
		this.month = month;
		this.year = year;
		beginDate += "" + year;
		endDate = endMonths[month] + "-" + year;
	}

	public Neraca(long branch, Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		jsonClient = new MicrobankCoreClient(host,port);
		type = 2;
		this.branch = branch;
		this.date = date;
		this.year = Integer.parseInt(sdf.format(date));
		beginDate += "" + year;
		endDate = DateUtils.dateToStr(date);
	}

	public void createNeracaLR() {
		// createNeraca();
		System.out.println("Ambil start value");
		loadStartValue();
		System.out.println("Buat current value");
		createNeraca();
		createLabaRugi();
		System.out.println("Buat end value dan kalkulasi");
		createEndValue();
		calculateValues();
	}

	void createNeraca() {
		//
		for (CoaGroupDto dto : listCoaGroup(1)) {
			createCurrentValue(dto);
		}
		for (CoaGroupDto dto : listCoaGroup(2)) {
			createCurrentValue(dto);
		}
	}

	void createLabaRugi() {
		//
		for (CoaGroupDto dto : listCoaGroup(3)) {
			createCurrentValue(dto);
		}
		for (CoaGroupDto dto : listCoaGroup(4)) {
			createCurrentValue(dto);
		}
	}

	public void calculateValues() {
		// Hitung nilai-nilai
		for (NeracaItem item : itemList) {
			values[item.getGroup()] += item.getEndValue();
		}
		// Perhitungan laba rugi
		boolean found = false;
		values[10] = values[3] + values[5] - values[4] - values[6];
		for (NeracaItem item : itemList) {
			if (item.getCoa() == coaLR) {
				item.setCurrentValue(values[10]);
				item.setEndValue(values[10]);
				found = true;
			}
			if (found)
				break;
		}
		if (!found) {

		}
		// Koreksi pasiva
		values[2] += values[10];
	}

	void createEndValue() {
		//
		NeracaItem lrtl = neracaMap.get(coaLRTL);
		if (lrtl != null) {
			lrtl.setGroup(2);
			neracaMap.put(coaLRTL, lrtl);
		}
		//
		NeracaItem lr = neracaMap.get(coaLR);
		if (lr == null) {
			NeracaItem item = new NeracaItem();
			item.setGroup(2);
			item.setCoa(coaLR);
			item.setCode("430.02.01");
			item.setDescription("LABA/RUGI TAHUN BERJALAN");
			item.setStartValue(0);
			item.setFactor(1);
			neracaMap.put(item.getCoa(), item);
		}
		//
		itemList.addAll(neracaMap.values());
		Collections.sort(itemList, new Comparator<NeracaItem>() {

			@Override
			public int compare(NeracaItem o1, NeracaItem o2) {
				return o1.getCode().compareTo(o2.getCode());
			}
		});
		for (NeracaItem item : itemList) {
			item.setCurrentValue(item.getCurrentValue() * item.getFactor());
			item.setEndValue(item.getStartValue() + item.getCurrentValue());
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

	void loadStartValue() {
		String data = jsonClient.sendRawData("listGlTransStart", branch + ";1;"
				+ year);
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<GLTransStartDto> objects = mapper.readValue(data, TypeFactory
					.collectionType(ArrayList.class, GLTransStartDto.class));
			for (GLTransStartDto rs : objects) {
				NeracaItem item = new NeracaItem();
				item.setGroup(rs.getGroup());
				item.setCoa(rs.getCoa());
				item.setCode(rs.getCode());
				item.setDescription(rs.getDescription());
				item.setStartValue(rs.getValue());
				item.setFactor(1);
				neracaMap.put(rs.getCoa(), item);
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void createCurrentValue(CoaGroupDto dto) {
		String data = jsonClient.sendRawData("listSumGlTransGroupRange", branch
				+ ";" + beginDate + ";" + endDate + ";" + dto.getCode());
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<Object[]> objects = mapper
					.readValue(data, TypeFactory.collectionType(
							ArrayList.class, Object[].class));
			for (Object[] rs : objects) {
				long id = Long.parseLong(rs[0].toString());
				String coaCode = rs[1].toString();
				String description = rs[2].toString();
				int direction = Integer.parseInt(rs[3].toString());
				double value = Double.parseDouble(rs[4].toString());
				NeracaItem item = neracaMap.get(id);
				if (item == null) {
					item = new NeracaItem();
					item.setCoa(id);
					item.setCode(coaCode);
					item.setDescription(description);
					item.setStartValue(0D);
					item.setCurrentValue(direction == 1 ? value : -value);
				} else {
					if (direction == 1) {
						item.setCurrentValue(item.getCurrentValue() + value);
					} else {
						item.setCurrentValue(item.getCurrentValue() - value);
					}
				}
				item.setGroup(dto.getGroup());
				item.setFactor(dto.getFactor());
				neracaMap.put(id, item);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void checkBallance() {
		double value = 0;
		for (NeracaItem item : itemList) {
			value += item.getCurrentValue();
		}
		if (Math.abs(value) > 0.01) {
			System.out.println("Gak ballance");
		} else {
			System.out.println("Ok");
		}
	}

	String createText(int group) {
		String[] texts = { "", "AKTIVA", "PASIVA", "PENDAPATAN OPS",
				"BEBAN OPS", "", "", "", "", "", "L/R" };
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String buffer = "";
		for (NeracaItem item : itemList) {
			if (item.getGroup() == group) {
				if ((Math.abs(item.getStartValue()) > 0.01)
						|| (Math.abs(item.getCurrentValue()) > 0.01)
						|| (Math.abs(item.getEndValue()) > 0.01)) {
					// String line = item.getCode() + "-" +
					// item.getDescription()
					// + "\t" + df.format(item.getStartValue()) + "\t"
					// + df.format(item.getCurrentValue()) + "\t"
					// + df.format(item.getEndValue());

					String line = item.getCode() + "-" + item.getDescription()
							+ "\t" + df.format(item.getEndValue());

					buffer += line;
					if (!buffer.isEmpty())
						buffer += "\n";
				}
			}
		}
		// String line = texts[group] + "\t" + "\t" + "\t"
		// + df.format(values[group]);
		String line = texts[group] + "\t" + df.format(values[group]);
		buffer += line + "\n\n";
		return buffer;
	}

	public void saveFinReport() {
		List<FinReportDto> toSend = new ArrayList<FinReportDto>();
		for (NeracaItem item : itemList) {
			if ((Math.abs(item.getStartValue()) > 0.01)
					|| (Math.abs(item.getCurrentValue()) > 0.01)
					|| (Math.abs(item.getEndValue()) > 0.01)) {
				FinReportDto fr = new FinReportDto();
				fr.setCompany(company);
				fr.setBranch(branch);
				fr.setType(type);
				if (type == 1) {
					fr.setMonth(month);
					fr.setYear(year);
				} else {
					fr.setDate(date);
				}
				fr.setCoa(item.getCoa());
				fr.setGroup(item.getGroup());
				fr.setFactor(item.getFactor());
				fr.setStartValue(item.getStartValue());
				fr.setCurrentValue(item.getCurrentValue());
				fr.setEndValue(item.getEndValue());
				toSend.add(fr);
			}
		}
		//
		try {
			ObjectMapper mapper = new ObjectMapper();
			StringWriter sw = new StringWriter();
			mapper.writeValue(sw, toSend);
			jsonClient.sendRawData("saveFinReport", sw.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	void createFile() {
		//
		DecimalFormat df = new DecimalFormat("#,##0.00");
		String buffer = "";
		buffer += createText(1);
		buffer += createText(2);
		buffer += createText(3);
		buffer += createText(4);
		// String line = "L/R" + "\t" + "\t" + "\t" + df.format(values[10]);
		String line = "L/R\t" + df.format(values[10]);
		buffer += line;
		//
		try {
			FileOutputStream fstream = new FileOutputStream("neraca.csv");
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
