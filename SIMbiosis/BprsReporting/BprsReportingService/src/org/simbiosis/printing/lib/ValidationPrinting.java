package org.simbiosis.printing.lib;

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;

public class ValidationPrinting extends SimbiosisJasper {

	public ValidationPrinting() {
		super();
	}

	public ValidationPrinting(String formName) {
		super(formName);
	}

	@Override
	public void setupExporter(JRExporter exporter) {
		exporter.setParameter(JRExporterParameter.OFFSET_X, -200);
		exporter.setParameter(JRExporterParameter.OFFSET_Y, -50);
	}

}
