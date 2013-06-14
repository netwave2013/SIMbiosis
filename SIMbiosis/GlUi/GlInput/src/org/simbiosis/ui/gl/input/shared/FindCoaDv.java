package org.simbiosis.ui.gl.input.shared;

import java.util.ArrayList;
import java.util.List;

public class FindCoaDv {
	List<CoaDv> resultTable = new ArrayList<CoaDv>();

	public FindCoaDv() {
	}
	
	public FindCoaDv(List<CoaDv> listCoa) {
		resultTable.addAll(listCoa);
	}

	public List<CoaDv> getResultTable() {
		return resultTable;
	}

	public void setResultTable(List<CoaDv> resultTable) {
		this.resultTable = resultTable;
	}

}
