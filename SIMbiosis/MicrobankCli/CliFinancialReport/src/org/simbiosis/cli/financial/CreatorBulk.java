package org.simbiosis.cli.financial;

import java.text.DecimalFormat;

import org.simbiosis.cli.financial.lib.Neraca;
import org.simbiosis.utils.DateUtils;

public class CreatorBulk {

	static long[] branches = { 0, 3, 4, 5 };

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("00");
		int beginDate = 30;
		int maxDate = 31;
		String monthYear = "-03-2013";
		for (int i = beginDate; i <= maxDate; i++) {
			String date = df.format(i) + monthYear;
			for (int j = 0; j < branches.length; j++) {
				long branch = branches[j];
				System.out.println("Neraca untuk branch " + branch
						+ " tanggal " + date);
				Neraca neraca = new Neraca(branch, DateUtils.strToDate(date));
				neraca.createNeracaLR();
				neraca.saveFinReport();
			}
		}
	}

}
