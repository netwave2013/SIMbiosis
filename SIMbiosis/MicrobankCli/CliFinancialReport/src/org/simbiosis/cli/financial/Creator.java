package org.simbiosis.cli.financial;

import java.util.Date;

import org.simbiosis.cli.financial.lib.Neraca;

public class Creator {

	static long[] branches = { 0, 3, 4, 5 };

	public static void main(String[] args) {
		for (int j = 0; j < branches.length; j++) {
			long branch = branches[j];
			System.out.println("Create financial report hari ini untuk cabang "
					+ branch);
			Neraca neraca = new Neraca(branch, new Date());
			neraca.createNeracaLR();
			neraca.saveFinReport();
		}
	}

}
