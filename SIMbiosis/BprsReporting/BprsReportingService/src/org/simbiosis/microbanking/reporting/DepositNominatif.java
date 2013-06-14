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

import org.simbiosis.microbanking.reporting.model.DepositNominatifDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getDepositNominatif")
public class DepositNominatif extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankReportEar/MicrobankReportEjb/CoreBankingReport")
	ICoreBankingReport report;

	long company = 2;
	long branch;
	Date date;

	public DepositNominatif() {
		super("DepositNominatif");
	}

	List<DepositNominatifDv> prepareData() throws ParseException {
		List<DepositNominatifDv> result = new ArrayList<DepositNominatifDv>();
		List<DepositRpt> listSaving = report.listDailyDeposit(company, branch,
				date);
		for (DepositRpt savingRpt : listSaving) {
			DepositNominatifDv sn = new DepositNominatifDv();
			sn.setNr(savingRpt.getNr());
			sn.setCustomerName(savingRpt.getName());
			sn.setProduct(savingRpt.getProductName());
			sn.setCbg("" + savingRpt.getBranch());
			sn.setDepositCode(savingRpt.getCode());
			sn.setOpenDate(savingRpt.getRegistration());
			sn.setDatebegin(savingRpt.getBegin());
			sn.setDateend(savingRpt.getEnd());
			sn.setNominal(savingRpt.getValue());
			result.add(sn);
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
			String strDate = request.getParameter("date");
			if (strDate == null) {
				date = today;
			} else {
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
				date = sdf.parse(strDate);
			}
			String strBranch = request.getParameter("branch");
			String branchName = "KONSOLIDASI";
			branch = (strBranch == null) ? 0 : Long.parseLong(strBranch);
			if (branch != 0) {
				branchName = getBranchName(branch);
			}
			prepare();
			//
			setBeanCollection(prepareData());
			//
			setParameter("Deposit.company", getCompanyName());
			setParameter("Deposit.branch", branchName);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
