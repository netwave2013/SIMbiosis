package org.simbiosis.ui.gl.input.client.editor;

import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.ColumnValue;
import org.kembang.grid.client.SimpleGrid;
import org.simbiosis.ui.gl.input.shared.GLTransDv;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class GLTransListTable extends SimpleGrid<GLTransDv> {
	NumberFormat numberFormat = NumberFormat.getFormat("####,###.00");

	ColumnDef<GLTransDv, Boolean> colCheck = new ColumnDef<GLTransDv, Boolean>(
			ColumnType.CHECK, "X", "24px", "20px") {

		@Override
		public Boolean getDataValue(GLTransDv data) {
			return data.getChecked();
		}
	};

	ColumnDef<GLTransDv, String> colNr = new ColumnDef<GLTransDv, String>(
			ColumnType.LABEL, "Nr", "34px", "30px") {

		@Override
		public String getDataValue(GLTransDv data) {
			return "" + data.getNr();
		}
	};

	ColumnDef<GLTransDv, String> colCode = new ColumnDef<GLTransDv, String>(
			ColumnType.LABEL,"Kode", "129px", "125px") {

		@Override
		public String getDataValue(GLTransDv data) {
			return data.getCode();
		}
	};

	ColumnDef<GLTransDv, String> colDate = new ColumnDef<GLTransDv, String>(
			ColumnType.LABEL,"Tanggal", "79px", "75px") {

		@Override
		public String getDataValue(GLTransDv data) {
			return data.getStrDate();
		}
	};

	ColumnDef<GLTransDv, String> colDescription = new ColumnDef<GLTransDv, String>(
			ColumnType.LABEL, "Keterangan") {

		@Override
		public String getDataValue(GLTransDv data) {
			return data.getDescription();
		}
	};

	ColumnDef<GLTransDv, String> colValue = new ColumnDef<GLTransDv, String>(
			ColumnType.LABEL, "Nilai", "154px", "150px") {

		@Override
		public String getDataValue(GLTransDv data) {
			return numberFormat.format(data.getValue());
		}
	};

	public GLTransListTable() {
		//
		colCheck.setCheckBoxHandler(new ColumnValue<GLTransDv>() {

			@Override
			public void setDataValue(GLTransDv data) {
				data.setChecked(isChecked());
			}
		});
		addColumn(colCheck);
		addColumn(colNr);
		addColumn(colCode);
		addColumn(colDate);
		addColumn(colDescription);
		colValue.setAlign(HasHorizontalAlignment.ALIGN_RIGHT);
		addColumn(colValue);
	}
}
