package org.simbiosis.ui.gl.input.shared;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class GLTransDv implements IsSerializable {

	public enum TransTypeEnum {
		Masuk, Keluar;
		public static String valueToString(int value) {
			switch (value) {
			case 1:
				return "Masuk";
			case 2:
				return "Keluar";
			default:
				return "";
			}
		}
	};

	public enum TransModeEnum {
		Tunai, Bank;
		public static String valueToString(int value) {
			switch (value) {
			case 1:
				return "Tunai";
			case 2:
				return "Bank";
			default:
				return "";
			}
		}
	};

	Boolean checked;
	Integer nr;
	Integer mode;
	String strMode;
	Integer type;
	String strType;
	Long coa;
	String strCoa;
	Long id;
	Date date;
	String strDate;
	String code;
	String description;
	Long currencyId;
	String strCurrency;
	Double rate;
	String strRate;
	Double value;
	Double debit;
	Double credit;
	String strDebit;
	String strCredit;

	List<GLTransItemDv> items = new ArrayList<GLTransItemDv>();

	public GLTransDv() {
		id = 0L;
		coa = 0L;
	}

	public Integer getNr() {
		return nr;
	}

	public void setNr(Integer nr) {
		this.nr = nr;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getStrMode() {
		return strMode;
	}

	public void setStrMode(String strMode) {
		this.strMode = strMode;
	}

	public String getStrType() {
		return strType;
	}

	public void setStrType(String strType) {
		this.strType = strType;
	}

	public Long getCoa() {
		return coa;
	}

	public void setCoa(Long coa) {
		this.coa = coa;
	}

	public String getStrCoa() {
		return strCoa;
	}

	public void setStrCoa(String strCoa) {
		this.strCoa = strCoa;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStrDate() {
		return strDate;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Double getValue() {
		return value;
	}

	public void setValue(Double value) {
		this.value = value;
	}

	public Long getCurrencyId() {
		return currencyId;
	}

	public void setCurrencyId(Long currencyId) {
		this.currencyId = currencyId;
	}

	public String getStrCurrency() {
		return strCurrency;
	}

	public void setStrCurrency(String strCurrency) {
		this.strCurrency = strCurrency;
	}

	public Double getRate() {
		return rate;
	}

	public void setRate(Double rate) {
		this.rate = rate;
	}

	public String getStrRate() {
		return strRate;
	}

	public void setStrRate(String strRate) {
		this.strRate = strRate;
	}

	public List<GLTransItemDv> getItems() {
		return items;
	}

	public void setItems(List<GLTransItemDv> items) {
		this.items = items;
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

	public String getStrDebit() {
		return strDebit;
	}

	public void setStrDebit(String strDebit) {
		this.strDebit = strDebit;
	}

	public String getStrCredit() {
		return strCredit;
	}

	public void setStrCredit(String strCredit) {
		this.strCredit = strCredit;
	}

	public Boolean getChecked() {
		return checked;
	}

	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
}
