package org.simbiosis.cli.loan.lib;

public class LoanBillingDv {
	long id;
	long saving;
	String code;
	int schema;
	String product;
	String name;
	int count;
	double bill;
	double principal;
	double principalPayd;
	double margin;
	double marginPayd;
	double savingBallance;
	boolean billable;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getSaving() {
		return saving;
	}

	public void setSaving(long saving) {
		this.saving = saving;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public int getSchema() {
		return schema;
	}

	public void setSchema(int schema) {
		this.schema = schema;
	}

	public double getBill() {
		return bill;
	}

	public void setBill(double bill) {
		this.bill = bill;
	}

	public double getSavingBallance() {
		return savingBallance;
	}

	public void setSavingBallance(double savingBallance) {
		this.savingBallance = savingBallance;
	}

	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public boolean isBillable() {
		return billable;
	}

	public void setBillable(boolean billable) {
		this.billable = billable;
	}

	public double getPrincipalPayd() {
		return principalPayd;
	}

	public void setPrincipalPayd(double principalPayd) {
		this.principalPayd = principalPayd;
	}

	public double getMarginPayd() {
		return marginPayd;
	}

	public void setMarginPayd(double marginPayd) {
		this.marginPayd = marginPayd;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
}