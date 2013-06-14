package org.simbiosis.cli.bi.lb;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.microbank.LoanTransInfoDto;
import org.simbiosis.utils.MicrobankCoreClient;
import org.simbiosis.utils.StrUtils;

public class Lending {

	SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");

	List<LoanTransInfoDto> listMbh = new ArrayList<LoanTransInfoDto>();
	List<LoanTransInfoDto> listPby = new ArrayList<LoanTransInfoDto>();
	List<LoanTransInfoDto> listQdh = new ArrayList<LoanTransInfoDto>();
	List<LoanTransInfoDto> listMjs = new ArrayList<LoanTransInfoDto>();

	MicrobankCoreClient jsonClient = null;
	String endDate = "";
	String newLine = "";

	public Lending(MicrobankCoreClient jsonClient, String endDate, String newLine) {
		this.jsonClient = jsonClient;
		this.endDate = endDate;
		this.newLine = newLine;
	}

	public void retrieve() {
		listAllLoan();
	}

	public Double createPpap(LoanTransInfoDto info) {
		double ppap = 0.5 * info.getOsPrincipal() / 100;
		double osPpap = info.getOsPrincipal() - (0.5 * info.getGuarantee());
		if (osPpap < 0) {
			osPpap = 0;
		}
		switch (info.getQuality()) {
		case 2:
			ppap = 10 * osPpap / 100;
			break;
		case 3:
			ppap = 50 * osPpap / 100;
			break;
		case 4:
			ppap = osPpap;
			break;
		}
		ppap = ppap / 1000;
		if (ppap < 1)
			ppap = 0;
		return ppap;
	}

	void listAllLoan() {
		ObjectMapper mapper = new ObjectMapper();
		String data = jsonClient.sendRawData("listSimpleLoanTransactionPayd",
				"0;" + endDate);
		try {
			List<LoanTransInfoDto> infos = mapper.readValue(data, TypeFactory
					.collectionType(ArrayList.class, LoanTransInfoDto.class));
			for (LoanTransInfoDto info : infos) {
				// if (info.getCode().equalsIgnoreCase("0014300421")) {
				// System.out.println(info.getId() + ";" + info.getCode()
				// + ";" + info.getPaydPrincipal() + ";"
				// + info.getPaydMargin() + ";" + info.getQuality());
				// }
				if (info.getQuality() == 0) {
					System.out.println("Warning : " + info.getCode());
				}
				switch (info.getSchema()) {
				case 1:
					listMbh.add(info);
					break;
				case 2:
				case 3:
					listPby.add(info);
					break;
				case 4:
					listQdh.add(info);
					break;
				case 5:
					listMjs.add(info);
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	String create03() {
		// String code = "BS03";
		String code = "";
		String result = "";
		try {
			FileInputStream fstream = new FileInputStream("LABULBS03.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				result += (code + strLine + newLine);
			}
			// Close the input stream
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	String create18() {
		// String code = "BS03";
		String code = "";
		String result = "";
		try {
			FileInputStream fstream = new FileInputStream("LABULBS03.txt");
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				result += (code + strLine + newLine);
			}
			// Close the input stream
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	String getJenisPenggunaan(String code) {
		String productCode = code.substring(3, 5);
		System.out.println(productCode);
		if (productCode.equalsIgnoreCase("41")) {
			return "10";
		} else if (productCode.equalsIgnoreCase("42")) {
			return "40";
		} else if (productCode.equalsIgnoreCase("43")) {
			return "70";
		}
		return "10";
	}

	String getGolonganPiutang(String jenisPenggunaan) {
		if (jenisPenggunaan.equalsIgnoreCase("70")) {
			return "4";
		}
		return "1";
	}

	String getSektorE(String jenisPenggunaan) {
		if (jenisPenggunaan.equalsIgnoreCase("70")) {
			return "1020";
		}
		return "1007";
	}

	String create04() {
		String buffer = "";
		int line = 1;
		for (LoanTransInfoDto info : listMbh) {
			buffer += create04Text(info, line++) + newLine;
		}
		return buffer;
	}

	String create04Text(LoanTransInfoDto info, int line) {
		String code = "BS04";
		String loanCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String kodeKhusus = "1       ";
		String jenisPenggunaan = getJenisPenggunaan(loanCode);
		String hubDenganBank = "2";
		String registrasi = df.format(info.getBegin());
		String tempo = df.format(info.getEnd());
		String col = "" + info.getQuality();
		Double dImbalan = (info.getMargin() * 10000) / info.getPrincipal();
		Integer iImbalan = dImbalan.intValue();
		if (iImbalan > 9900) {
			iImbalan = 9900;
		}
		String imbalan = "1850";// StrUtils.lpadded(iImbalan.toString(), 4,
								// '0');
		String sektorE = getSektorE(jenisPenggunaan);
		Double dHargaJual = (info.getPrincipal() + info.getMargin()) / 1000;
		Integer iHargaJual = dHargaJual.intValue();
		String hargaJual = StrUtils.lpadded(iHargaJual.toString(), 12, '0');
		Double dSaldoPokok = info.getOsPrincipal() / 1000;
		Integer iSaldoPokok = dSaldoPokok.intValue();
		String saldoPokok = StrUtils.lpadded(iSaldoPokok.toString(), 12, '0');
		Double dSaldoMargin = info.getOsMargin() / 1000;
		Integer iSaldoMargin = dSaldoMargin.intValue();
		String saldoMargin = StrUtils.lpadded(iSaldoMargin.toString(), 12, '0');
		Integer iSaldoPiutang = iSaldoPokok + iSaldoMargin;
		String saldoPiutang = StrUtils.lpadded(iSaldoPiutang.toString(), 12,
				'0');
		Double dNilaiAgunan = info.getGuarantee() / 1000;
		Integer iNilaiAgunan = dNilaiAgunan.intValue();
		String nilaiAgunan = StrUtils.lpadded(iNilaiAgunan.toString(), 12, '0');
		String agunan = iNilaiAgunan > 0 ? "3" : "0";
		Double dPpap = createPpap(info);
		Integer iPpap = dPpap.intValue();
		String ppap = StrUtils.lpadded(iPpap.toString(), 12, '0');
		String metodeBasil = "2";
		String golPenjamin = "000";
		String bagPenjamin = "0000";
		String golNasabah = "876";
		String lokasiUsaha = "1291";
		String golPiutang = getGolonganPiutang(jenisPenggunaan);
		String tujKepemilikan = "73";
		String pendapatanAkanDiterima = StrUtils.lpadded("0", 12, '0');
		String strLine = StrUtils.lpadded("" + line, 6, '0');
		return code + loanCode + kodeKhusus + jenisPenggunaan + hubDenganBank
				+ registrasi + tempo + col + imbalan + sektorE + hargaJual
				+ saldoPokok + saldoMargin + saldoPiutang + agunan
				+ nilaiAgunan + ppap + metodeBasil + golPenjamin + bagPenjamin
				+ golNasabah + lokasiUsaha + golPiutang + tujKepemilikan
				+ pendapatanAkanDiterima + strLine;
	}

	String create07() {
		String buffer = "";
		int line = 1;
		for (LoanTransInfoDto info : listPby) {
			buffer += create07Text(info, line++) + newLine;
		}
		return buffer;
	}

	String create07Text(LoanTransInfoDto info, int line) {
		String code = "BS07";
		String loanCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String kodeKhusus = "1       ";
		String jenisPembiayaan = "10";
		String jenisPenggunaan = getJenisPenggunaan(loanCode);
		String hubDenganBank = "2";
		String registrasi = df.format(info.getBegin());
		String tempo = df.format(info.getEnd());
		String col = "" + info.getQuality();
		Double dImbalan = (info.getMargin() * 10000) / info.getPrincipal();
		Integer iImbalan = dImbalan.intValue();
		if (iImbalan > 9900) {
			iImbalan = 9900;
		}
		String imbalan = "1850";// StrUtils.lpadded(iImbalan.toString(), 4,
								// '0');
		String sektorE = getSektorE(jenisPenggunaan);
		Double dPlafond = info.getOsPrincipal() / 1000;
		Integer iPlafond = dPlafond.intValue();
		String plafond = StrUtils.lpadded(iPlafond.toString(), 12, '0');
		String longgarTarik = StrUtils.lpadded("0", 12, '0');
		Double dSaldoPembiayaan = info.getOsPrincipal() / 1000;
		Integer iSaldoPembiayaan = dSaldoPembiayaan.intValue();
		String saldoPembiayaan = StrUtils.lpadded(iSaldoPembiayaan.toString(),
				12, '0');
		Double dNilaiAgunan = info.getGuarantee() / 1000;
		Integer iNilaiAgunan = dNilaiAgunan.intValue();
		String nilaiAgunan = StrUtils.lpadded(iNilaiAgunan.toString(), 12, '0');
		String agunan = iNilaiAgunan > 0 ? "3" : "0";
		Double dPpap = createPpap(info);
		Integer iPpap = dPpap.intValue();
		String ppap = StrUtils.lpadded(iPpap.toString(), 12, '0');
		String sifatPlafond = "1";
		String metodeBasil = "2";
		String metodeBasilDana = "2";
		String golPenjamin = "000";
		String bagPenjamin = "0000";
		String golNasabah = "876";
		String lokasiUsaha = "1291";
		String golPiutang = getGolonganPiutang(jenisPenggunaan);
		String strLine = StrUtils.lpadded("" + line, 6, '0');
		return code + loanCode + kodeKhusus + jenisPembiayaan + jenisPenggunaan
				+ hubDenganBank + registrasi + tempo + col + imbalan + sektorE
				+ plafond + longgarTarik + saldoPembiayaan + agunan
				+ nilaiAgunan + ppap + sifatPlafond + metodeBasil
				+ metodeBasilDana + golPenjamin + bagPenjamin + golNasabah
				+ lokasiUsaha + golPiutang + strLine;
	}

	String create09() {
		String buffer = "";
		int line = 1;
		for (LoanTransInfoDto info : listQdh) {
			buffer += create09Text(info, line++) + newLine;
		}
		return buffer;
	}

	String create09Text(LoanTransInfoDto info, int line) {
		String code = "BS09";
		String loanCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String kodeKhusus = "1       ";
		String jenisPenggunaan = getJenisPenggunaan(loanCode);
		String hubDenganBank = "2";
		String registrasi = df.format(info.getBegin());
		String tempo = df.format(info.getEnd());
		String col = "" + info.getQuality();
		String sektorE = getSektorE(jenisPenggunaan);
		Double dSaldoPiutang = info.getOsPrincipal() / 1000;
		Integer iSaldoPiutang = dSaldoPiutang.intValue();
		String saldoPiutang = StrUtils.lpadded(iSaldoPiutang.toString(), 12,
				'0');
		Double dNilaiAgunan = info.getGuarantee() / 1000;
		Integer iNilaiAgunan = dNilaiAgunan.intValue();
		String nilaiAgunan = StrUtils.lpadded(iNilaiAgunan.toString(), 12, '0');
		String agunan = iNilaiAgunan > 0 ? "2" : "0";
		Double dPpap = createPpap(info);
		Integer iPpap = dPpap.intValue();
		String ppap = StrUtils.lpadded(iPpap.toString(), 12, '0');
		String metodeBasil = "2";
		String golPenjamin = "000";
		String bagPenjamin = "0000";
		String golNasabah = "876";
		String lokasiUsaha = "1291";
		String golPiutang = getGolonganPiutang(jenisPenggunaan);
		String strLine = StrUtils.lpadded("" + line, 6, '0');
		return code + loanCode + kodeKhusus + jenisPenggunaan + hubDenganBank
				+ registrasi + tempo + col + sektorE + saldoPiutang + agunan
				+ nilaiAgunan + ppap + metodeBasil + golPenjamin + bagPenjamin
				+ golNasabah + lokasiUsaha + golPiutang + strLine;
	}

	String create20() {
		String buffer = "";
		int line = 1;
		for (LoanTransInfoDto info : listMjs) {
			buffer += create20Text(info, line++) + newLine;
		}
		return buffer;
	}

	String create20Text(LoanTransInfoDto info, int line) {
		String code = "BS20";
		String loanCode = StrUtils.rpadded(info.getCode(), 15, ' ');
		String kodeKhusus = "1       ";
		String jenisPenggunaan = getJenisPenggunaan(loanCode);
		String hubDenganBank = "2";
		String registrasi = df.format(info.getBegin());
		String tempo = df.format(info.getEnd());
		String col = "" + info.getQuality();
		String sektorE = getSektorE(jenisPenggunaan);
		String metodeBasilDana = "2";
		String golPenjamin = "000";
		String bagPenjamin = "0000";
		String golNasabah = "876";
		String lokasiUsaha = "1291";
		String golPiutang = getGolonganPiutang(jenisPenggunaan);
		Double dHargaJual = (info.getPrincipal() + info.getMargin()) / 1000;
		Integer iHargaJual = dHargaJual.intValue();
		String hargaJual = StrUtils.lpadded(iHargaJual.toString(), 12, '0');
		Double dSaldoPokok = info.getOsPrincipal() / 1000;
		Integer iSaldoPokok = dSaldoPokok.intValue();
		String saldoPokok = StrUtils.lpadded(iSaldoPokok.toString(), 12, '0');
		Double dSaldoMargin = info.getOsMargin() / 1000;
		Integer iSaldoMargin = dSaldoMargin.intValue();
		String saldoMargin = StrUtils.lpadded(iSaldoMargin.toString(), 12, '0');
		Integer iSaldoPiutang = iSaldoPokok + iSaldoMargin;
		String saldoPiutang = StrUtils.lpadded(iSaldoPiutang.toString(), 12,
				'0');
		Double dNilaiAgunan = info.getGuarantee() / 1000;
		Integer iNilaiAgunan = dNilaiAgunan.intValue();
		String nilaiAgunan = StrUtils.lpadded(iNilaiAgunan.toString(), 12, '0');
		String agunan = iNilaiAgunan > 0 ? "3" : "0";
		Double dPpap = createPpap(info);
		Integer iPpap = dPpap.intValue();
		String ppap = StrUtils.lpadded(iPpap.toString(), 12, '0');
		String strLine = StrUtils.lpadded("" + line, 6, '0');
		return code + loanCode + kodeKhusus + jenisPenggunaan + hubDenganBank
				+ registrasi + tempo + col + sektorE + metodeBasilDana
				+ golPenjamin + bagPenjamin + golNasabah + lokasiUsaha
				+ golPiutang + hargaJual + saldoPokok + saldoMargin
				+ saldoPiutang + agunan + nilaiAgunan + ppap + strLine;
	}

	public List<LoanTransInfoDto> getListMbh() {
		return listMbh;
	}

	public List<LoanTransInfoDto> getListPby() {
		return listPby;
	}

	public List<LoanTransInfoDto> getListQdh() {
		return listQdh;
	}

	public List<LoanTransInfoDto> getListMjs() {
		return listMjs;
	}

}
