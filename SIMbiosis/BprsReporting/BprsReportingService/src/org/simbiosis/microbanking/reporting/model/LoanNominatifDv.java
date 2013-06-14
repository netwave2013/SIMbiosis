package org.simbiosis.microbanking.reporting.model;

import java.io.Serializable;
import java.util.Date;

public class LoanNominatifDv implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6128572093714392206L;
	int nr;
	long id;
	long customer;
	long refId;
	long company;
	long branch;
	String branchCode;
	String branchName;
	String code;
	String name;
	long product;
	String productCode;
	String productName;
	/*double rate;
	double iRate;
	int type;
	Date pos;
	int month;
	int year;
	int schema;
	String contract;
	Date contractDate;
	int tenor;
	*/
	double principal;
	double margin;
	double total;
	//double paydPrincipal;
	//double paydMargin;
	//double paydTotal;
	double osPrincipal;
	double osMargin;
	double osTotal;
	double guarantee;
	double ppap;
	int quality;
	/*
	int dueOs;
	Date lastPaid;
	double outstanding;
	*/
	//Date registration;
	Date begin;
	Date end;
	//String biCity;

	public long getCustomer() {
		return customer;
	}

	public void setCustomer(long customer) {
		this.customer = customer;
	}

	public int getNr() {
		return nr;
	}

	public void setNr(int nr) {
		this.nr = nr;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getRefId() {
		return refId;
	}

	/*public String getContract() {
		return contract;
	}

	public void setContract(String contract) {
		this.contract = contract;
	}

	public Date getContractDate() {
		return contractDate;
	}

	public void setContractDate(Date contractDate) {
		this.contractDate = contractDate;
	}

	public void setRefId(long refId) {
		this.refId = refId;
	}

	public long getCompany() {
		return company;
	}

	public void setCompany(long company) {
		this.company = company;
	}*/

	public long getBranch() {
		return branch;
	}

	public void setBranch(long branch) {
		this.branch = branch;
	}
/*
	public String getBranchCode() {
		return branchCode;
	}

	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}

	public String getBranchName() {
		return branchName;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}*/

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getProduct() {
		return product;
	}

	public void setProduct(long product) {
		this.product = product;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	/*public double getRate() {
		return rate;
	}

	public void setRate(double rate) {
		this.rate = rate;
	}

	public double getiRate() {
		return iRate;
	}

	public void setiRate(double iRate) {
		this.iRate = iRate;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getPos() {
		return pos;
	}

	public void setPos(Date pos) {
		this.pos = pos;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getSchema() {
		return schema;
	}

	public void setSchema(int schema) {
		this.schema = schema;
	}

*/
	public double getPrincipal() {
		return principal;
	}

	public void setPrincipal(double principal) {
		this.principal = principal;
	}

	/*public double getPaydPrincipal() {
		return paydPrincipal;
	}

	public void setPaydPrincipal(double paydPrincipal) {
		this.paydPrincipal = paydPrincipal;
	}*/

	public double getOsPrincipal() {
		return osPrincipal;
	}

	public void setOsPrincipal(double osPrincipal) {
		this.osPrincipal = osPrincipal;
	}

	public double getMargin() {
		return margin;
	}

	public void setMargin(double margin) {
		this.margin = margin;
	}

	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}
/*
	public double getPaydMargin() {
		return paydMargin;
	}

	public void setPaydMargin(double paydMargin) {
		this.paydMargin = paydMargin;
	}

	public double getPaydTotal() {
		return paydTotal;
	}

	public void setPaydTotal(double paydTotal) {
		this.paydTotal = paydTotal;
	}
*/
	public double getOsMargin() {
		return osMargin;
	}

	public void setOsMargin(double osMargin) {
		this.osMargin = osMargin;
	}

	public double getOsTotal() {
		return osTotal;
	}

	public void setOsTotal(double osTotal) {
		this.osTotal = osTotal;
	}
/*
	public Date getRegistration() {
		return registration;
	}

	public void setRegistration(Date registration) {
		this.registration = registration;
	}
*/
	public Date getBegin() {
		return begin;
	}

	public void setBegin(Date begin) {
		this.begin = begin;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}
/*
	public String getBiCity() {
		return biCity;
	}

	public void setBiCity(String biCity) {
		this.biCity = biCity;
	}

	public int getTenor() {
		return tenor;
	}

	public void setTenor(int tenor) {
		this.tenor = tenor;
	}
*/
	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public double getGuarantee() {
		return guarantee;
	}

	public void setGuarantee(double guarantee) {
		this.guarantee = guarantee;
	}

	public double getPpap() {
		return ppap;
	}

	public void setPpap(double ppap) {
		this.ppap = ppap;
	}

	/*public int getDueOs() {
		return dueOs;
	}

	public void setDueOs(int dueOs) {
		this.dueOs = dueOs;
	}

	public Date getLastPaid() {
		return lastPaid;
	}

	public void setLastPaid(Date lastPaid) {
		this.lastPaid = lastPaid;
	}

	public double getOutstanding() {
		return outstanding;
	}

	public void setOutstanding(double outstanding) {
		this.outstanding = outstanding;
	}*/

}
