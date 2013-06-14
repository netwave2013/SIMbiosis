package org.simbiosis.cli.bi.lb;

import org.simbiosis.utils.StrUtils;

public class FileCreator {

	String newLine;

	String kodeBank;
	String cabang;
	String bulan;
	String tahun;

	String namaBank = StrUtils.rpadded("PT. BPRS KARYA MUGI SENTOSA", 50, ' ');
	String alamatBank = StrUtils.rpadded("JL. MARGOREJO INDAH 70-D", 100, ' ');
	String kotaBank = StrUtils.rpadded("SURABAYA", 30, ' ');
	String kodeKhususBank = "129111200000200002";
	String direktur = StrUtils
			.rpadded("DRS. EC. H.DIDIK SUPARDANA SA", 50, ' ');

	public FileCreator(String kodeBank, String cabang, String bulan,
			String tahun, String newLine) {
		this.kodeBank = kodeBank;
		this.cabang = cabang;
		this.bulan = bulan;
		this.tahun = tahun;
		this.newLine = newLine;
	}

	String createHeader() {
		String code = "﻿CLBUL";
		String kodeKhusus = "00";
		String result = "00001031030201060121    ";
		return code + kodeBank + cabang + kodeKhusus + bulan + tahun + result
				+ newLine;
	}

	String createHeaderBank() {
		String code = "BS00";
		String result = "000150000000000000100000200003000000000400006000050318485888                                        0318470881                                                                                          bprskms@yahoo.co.id                               ";
		String namaAdmin = StrUtils.rpadded("SULISTIYANTINI", 50, ' ');
		String tugasAdmin = StrUtils.rpadded("ACCOUNTING", 50, ' ');
		String telpAdmin = StrUtils.rpadded("0318485888", 50, ' ');
		String faxAdmin = StrUtils.rpadded("0318470881", 50, ' ');
		String admin = "000000000000000100000000000000000000001";
		return code + namaBank + alamatBank + kotaBank + kodeKhususBank
				+ direktur + result + namaAdmin + tugasAdmin + telpAdmin
				+ faxAdmin + admin + newLine;
	}

}
