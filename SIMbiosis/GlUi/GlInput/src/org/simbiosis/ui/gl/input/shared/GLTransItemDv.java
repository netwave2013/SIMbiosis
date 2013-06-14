package org.simbiosis.ui.gl.input.shared;

import java.util.Date;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GLTransItemDv implements IsSerializable {
	Integer nr;
	Date date;
	String code;
	Long id;
	Long coa;
	String coaStr;
	String description;
	Double debit;
	Double credit;
	Double saldo;

	public GLTransItemDv() {
		id = 0L;
		coa = 0L;
		debit = 0.00;
		credit = 0.00;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getCoa() {
		return coa;
	}

	public void setCoa(Long coa) {
		this.coa = coa;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getDebit() {
		return debit;
	}

	public void setDebit(Double debit) {
		this.debit = debit;
	}

	public Double getCredit() {
		return credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	public String getCoaStr() {
		return coaStr;
	}

	public void setCoaStr(String coaStr) {
		this.coaStr = coaStr;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

}
