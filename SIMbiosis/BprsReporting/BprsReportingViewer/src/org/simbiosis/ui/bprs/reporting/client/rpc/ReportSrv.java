package org.simbiosis.ui.bprs.reporting.client.rpc;

import java.util.List;

import org.kembang.module.shared.BranchDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("reportsrv")
public interface ReportSrv extends RemoteService {

	List<BranchDv> loadBranch(String key);

}
