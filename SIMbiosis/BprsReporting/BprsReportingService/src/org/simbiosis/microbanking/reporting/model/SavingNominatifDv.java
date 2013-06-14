package org.simbiosis.microbanking.reporting.model;

import java.io.Serializable;

public class SavingNominatifDv implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6263807840210306879L;
	int nr;
	String savingCode;
	String customerName;
	double saldoAwal;
	double mutCredit;
	double mutDebet;
	double saldoAkhir;
	String cbg;
	String product;

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public String getSavingCode() {
		return savingCode;
	}

	public void setSavingCode(String savingCode) {
		this.savingCode = savingCode;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public double getSaldoAwal() {
		return saldoAwal;
	}

	public void setSaldoAwal(double saldoAwal) {
		this.saldoAwal = saldoAwal;
	}

	public double getMutCredit() {
		return mutCredit;
	}

	public void setMutCredit(double mutCredit) {
		this.mutCredit = mutCredit;
	}

	public double getSaldoAkhir() {
		return saldoAkhir;
	}

	public void setSaldoAkhir(double saldoAkhir) {
		this.saldoAkhir = saldoAkhir;
	}

	public double getMutDebet() {
		return mutDebet;
	}

	public void setMutDebet(double mutDebet) {
		this.mutDebet = mutDebet;
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
