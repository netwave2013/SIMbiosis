package org.simbiosis.ui.gl.admin.client.rpc;

import java.util.List;

import org.simbiosis.ui.gl.admin.shared.BranchDv;
import org.simbiosis.ui.gl.admin.shared.CoaDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GlServiceAsync {

	void listCoaByType(String key, Integer type,
			AsyncCallback<List<CoaDv>> callback);

	void saveCoa(String key, CoaDv coaDv, AsyncCallback<CoaDv> callback);

	void removeCoa(long id, AsyncCallback<Void> callback);

	void listCoaByParent(String key, Long parent,
			AsyncCallback<List<CoaDv>> callback);

	void listBranch(String key, AsyncCallback<List<BranchDv>> callback);

}
