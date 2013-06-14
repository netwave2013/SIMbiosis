package org.simbiosis.ui.bprs.reporting.client.rpc;

import java.util.List;

import org.kembang.module.shared.BranchDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface ReportSrvAsync {

	void loadBranch(String key, AsyncCallback<List<BranchDv>> callback);

}
