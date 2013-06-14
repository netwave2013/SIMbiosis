package org.simbiosis.ui.system.user.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.kembang.module.shared.UserDv;

public class UsersTable extends SimpleGrid<UserDv>{
	ColumnDef<UserDv, String> colNr = new ColumnDef<UserDv, String>(
			ColumnType.LABEL,"No","34px","30px") {

		@Override
		public String getDataValue(UserDv data) {
			return data.getNr().toString();
		}
	};

	ColumnDef<UserDv, String> colName = new ColumnDef<UserDv, String>(
			ColumnType.LABEL,"Nama","84px","80px") {

		@Override
		public String getDataValue(UserDv data) {
			return data.getName();
		}
	};

	ColumnDef<UserDv, String> colRealName = new ColumnDef<UserDv, String>(
			ColumnType.LABEL,"Nama lengkap","154px","150px") {

		@Override
		public String getDataValue(UserDv data) {
			return data.getRealName();
		}
	};

	ColumnDef<UserDv, String> colBranch = new ColumnDef<UserDv, String>(
			ColumnType.LABEL,"Cabang","134px","130px") {

		@Override
		public String getDataValue(UserDv data) {
			return data.getBranchName();
		}
	};

	ColumnDef<UserDv, String> colRole= new ColumnDef<UserDv, String>(
			ColumnType.LABEL,"Role") {

		@Override
		public String getDataValue(UserDv data) {
			return data.getRoleName();
		}
	};

	public UsersTable() {
		addColumn(colNr);
		addColumn(colName);
		addColumn(colRealName);
		addColumn(colBranch);
		addColumn(colRole);
	}

	
}
