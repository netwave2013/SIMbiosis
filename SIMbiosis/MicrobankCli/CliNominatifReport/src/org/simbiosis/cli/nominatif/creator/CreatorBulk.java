package org.simbiosis.cli.nominatif.creator;

import java.text.DecimalFormat;

public class CreatorBulk {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int beginDate = 30;
		int endDate = 31;
		String month = "-03-2013";
		DecimalFormat df = new DecimalFormat("00");
		for (int i = beginDate; i <= endDate; i++) {
			Creator creator = new Creator(df.format(i) + month);
			creator.execute();
		}
	}

}
