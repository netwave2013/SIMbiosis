package org.simbiosis.ui.gl.input.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class GLTransItemViewer extends SimpleGrid<GLTransItemDv> {

	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");

	ColumnDef<GLTransItemDv, String> colNr = new ColumnDef<GLTransItemDv, String>(
			ColumnType.LABEL, "No", "34px", "30px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return "" + data.getNr();
		}
	};

	ColumnDef<GLTransItemDv, String> colCoa = new ColumnDef<GLTransItemDv, String>(
			ColumnType.LABEL, "Akun", "254px", "250px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return data.getCoaStr();
		}
	};

	ColumnDef<GLTransItemDv, String> colDesc = new ColumnDef<GLTransItemDv, String>(
			ColumnType.LABEL, "Keterangan") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return data.getDescription();
		}
	};

	ColumnDef<GLTransItemDv, String> colDebit = new ColumnDef<GLTransItemDv, String>(
			ColumnType.LABEL, "Debit", "154px", "150px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return numberFormat.format(data.getDebit() == null ? 0 : data
					.getDebit());
		}
	};

	ColumnDef<GLTransItemDv, String> colCredit = new ColumnDef<GLTransItemDv, String>(
			ColumnType.LABEL, "Kredit", "154px", "150px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return numberFormat.format(data.getCredit() == null ? 0 : data
					.getCredit());
		}
	};

	public GLTransItemViewer() {
		//
		addColumn(colNr);
		addColumn(colCoa);
		addColumn(colDesc);
		colDebit.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colDebit);
		colCredit.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colCredit);
	}
}
