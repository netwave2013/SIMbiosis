package org.simbiosis.ui.gl.input.client.editor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.kembang.grid.client.AdvancedGrid;
import org.kembang.grid.client.ButtonGridHandler;
import org.kembang.grid.client.ColumnDef;
import org.kembang.grid.client.ColumnType;
import org.kembang.grid.client.ColumnValue;
import org.kembang.grid.client.FlexTableHelper;
import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.HasHorizontalAlignment;

public class GLTransItemEditor extends AdvancedGrid<GLTransItemDv> {

	List<CoaDv> listCoa = new ArrayList<CoaDv>();

	FlexTable footer = null;

	NumberFormat number = NumberFormat.getFormat("####,###.00");

	ColumnDef<GLTransItemDv, String> colNr = new ColumnDef<GLTransItemDv, String>(
			ColumnType.LABEL, "No", "34px", "30px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return "" + data.getNr();
		}
	};

	ColumnDef<GLTransItemDv, String> colCoa = new ColumnDef<GLTransItemDv, String>(
			ColumnType.SUGGEST, "Akun", "254px", "250px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			String result = "";
			if (data != null) {
				Iterator<CoaDv> iter = listCoa.iterator();
				boolean found = false;
				while (iter.hasNext() && !found) {
					CoaDv coa = iter.next();
					if (coa.getId().compareTo(data.getCoa()) == 0) {
						result = coa.toString();
						found = true;
					}
				}
			}
			return result;
		}
	};

	ColumnDef<GLTransItemDv, String> colDesc = new ColumnDef<GLTransItemDv, String>(
			ColumnType.TEXT, "Keterangan") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return data.getDescription();
		}
	};

	ColumnDef<GLTransItemDv, String> colDebit = new ColumnDef<GLTransItemDv, String>(
			ColumnType.TEXT, "Debit", "154px", "150px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return "" + (data.getDebit() == null ? 0 : data.getDebit());
		}
	};

	ColumnDef<GLTransItemDv, String> colCredit = new ColumnDef<GLTransItemDv, String>(
			ColumnType.TEXT, "Kredit", "154px", "150px") {

		@Override
		public String getDataValue(GLTransItemDv data) {
			return "" + (data.getCredit() == null ? 0 : data.getCredit());
		}
	};

	public GLTransItemEditor() {
		//
		addColumn(colNr);
		colCoa.setDataSource(listCoa);
		colCoa.setSuggestBoxHandler(new ColumnValue<GLTransItemDv>() {

			@Override
			public void setDataValue(GLTransItemDv data) {
				Iterator<CoaDv> iter = listCoa.iterator();
				boolean found = false;
				CoaDv object = null;
				while (iter.hasNext() && !found) {
					CoaDv coa = iter.next();
					if (getText().equalsIgnoreCase(coa.toString())) {
						found = true;
						object = coa;
					}
				}
				data.setCoa(object == null ? 0 : object.getId());
			}
		});
		//
		addColumn(colCoa);
		colDesc.setTextBoxHandler(new ColumnValue<GLTransItemDv>() {

			@Override
			public void setDataValue(GLTransItemDv data) {
				data.setDescription(getText());
			}
		});
		addColumn(colDesc);
		colDebit.setVisibleLength(15);
		colDebit.setTextBoxHandler(new ColumnValue<GLTransItemDv>() {

			@Override
			public void setDataValue(GLTransItemDv data) {
				data.setDebit(getText().isEmpty() ? 0.00 : Double
						.parseDouble(getText()));
				//
				if (footer != null) {
					Double values = 0.00;
					for (GLTransItemDv item : getValue()) {
						values += item.getDebit();
					}
					FlexTableHelper.setTextFooter(footer, 3,
							number.format(values));
					footer.getCellFormatter().setHorizontalAlignment(0, 3,
							HasHorizontalAlignment.ALIGN_RIGHT);
				}
			}
		});
		addColumn(colDebit);
		colCredit.setVisibleLength(15);
		colCredit.setTextBoxHandler(new ColumnValue<GLTransItemDv>() {

			@Override
			public void setDataValue(GLTransItemDv data) {
				data.setCredit(getText().isEmpty() ? 0.00 : Double
						.parseDouble(getText()));
				//
				if (footer != null) {
					Double values = 0.00;
					for (GLTransItemDv item : getValue()) {
						values += item.getCredit();
					}
					FlexTableHelper.setTextFooter(footer, 4,
							number.format(values));
					footer.getCellFormatter().setHorizontalAlignment(0, 4,
							HasHorizontalAlignment.ALIGN_RIGHT);
				}
			}
		});
		addColumn(colCredit);
	}

	public List<CoaDv> getListCoa() {
		return listCoa;
	}

	public void setListCoa(List<CoaDv> dataCoa) {
		listCoa.clear();
		listCoa.addAll(dataCoa);
	}

	//
	public void addCoaClickHandler(ButtonGridHandler clickEvent) {
		colCoa.addButton("...", clickEvent);
	}

	public void setFooter(FlexTable footer) {
		this.footer = footer;
	}
}
