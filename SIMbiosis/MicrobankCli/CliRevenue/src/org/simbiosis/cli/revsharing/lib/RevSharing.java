package org.simbiosis.cli.revsharing.lib;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.simbiosis.microbank.RevenueDto;
import org.simbiosis.microbank.RevenueSharingDto;
import org.simbiosis.utils.MicrobankCoreClient;

public class RevSharing {

	SimpleDateFormat dayFormat = new SimpleDateFormat("dd");
	int days;

	Map<String, RevenueSharingDto> revSharingMap = new HashMap<String, RevenueSharingDto>();
	Map<Long, Double> taxList = new HashMap<Long, Double>();

	Funding funding = null;
	Landing landing = null;
	double wadiahSharing = 2000000;
	double taxableMin = 7500000;

	public RevSharing(MicrobankCoreClient jsonClient, String beginDate, int days,
			String endDate) {
		this.days = days;
		landing = new Landing(jsonClient, beginDate, endDate);
		funding = new Funding(jsonClient, beginDate, endDate, days,
				revSharingMap, taxList);
	}

	public void execute() {
		System.out.println("Landing...");
		landing.execute();
		System.out.println("Funding...");
		funding.execute();
		System.out.println("Disburst revenue...");
		disburstRevenue();
	}

	public void disburstRevenue() {
		double factor = landing.getTotalRevenue()
				/ funding.getTotalAverageBallance();
		double wadiahFactor = wadiahSharing
				/ funding.getTotalWadiahAverageBallance();
		for (RevenueSharingDto revSharing : revSharingMap.values()) {
			if (revSharing.getAverageValue() > 0) {
				double totalSharing = revSharing.getAverageValue() * factor;
				double customerSharing = 0;
				if (revSharing.getSharing() > 0) {
					customerSharing = totalSharing * revSharing.getSharing()
							/ 100;
				} else if (revSharing.getHasShare() == 1) {
					customerSharing = revSharing.getAverageValue()
							* wadiahFactor;
				}
				revSharing.setTotalSharing(totalSharing);
				revSharing.setCustomerSharing(customerSharing);
				//
				// revSharing.setZakat(0.025 * revSharing.getCustomerSharing());
				revSharing.setZakat(0);
				Double taxBallance = taxList.get(revSharing.getCustomer());
				Double subBallance = revSharing.getCustomerSharing()
						- revSharing.getZakat();
				if (taxBallance != null && taxBallance > taxableMin) {
					revSharing.setTax(0.2 * subBallance);
				}
			} else {
				revSharing.setTotalSharing(0);
				revSharing.setCustomerSharing(0);
				revSharing.setTax(0);
				revSharing.setZakat(0);
			}
		}
	}

	public List<RevenueDto> getRevenue() {
		return landing.getListRevenue();
	}

	public Collection<RevenueSharingDto> getRevenueSharing() {
		return revSharingMap.values();
	}

}
