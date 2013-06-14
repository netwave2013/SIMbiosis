package org.simbiosis.ui.gl.input.client.rpc;

import java.util.Date;
import java.util.List;

import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface GlServiceAsync {

	void listCoaForTransaction(String key, AsyncCallback<List<CoaDv>> callback);

	void listGLTransByDate(String key, Date begin, Date end, Integer status,
			AsyncCallback<List<GLTransDv>> callback);

	void getGLTrans(Long id, AsyncCallback<GLTransDv> callback);

	void saveGLTrans(String key, GLTransDv transaction,
			AsyncCallback<Long> callback);

	void removeGLTrans(Long id, AsyncCallback<Void> callback);

	void releaseGLTrans(String key, List<Long> idList, Boolean released,
			AsyncCallback<Void> callback);

	void postGLTrans(String key, List<Long> idList, Boolean posted,
			AsyncCallback<Void> callback);

	void listGLTransCoa(String key, Date begin, Date end, Long coa,
			AsyncCallback<List<GLTransItemDv>> callback);

}
