package org.simbiosis.ui.gl.input.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class GLTransItemViewerExt extends SimpleGrid<GLTransItemDv> {

	DateTimeFormat df = DateTimeFormat.getFormat("dd/MM/yyyy");
	NumberFormat nf = NumberFormat.getFormat("####,###.00");

	ColumnDef<GLTransItemDv, String> colNr = new ColumnDef<GLTransItemDv, String>(
			ColumnType.LABEL, "No", "34px", "30px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return "" + data.getNr();
		}
	};

	ColumnDef<GLTransItemDv, String> colDate = new ColumnDef<GLTransItemDv, String>(
			ColumnType.LABEL, "Tanggal", "79px", "75px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return df.format(data.getDate());
		}
	};

	ColumnDef<GLTransItemDv, String> colCode = new ColumnDef<GLTransItemDv, String>(
			ColumnType.LABEL, "Kode", "129px", "125px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return data.getCode();
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
			return nf.format(data.getDebit() == null ? 0 : data.getDebit());
		}
	};

	ColumnDef<GLTransItemDv, String> colCredit = new ColumnDef<GLTransItemDv, String>(
			ColumnType.LABEL, "Kredit", "154px", "150px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return nf.format(data.getCredit() == null ? 0 : data.getCredit());
		}
	};

	ColumnDef<GLTransItemDv, String> colSaldo = new ColumnDef<GLTransItemDv, String>(
			ColumnType.LABEL, "Saldo", "154px", "150px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return nf.format(data.getSaldo());
		}
	};

	public GLTransItemViewerExt() {
		//
		addColumn(colNr);
		addColumn(colDate);
		addColumn(colCode);
		addColumn(colDesc);
		colDebit.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colDebit);
		colCredit.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colCredit);
		colSaldo.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colSaldo);
	}
}
