package org.simbiosis.ui.gl.input.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class CoaDv implements IsSerializable {
	Long id;
	String code;
	String description;

	public CoaDv() {
		super();
		id = 0L;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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


	@Override
	public String toString() {
		return code + " - " + description;
	}

}
