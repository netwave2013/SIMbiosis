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

import org.simbiosis.bp.gl.IGlBp;
import org.simbiosis.gl.CoaDto;
import org.simbiosis.gl.GLTransItemDto;
import org.simbiosis.microbanking.reporting.model.TransactionDv;
import org.simbiosis.printing.lib.ReportServlet;

@WebServlet("/getGlTransPdf")
public class GlTransPdf extends ReportServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -67342964331769927L;

	@EJB(lookup = "java:global/GlBpEar/GlBpEjb/GlBp")
	IGlBp glBp;

	long company = 2;
	long branch;
	Date beginDate;
	Date endDate;
	long coa;

	public GlTransPdf() {
		super("GlTransPdf");
		setType(1);
	}

	List<TransactionDv> prepareData() throws ParseException {
		List<TransactionDv> result = new ArrayList<TransactionDv>();
		List<GLTransItemDto> items = glBp.listGLTransByCoa(getSession(),
				beginDate, endDate, coa);
		int i = 1;
		double saldo = 0;
		for (GLTransItemDto item : items) {
			TransactionDv dv = new TransactionDv();
			dv.setNr(i++);
			dv.setDate(item.getDate());
			dv.setCode(item.getCode());
			dv.setDescription(item.getDescription());
			if (item.getDirection() == 1) {
				dv.setDebit(item.getValue());
				dv.setCredit(0D);
				saldo += item.getValue();
			} else {
				dv.setDebit(0D);
				dv.setCredit(item.getValue());
				saldo -= item.getValue();
			}
			dv.setSubTotal(saldo*item.getFactor());
			result.add(dv);
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
			String strBeginDate = request.getParameter("beginDate");
			if (strBeginDate == null) {
				beginDate = today;
			} else {
				beginDate = sdf.parse(strBeginDate);
			}
			strBeginDate = sdf.format(beginDate);
			String strEndDate = request.getParameter("endDate");
			if (strEndDate == null) {
				endDate = today;
			} else {
				endDate = sdf.parse(strEndDate);
			}
			strEndDate = sdf.format(endDate);
			String strCoa = request.getParameter("coa");
			coa = Long.parseLong(strCoa);
			CoaDto coaDto = glBp.getCoa(coa);
			strCoa = coaDto.getCode() + " - " + coaDto.getDescription();
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
			setParameter("GlTrans.company", getCompanyName());
			setParameter("GlTrans.branch", getBranchName());
			setParameter("GlTrans.date", strBeginDate + " - " + strEndDate);
			setParameter("GlTrans.coa", strCoa);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
