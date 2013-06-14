package org.simbiosis.ui.bprs.reporting.server;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;

import org.kembang.module.shared.BranchDv;
import org.simbiosis.bp.system.ISystemBp;
import org.simbiosis.system.BranchDto;
import org.simbiosis.ui.bprs.reporting.client.rpc.ReportSrv;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class ReportSrvImpl extends RemoteServiceServlet implements ReportSrv {

	@EJB(lookup = "java:global/SystemBpEar/SystemBpEjb/SystemBp")
	ISystemBp iSystemBp;

	public ReportSrvImpl() {
	}

	@Override
	public List<BranchDv> loadBranch(String key) {
		List<BranchDv> result = new ArrayList<BranchDv>();
		for (BranchDto dto : iSystemBp.listBranchByLevel(key)) {
			BranchDv dv = new BranchDv();
			dv.setId(dto.getId());
			dv.setName(dto.getName());
			result.add(dv);
		}
		return result;
	}

}
