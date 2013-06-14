package org.simbiosis.cli.financial;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.simbiosis.cli.financial.lib.Neraca;
import org.simbiosis.utils.DateUtils;

public class CreatorCorrection {

	static long[] branches = { 0, 3, 4, 5 };

	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = sdf.format(new Date());
		String[] strDates = strDate.split("-");
		int maxDate = Integer.parseInt(strDates[0]);
		for (int i = 1; i <= maxDate; i++) {
			String date = df.format(i) + "-" + strDates[1] + "-" + strDates[2];
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
