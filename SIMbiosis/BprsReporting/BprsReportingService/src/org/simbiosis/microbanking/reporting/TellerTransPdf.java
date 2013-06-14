package org.simbiosis.microbanking.reporting;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.bp.micbank.ITellerBp;
import org.simbiosis.microbank.TellerTransactionDto;
import org.simbiosis.microbanking.reporting.model.TransactionDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getTellerTransPdf")
public class TellerTransPdf extends ReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -67342964331769927L;

	@EJB(lookup = "java:global/BprsBpEar/BprsBpEjb/TellerBp")
	ITellerBp tellerBp;

	long company = 2;
	long branch;
	Date date;

	public TellerTransPdf() {
		super("TellerTransPdf");
		setType(1);
	}

	List<TransactionDv> prepareData() throws ParseException {
		List<TransactionDv> result = new ArrayList<TransactionDv>();
		List<TellerTransactionDto> transDtos = tellerBp
				.listTellerTransactionByUser(getSession(), date);
		int nr = 1;
		double saldo = 0;
		for (TellerTransactionDto transDto : transDtos) {
			TransactionDv transDv = new TransactionDv();
			transDv.setNr(nr++);
			transDv.setCode(transDto.getCode());
			transDv.setDescription(transDto.getDescription());
			if (transDto.getDirection() == 1) {
				transDv.setDebit(transDto.getValue());
				transDv.setCredit(0D);
				saldo += transDto.getValue();
			} else {
				transDv.setDebit(0D);
				transDv.setCredit(transDto.getValue());
				saldo -= transDto.getValue();
			}
			transDv.setSubTotal(saldo);
			result.add(transDv);
		}
		return result;
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		//
		Date today = new Date();
		//
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			String strDate = request.getParameter("date");
			if (strDate == null) {
				date = today;
			} else {
				date = sdf.parse(strDate);
			}
			strDate = sdf.format(date);
			String strBranch = request.getParameter("branch");
			if (strBranch == null) {
				branch = getBranch();
			} else {
				branch = Long.parseLong(strBranch);
			}
			prepare();
			//
			setBeanCollection(prepareData());
			//
			setParameter("TellerTrans.company", getCompanyName());
			setParameter("TellerTrans.branch", getBranchName());
			setParameter("TellerTrans.date", strDate);
			setParameter("TellerTrans.realName", getUserRealName());
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
