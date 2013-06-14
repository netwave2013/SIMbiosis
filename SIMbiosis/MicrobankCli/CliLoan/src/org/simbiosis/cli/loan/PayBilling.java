package org.simbiosis.cli.loan;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Date;

import org.codehaus.jackson.map.ObjectMapper;
import org.simbiosis.cli.loan.lib.LoanBillingDv;
import org.simbiosis.microbank.LoanTransactionDto;
import org.simbiosis.utils.MicrobankCoreClient;

public class PayBilling {
	MicrobankCoreClient jsonMain;

	public PayBilling(MicrobankCoreClient jsonMain) {
		super();
		this.jsonMain = jsonMain;
	}

	public void sendPay(LoanBillingDv loan) {
		ObjectMapper mapper = new ObjectMapper();
		StringWriter sw = new StringWriter();
		try {
			LoanTransactionDto loanTransDto = new LoanTransactionDto();
			loanTransDto.setDate(new Date());
			loanTransDto.setLoan(loan.getId());
			loanTransDto.setRefCode("ADL");
			loanTransDto.setType(2);
			loanTransDto.setDirection(2);
			loanTransDto.setPrincipal(loan.getPrincipal());
			loanTransDto.setMargin(loan.getMargin());
			mapper.writeValue(sw, loanTransDto);
			jsonMain.sendRawData("payBilling", "" + loan.getSaving(), "",
					sw.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
