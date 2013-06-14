package org.simbiosis.printing.lib;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;

public class ReportPrinting extends SimbiosisJasper {

	public ReportPrinting() {
		super();
	}

	public ReportPrinting(String formName) {
		super(formName);
	}

	@Override
	public void setupExporter(JRExporter exporter) {
		switch (getType()) {
		case 1:
			break;
		default:
			exporter.setParameter(JRHtmlExporterParameter.ZOOM_RATIO, 1.27F);
			exporter.setParameter(JRExporterParameter.OFFSET_X, 5);
			exporter.setParameter(JRExporterParameter.OFFSET_Y, 5);
		}
	}

}
