package org.simbiosis.printing.lib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public abstract class SimbiosisJasper {

	int type;
	String path;
	String location;
	String formName;
	String imagesUri;

	JasperReport jasperReport = null;
	JasperPrint jasperPrint = null;
	Map<String, Object> parameters = new HashMap<String, Object>();
	JRBeanCollectionDataSource jrCollection = null;

	public SimbiosisJasper() {
		initValue();
	}

	public SimbiosisJasper(String formName) {
		this.formName = formName;
		initValue();
	}

	private void initValue() {
		imagesUri = new String("/BprsReportingService/servlets/image?image=");
		type = 0;
	}

	public void setupPath(String serverPath, String subPath) {
		//
		path = new String(serverPath + "/reports/");
		location = subPath;
	}

	public void prepare() {
		try {
			jasperReport = (JasperReport) JRLoader.loadObjectFromFile(path
					+ location + "/" + formName + ".jasper");
			//
			parameters.put("SUBREPORT_DIR", path + location + "/");
		} catch (Exception ex) {
			System.out.println("EXCEPTION: " + ex);
		}
	}

	@SuppressWarnings("rawtypes")
	public void setBeanCollection(List collection) {
		jrCollection = new JRBeanCollectionDataSource(collection);
	}

	public void print(HttpServletResponse response) {
		try {
			if (jrCollection == null) {
				jasperPrint = JasperFillManager.fillReport(jasperReport,
						parameters, new JREmptyDataSource());
			} else {
				jasperPrint = JasperFillManager.fillReport(jasperReport,
						parameters, jrCollection);
			}
			JRExporter exporter;
			switch (type) {
			case 1:
				response.setHeader("Content-Disposition",
						"inline, filename=report.pdf");
				response.setContentType("application/pdf");
				exporter = new JRPdfExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
						response.getOutputStream());
				exporter.exportReport();

				break;
			case 2:
				response.setHeader("Content-Disposition",
						"inline, filename=report.xls");
				response.setContentType("application/vnd.ms-excel");
				exporter = new JRXlsExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
						response.getOutputStream());
				exporter.exportReport();

				break;
			default:
				exporter = new JRHtmlExporter();
				exporter.setParameter(JRHtmlExporterParameter.JASPER_PRINT,
						jasperPrint);
				setupExporter(exporter);
				exporter.setParameter(JRHtmlExporterParameter.OUTPUT_STREAM,
						response.getOutputStream());
				// exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,
				// path
				// + location + "/");
				exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
						imagesUri);
				exporter.exportReport();
			}
		} catch (Exception ex) {
			System.out.println("EXCEPTION: " + ex);
		}
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public Map<String, Object> getParameters() {
		return parameters;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public abstract void setupExporter(JRExporter exporter);

}
