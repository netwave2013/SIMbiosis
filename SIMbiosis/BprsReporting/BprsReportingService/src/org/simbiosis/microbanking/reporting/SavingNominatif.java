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

import org.simbiosis.microbanking.reporting.model.SavingNominatifDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getSavingNominatif")
public class SavingNominatif extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/MicrobankReportEar/MicrobankReportEjb/SavingReport")
	ISavingReport savingReport;

	long company = 2;
	long branch;
	Date date;

	public SavingNominatif() {
		super("SavingNominatif");
	}

	List<SavingNominatifDv> prepareData() throws ParseException {
		List<SavingNominatifDv> result = new ArrayList<SavingNominatifDv>();
		List<SavingRpt> listSaving = savingReport.listDailySaving(company,
				branch, date);
		for (SavingRpt savingRpt : listSaving) {
			SavingNominatifDv sn = new SavingNominatifDv();
			sn.setNr(savingRpt.getNr());
			sn.setCustomerName(savingRpt.getName());
			sn.setProduct(savingRpt.getProductName());
			sn.setCbg("" + savingRpt.getBranch());
			sn.setSavingCode(savingRpt.getCode());
			sn.setSaldoAwal(savingRpt.getValPrev());
			sn.setMutDebet(savingRpt.getTransCredit());
			sn.setMutCredit(savingRpt.getTransDebet());
			sn.setSaldoAkhir(savingRpt.getValAfter());
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
			setParameter("Saving.company", getCompanyName());
			setParameter("Saving.branch", branchName);
		} catch (ParseException e) {
			e.printStackTrace();
		}

	}

}
