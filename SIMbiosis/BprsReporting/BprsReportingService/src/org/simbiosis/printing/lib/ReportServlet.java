package org.simbiosis.printing.lib;

public abstract class ReportServlet extends SimbiosisJasperServlet {
	private static final long serialVersionUID = 1L;

	public ReportServlet(String jasperName) {
		super(new ReportPrinting(jasperName));
	}

}
