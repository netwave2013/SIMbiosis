package org.simbiosis.microbanking.reporting;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.bp.micbank.ISavingBp;
import org.simbiosis.microbank.SavingTransactionDto;
import org.simbiosis.microbanking.reporting.model.SavingTransDv;
import org.simbiosis.printing.lib.ValidationServlet;

@WebServlet("/getSavingBook")
public class SavingBook extends ValidationServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/SavingBp")
	ISavingBp savingBp;

	String[] strType = { "00", "01", "02", "03", "04", "05", "06", "07", "08",
			"09" };
	SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	int space = 2;
	int maxLine = 38;
	int middleLine = 17;

	public SavingBook() {
		super("SavingBook");
	}

	@Override
	protected void onRequest(HttpServletRequest request) throws ServletException,
			IOException {

		String data = request.getParameter("data");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String[] datas = data.split("<>");
		try {
			long id = Long.parseLong(datas[0]);
			Date date = sdf.parse(datas[1]);
			int nuc = Integer.parseInt(datas[2]);
			//
			prepare();
			//
			setBeanCollection(listSavingTransForPrint(id, date, nuc));
			//
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String formatNumber(double value) {
		DecimalFormatSymbols fs = DecimalFormatSymbols.getInstance();
		fs.setDecimalSeparator(',');
		fs.setGroupingSeparator('.');
		DecimalFormat df = new DecimalFormat("#,##0.00");
		df.setDecimalFormatSymbols(fs);
		return df.format(value);
	}

	public List<SavingTransDv> listSavingTransForPrint(long id, Date date,
			int nuc) {
		int myNuc = nuc;
		List<SavingTransactionDto> dtoList = savingBp.listSavingTransForPrint(
				id, date, myNuc);
		List<SavingTransDv> dvList = new ArrayList<SavingTransDv>();
		//
		if (myNuc == 0) {
			myNuc = savingBp.getLastNuc(id)+1;
			if (myNuc >= (maxLine - space - 1))
				myNuc = 1;
		}
		//
		int i = 1;
		//
		List<SavingTransDv> endResultList = new ArrayList<SavingTransDv>();
		while (i < myNuc) {
			endResultList.add(new SavingTransDv());
			i++;
		}
		//
		double ballance = 0;
		if (nuc == 0) {
			ballance = savingBp.getBallanceBeforeNuc(id);
		} else {
			ballance = savingBp.getBallanceBefore(id, date);
		}
		for (SavingTransactionDto dto : dtoList) {
			SavingTransDv transDv = new SavingTransDv();
			transDv.setId(dto.getId());
			transDv.setNr("" + i);
			transDv.setDate(df.format(dto.getDate()));
			if (dto.getType() >= 0 && dto.getType() <= 9) {
				transDv.setType(strType[dto.getType()]);
			} else {
				transDv.setType("" + dto.getType());
			}
			if (dto.getDirection() == 1) {
				ballance += dto.getValue();
				transDv.setDebet("");
				transDv.setCredit(formatNumber(dto.getValue()));
				transDv.setBallance(formatNumber(ballance));
			} else {
				ballance -= dto.getValue();
				transDv.setDebet(formatNumber(dto.getValue()));
				transDv.setCredit("");
				transDv.setBallance(formatNumber(ballance));
			}
			// Teller tellerDto = teller.getTeller(dto.get);
			transDv.setOperator("T001");
			//
			dvList.add(transDv);
			savingBp.saveNUC(dto.getId(), i);
			i++;
			if (i >= (maxLine - space))
				break;
		}
		//
		int line = myNuc;
		for (SavingTransDv trans : dvList) {
			endResultList.add(trans);
			line++;
			if (line == middleLine) {
				endResultList.add(new SavingTransDv());
				line++;
				endResultList.add(new SavingTransDv());
				line++;
			}
			if (line >= maxLine)
				break;
		}
		//
		return endResultList;
	}

}
