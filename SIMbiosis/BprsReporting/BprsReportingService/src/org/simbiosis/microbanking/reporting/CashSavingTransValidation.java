package org.simbiosis.microbanking.reporting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.printing.lib.ValidationServlet;

@WebServlet("/getCashSavingTransValidation")
public class CashSavingTransValidation extends ValidationServlet {
	private static final long serialVersionUID = 1L;

	public CashSavingTransValidation() {
		super("CashSavingTransValidation");
	}

	@Override
	protected void onRequest(HttpServletRequest request) throws ServletException,
			IOException {

		String data = request.getParameter("data");
		String[] baris = data.split("<>");
		//
		//
		//
		prepare();
		//

		setParameter("BARIS1", baris[0]);
		setParameter("BARIS2",baris[1]);
		setParameter("BARIS3",baris[2]);
	}

}
