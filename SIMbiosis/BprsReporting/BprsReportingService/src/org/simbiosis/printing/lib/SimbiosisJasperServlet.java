package org.simbiosis.printing.lib;

import java.io.IOException;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.system.BranchDto;
import org.simbiosis.system.CompanyDto;
import org.simbiosis.system.UserDto;

public abstract class SimbiosisJasperServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp iSystem;

	SimbiosisJasper engine;
	long company = 0;
	String companyName = "";
	String companyAddress = "";
	String session;
	long branch = 0;
	String branchName = "";
	String userRealName = "";

	public SimbiosisJasperServlet(SimbiosisJasper engine) {
		super();
		this.engine = engine;
		session = "";
	}

	private boolean isSessionAvailable(HttpServletResponse response,
			Cookie[] cookies) {
		response.addHeader("Access-Control-Allow-Origin", "*");
		response.setContentType("text/html");
		//
		session = "";
		for (int i = 0; i < cookies.length; i++) {
			if (cookies[i].getName().equalsIgnoreCase("sid")) {
				session = cookies[i].getValue();
			}
		}
		UserDto user = iSystem.getUserFromSession(session);
		if (user != null) {
			userRealName = user.getRealName();
			company = user.getCompany();
			branch = user.getBranch();
			CompanyDto companyDto = iSystem.getCompany(company);
			companyName = companyDto.getName();
			companyAddress = companyDto.getAddress();
			BranchDto branchDto = iSystem.getBranch(branch);
			branchName = branchDto.getName();
			return true;
		}
		return false;
	}

	private Cookie[] init(HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		engine.setupPath(iSystem.getStaticServerPath(), "bprskms");
		return cookies;
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = init(request);
		if (cookies != null) {
			if (isSessionAvailable(response, cookies)) {
				onRequest(request);
				engine.print(response);
			}
		}
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Cookie[] cookies = init(request);
		if (cookies != null) {
			if (isSessionAvailable(response, cookies)) {
				onRequest(request);
				engine.print(response);
			}
		}

	}

	public void prepare() {
		engine.prepare();
	}

	@SuppressWarnings("rawtypes")
	public void setBeanCollection(List collection) {
		engine.setBeanCollection(collection);
	}

	public void setParameter(String key, Object value) {
		engine.getParameters().put(key, value);
	}

	public SimbiosisJasper getEngine() {
		return engine;
	}

	public long getCompany() {
		return company;
	}

	public long getBranch() {
		return branch;
	}

	protected String getCompanyName() {
		return companyName;
	}

	protected String getCompanyAddress() {
		return companyAddress;
	}

	protected String getSession() {
		return session;
	}

	protected void setType(int type) {
		engine.setType(type);
	}

	public String getBranchName(long id) {
		BranchDto dto = iSystem.getBranch(id);
		return dto.getName();
	}

	public String getBranchName() {
		return branchName;
	}

	public String getUserRealName() {
		return userRealName;
	}

	protected abstract void onRequest(HttpServletRequest request)
			throws ServletException, IOException;

}
