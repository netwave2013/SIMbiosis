package org.simbiosis.ui.gl.input.client.rpc;

import java.util.Date;
import java.util.List;

import org.simbiosis.ui.gl.input.shared.CoaDv;
import org.simbiosis.ui.gl.input.shared.GLTransDv;
import org.simbiosis.ui.gl.input.shared.GLTransItemDv;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("accounting")
public interface GlService extends RemoteService {
	List<CoaDv> listCoaForTransaction(String key);

	List<GLTransDv> listGLTransByDate(String key, Date begin, Date end,
			Integer status);

	List<GLTransItemDv> listGLTransCoa(String key, Date begin, Date end,
			Long coa);

	Long saveGLTrans(String key, GLTransDv transDv);

	GLTransDv getGLTrans(Long id);

	void removeGLTrans(Long id);

	void releaseGLTrans(String key, List<Long> idList, Boolean released);

	void postGLTrans(String key, List<Long> idList, Boolean posted);

}
