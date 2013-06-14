package org.simbiosis.microbanking.reporting;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;

import org.simbiosis.printing.lib.ValidationServlet;

@WebServlet("/getGlNote")
public class GlNote extends ValidationServlet {
	private static final long serialVersionUID = 1L;

	public GlNote() {
		super("GlNote");
	}

	@Override
	protected void onRequest(HttpServletRequest request) throws ServletException,
			IOException {

		String data = request.getParameter("data");
		String[] baris = data.split("<>");
		//
		//
		prepare();
		//

		setParameter("GlTrans.validCode", baris[0]);
		setParameter("GlTrans.refCode", baris[1]);
		setParameter("GlTrans.debet", baris[2]);
		setParameter("GlTrans.credit", baris[3]);
		setParameter("GlTrans.description", baris[4]);
		setParameter("GlTrans.value", baris[5]);
		setParameter("GlTrans.strValue", baris[6]);
		setParameter("GlTrans.operator", baris[7]);
	}

}
