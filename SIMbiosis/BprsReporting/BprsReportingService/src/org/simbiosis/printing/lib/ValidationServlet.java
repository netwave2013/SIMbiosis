package org.simbiosis.printing.lib;

public abstract class ValidationServlet extends SimbiosisJasperServlet {
	private static final long serialVersionUID = 1L;

	public ValidationServlet(String jasperName) {
		super(new ValidationPrinting(jasperName));
	}

}
