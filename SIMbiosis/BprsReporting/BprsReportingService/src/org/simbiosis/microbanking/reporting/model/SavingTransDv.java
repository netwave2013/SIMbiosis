package org.simbiosis.microbanking.reporting.model;

import java.io.Serializable;

public class SavingTransDv implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4609767320205708548L;
	long id;
	int line;
	String nr;
	String date;
	String type;
	String debet;
	String credit;
	String ballance;
	String operator;

	public SavingTransDv() {
		super();
		nr = "";
		date = "";
		type = "";
		debet = "";
		credit = "";
		ballance = "";
		operator = "";
	}

	public int getLine() {
		return line;
	}

	public void setLine(int line) {
		this.line = line;
	}

	public String getNr() {
		return nr;
	}

	public void setNr(String nr) {
		this.nr = nr;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDebet() {
		return debet;
	}

	public void setDebet(String debet) {
		this.debet = debet;
	}

	public String getCredit() {
		return credit;
	}

	public void setCredit(String credit) {
		this.credit = credit;
	}

	public String getBallance() {
		return ballance;
	}

	public void setBallance(String ballance) {
		this.ballance = ballance;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

}
