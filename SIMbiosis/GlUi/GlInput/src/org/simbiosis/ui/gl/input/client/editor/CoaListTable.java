package org.simbiosis.ui.gl.input.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.gl.input.shared.CoaDv;

public class CoaListTable extends SimpleGrid<CoaDv> {

	ColumnDef<CoaDv, String> colCode = new ColumnDef<CoaDv, String>(
			ColumnType.LABEL,"Kode","104px","100px") {

		@Override
		public String getDataValue(CoaDv data) {
			return data.getCode();
		}
	};

	ColumnDef<CoaDv, String> colDescription = new ColumnDef<CoaDv, String>(
			ColumnType.LABEL,"Keterangan") {

		@Override
		public String getDataValue(CoaDv data) {
			return data.getDescription();
		}
	};

	public CoaListTable() {
		addColumn(colCode);
		addColumn(colDescription);
	}

}
