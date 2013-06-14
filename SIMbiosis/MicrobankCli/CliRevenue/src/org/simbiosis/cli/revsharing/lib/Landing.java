package org.simbiosis.cli.revsharing.lib;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.simbiosis.microbank.RevenueDto;
import org.simbiosis.utils.MicrobankCoreClient;

public class Landing {
	String beginDate;
	String endDate;

	List<RevenueDto> listRevenue;
	double totalRevenue = 0;

	MicrobankCoreClient jsonClient;

	public Landing(MicrobankCoreClient jsonClient, String beginDate, String endDate) {
		super();
		this.beginDate = beginDate;
		this.endDate = endDate;
		this.jsonClient = jsonClient;
	}

	public void execute() {
		System.out.println("- Create revenue");
		listRevenue = listRevenue();
	}

	public List<RevenueDto> getListRevenue() {
		return listRevenue;
	}

	public double getTotalRevenue() {
		return totalRevenue;
	}

	List<RevenueDto> listRevenue() {
		String result = jsonClient.sendRawData("listRevenue", beginDate + ";"
				+ endDate);
		ObjectMapper mapper = new ObjectMapper();
		try {
			List<RevenueDto> listRevenue = mapper.readValue(result, TypeFactory
					.collectionType(ArrayList.class, RevenueDto.class));
			for (RevenueDto rev : listRevenue) {
				totalRevenue += rev.getValue();
			}
			return listRevenue;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return new ArrayList<RevenueDto>();
	}

}
