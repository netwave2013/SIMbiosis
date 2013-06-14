package org.simbiosis.cli.loan;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.simbiosis.cli.loan.lib.LoanBillingDv;
import org.simbiosis.utils.DateUtils;
import org.simbiosis.utils.MicrobankCoreClient;

public class ExecuteBill {

//	String host = "192.168.1.101";
	String host = "localhost";
	int port = 8080;

	String strBeginDate = new String("01-");
	String strEndDate = "";
	SimpleDateFormat sdf = new SimpleDateFormat("MM-yyyy");
	
	MicrobankCoreClient jsonMain;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		ExecuteBill penagihan = new ExecuteBill();
		penagihan.execute();
	}

	public ExecuteBill() {
		Date now = new Date();
		strBeginDate += sdf.format(now);
		strEndDate = DateUtils.dateToStr(now);
		//
		jsonMain = new MicrobankCoreClient(host, port);
		jsonMain.login("akunpus", "akunpus");
	}

	public void execute() {
		//
		System.out.println("Process billing data..." + strBeginDate + " s.d "
				+ strEndDate);
		//
		Map<Long, LoanBillingDv> loanMap = new HashMap<Long, LoanBillingDv>();

		Generator generator = new Generator(jsonMain, loanMap);
		generator.execute(strBeginDate, strEndDate);
		//
		System.out.println("Pay billing...");
		generator.payBilling();
		//
		jsonMain.logout();
	}

}
