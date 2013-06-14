package org.simbiosis.microbanking.reporting;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.gl.FinReportDto;
import org.simbiosis.gl.IFinReport;
import org.simbiosis.microbanking.reporting.model.NeracaItemDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getLabaRugiHarian")
public class LabaRugiHarian extends ReportServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/GlEar/GlEjb/FinReportImpl")
	IFinReport report;

	String[] months = { "", "Jan", "Feb", "Mar", "Apr", "Mei", "Jun", "Jul",
			"Agu", "Sep", "Okt", "Nov", "Des" };
	String[] types = { "", "AKTIVA", "PASIVA", "PENDAPATAN OPS", "BEBAN OPS" };
	long branch;
	Date date;

	public LabaRugiHarian() {
		super("LabaRugi");
	}

	List<NeracaItemDv> prepareData() throws ParseException {
		Map<Long, NeracaItemDv> ledgerMap = new HashMap<Long, NeracaItemDv>();
		Map<Long, NeracaItemDv> subLedgerMap = new HashMap<Long, NeracaItemDv>();
		// SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		List<NeracaItemDv> result = new ArrayList<NeracaItemDv>();
		List<FinReportDto> listSaving = report.listDailyFinReport(getCompany(),
				branch, date);
		for (FinReportDto dto : listSaving) {
			if (dto.getGroup() == 3 || dto.getGroup() == 4) {
				NeracaItemDv ledger = ledgerMap.get(dto.getCoaGrandParent());
				if (ledger == null) {
					ledger = new NeracaItemDv();
					ledger.setTipeNeraca(types[dto.getGroup()]);
					ledger.setLedger(dto.getGrandParentCode() + " - "
							+ dto.getGrandParentDescription());
					ledger.setLedgerWoCode(dto.getGrandParentDescription());
					ledger.setValueNow(dto.getEndValue());
					result.add(ledger);
				} else {
					ledger.setValueNow(ledger.getValueNow() + dto.getEndValue());
				}
				ledgerMap.put(dto.getCoaGrandParent(), ledger);
				//
				NeracaItemDv subLedger = subLedgerMap.get(dto.getCoaParent());
				if (subLedger == null) {
					subLedger = new NeracaItemDv();
					subLedger.setTipeNeraca(types[dto.getGroup()]);
					subLedger.setLedger(dto.getGrandParentCode() + " - "
							+ dto.getGrandParentDescription());
					subLedger.setSubLedger(dto.getParentCode() + " - "
							+ dto.getParentDescription());
					subLedger.setSubSubLedger(dto.getCode() + " - "
							+ dto.getDescription());
					subLedger.setLedgerWoCode(dto.getGrandParentDescription());
					subLedger.setSubLedgerWoCode(dto.getParentDescription());
					subLedger.setSubSubLedgerWoCode(dto.getDescription());
					subLedger.setValueNow(dto.getEndValue());
					ledger.getChildren().add(subLedger);
				}
				ledgerMap.put(dto.getCoaGrandParent(), ledger);
			}
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
			setParameter("Neraca.company", getCompanyName());
			setParameter("Neraca.branch", branchName);
			setParameter("Neraca.period", strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
