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

@WebServlet("/getLoanRemedial")
public class LoanRemedial extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankReportEar/MicrobankReportEjb/LoanReport")
	ILoanReport report;

	long company = 2;
	long branch;
	Date date;

	String[] endMonths = { "", "31-01", "28-02", "31-03", "30-04", "31-05",
			"30-06", "31-07", "31-08", "30-09", "31-10", "30-11", "31-12" };

	public LoanRemedial() {
		super("LoanRemedial");
	}

	@Override
	protected void onRequest(HttpServletRequest request)
			throws ServletException, IOException {
		Date today = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		SimpleDateFormat sdfy = new SimpleDateFormat("yyyy");
		String strMonth = request.getParameter("month");
		//
		//
		try {
			if (strMonth == null) {
				SimpleDateFormat sdfm = new SimpleDateFormat("MM");
				int month = Integer.parseInt(sdfm.format(today));
				date = sdf.parse(endMonths[month] + "-" + sdfy.format(today));
			} else {
				int month = Integer.parseInt(strMonth);
				date = sdf.parse(endMonths[month] + "-" + sdfy.format(today));
			}
			String strBranch = request.getParameter("branch");
			String branchName = "KONSOLIDASI";
			branch = (strBranch == null) ? 0 : Long.parseLong(strBranch);
			if (branch != 0) {
				branchName = getBranchName(branch);
			}
			prepare();
			//
			setBeanCollection(report.listLoanBilling(company, branch, date));
			//

			setParameter("Loan.company", getCompanyName());
			setParameter("Loan.branch", branchName);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
