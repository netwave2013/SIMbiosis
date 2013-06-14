package org.simbiosis.cli.nominatif.creator;

import java.util.Date;

import org.simbiosis.utils.DateUtils;

public class CreatorDaily {

	static Date date = new Date();

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Creator creator = new Creator(DateUtils.dateToStr(date));
		creator.execute();
	}

}
