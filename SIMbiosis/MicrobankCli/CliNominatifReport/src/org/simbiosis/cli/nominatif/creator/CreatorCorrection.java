package org.simbiosis.cli.nominatif.creator;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CreatorCorrection {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		DecimalFormat df = new DecimalFormat("00");
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = sdf.format(new Date());
		String[] strDates = strDate.split("-");
		int maxDate = Integer.parseInt(strDates[0]);
		for (int i = 1; i <= maxDate; i++) {
			String date = df.format(i) + "-" + strDates[1] + "-" + strDates[2];
			Creator creator = new Creator(date);
			creator.execute();
		}
	}

}
