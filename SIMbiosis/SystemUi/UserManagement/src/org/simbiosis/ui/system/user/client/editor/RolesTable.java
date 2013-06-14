package org.simbiosis.ui.system.user.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.system.user.shared.RoleDv;

public class RolesTable extends SimpleGrid<RoleDv> {
	ColumnDef<RoleDv, String> colNr = new ColumnDef<RoleDv, String>(
			ColumnType.LABEL, "No", "34px", "30px") {

		@Override
		public String getDataValue(RoleDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<RoleDv, String> colName = new ColumnDef<RoleDv, String>(
			ColumnType.LABEL, "Nama") {

		@Override
		public String getDataValue(RoleDv data) {
			return data.getName();
		}
	};

	public RolesTable() {
		addColumn(colNr);
		addColumn(colName);
	}

}
