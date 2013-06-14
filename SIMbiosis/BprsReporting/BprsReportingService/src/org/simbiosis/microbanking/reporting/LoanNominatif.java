package org.simbiosis.microbanking.reporting;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getLoanNominatif")
public class LoanNominatif extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankReportEar/MicrobankReportEjb/LoanReport")
	ILoanReport report;

	long company = 2;
	long branch;
	Date date;

	public LoanNominatif() {
		super("LoanNominatif");
	}

	@Override
	protected void onRequest(HttpServletRequest request) throws ServletException,
			IOException {
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
			setBeanCollection(report.listDailyLoan(company, branch, date));
			//
			setParameter("Loan.company", getCompanyName());
			setParameter("Loan.branch", branchName);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
