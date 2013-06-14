package org.simbiosis.microbanking.reporting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.printing.lib.ValidationServlet;

@WebServlet("/getSavingMaster")
public class SavingMaster extends ValidationServlet {
	private static final long serialVersionUID = 1L;

	public SavingMaster() {
		super("SavingMaster");
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

		setParameter("Saving.code", baris[0]);
		setParameter("Saving.name", baris[1]);
		setParameter("Saving.address", baris[2]);
		setParameter("Saving.city", baris[3]);
		setParameter("Saving.date", baris[4]);
	}

}
