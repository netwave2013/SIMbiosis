package org.simbiosis.ui.gl.admin.shared;

import com.google.gwt.user.client.rpc.IsSerializable;

public class BranchDv implements IsSerializable {
	long id;
	String code;
	String name;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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
	
	@Override
	public String toString() {
		return code + " - " + name;
	}


}
