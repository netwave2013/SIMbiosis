package org.simbiosis.microbanking.reporting.model;

import java.io.Serializable;
import java.util.Date;

public class DepositNominatifDv implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3003512162298608335L;
	int nr;
	String depositCode;
	String customerName;
	Date openDate;
	Date datebegin;
	Date dateend;
	double nominal;
	String cbg;
	String product;
	public int getNr() {
		return nr;
	}
	public void setNr(int nr) {
		this.nr = nr;
	}
	public String getDepositCode() {
		return depositCode;
	}
	public void setDepositCode(String depositCode) {
		this.depositCode = depositCode;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public Date getOpenDate() {
		return openDate;
	}
	public void setOpenDate(Date openDate) {
		this.openDate = openDate;
	}
	public Date getDatebegin() {
		return datebegin;
	}
	public void setDatebegin(Date datebegin) {
		this.datebegin = datebegin;
	}
	public Date getDateend() {
		return dateend;
	}
	public void setDateend(Date dateend) {
		this.dateend = dateend;
	}
	public double getNominal() {
		return nominal;
	}
	public void setNominal(double nominal) {
		this.nominal = nominal;
	}
	public String getCbg() {
		return cbg;
	}
	public void setCbg(String cbg) {
		this.cbg = cbg;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}
	

}
