package org.simbiosis.cli.bi.model;

public class LapKeuDv {
	String coa;
	Double oriValue;
	Integer type;
	Integer value;
	Integer level;

	public String getCoa() {
		return coa;
	}

	public void setCoa(String coa) {
		this.coa = coa;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Double getOriValue() {
		return oriValue;
	}

	public void setOriValue(Double oriValue) {
		this.oriValue = oriValue;
	}

}
