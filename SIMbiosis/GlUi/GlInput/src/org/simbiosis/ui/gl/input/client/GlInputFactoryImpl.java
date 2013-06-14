package org.simbiosis.ui.gl.input.client;

import org.kembang.module.client.mvp.KembangClientFactoryImpl;
import org.simbiosis.ui.gl.input.client.coatrans.CoaTrans;
import org.simbiosis.ui.gl.input.client.coatrans.ICoaTrans;
import org.simbiosis.ui.gl.input.client.juinput.IJUForm;
import org.simbiosis.ui.gl.input.client.juinput.JUEditorImpl;
import org.simbiosis.ui.gl.input.client.juinput.JUViewerImpl;
import org.simbiosis.ui.gl.input.client.julist.IJUList;
import org.simbiosis.ui.gl.input.client.julist.JUListForm;
import org.simbiosis.ui.gl.input.client.postlist.IPost;
import org.simbiosis.ui.gl.input.client.postlist.PostForm;

public class GlInputFactoryImpl extends KembangClientFactoryImpl implements
		GlInputFactory {

	static final JUListForm JU_LIST_FORM = new JUListForm();
	static final JUViewerImpl JU_VIEWER_IMPL = new JUViewerImpl();
	static final JUEditorImpl JU_EDITOR_IMPL = new JUEditorImpl();
	static final PostForm POST_FORM = new PostForm();
	static final CoaTrans COA_TRANS = new CoaTrans();

	@Override
	public IJUList getJUListForm() {
		return JU_LIST_FORM;
	}

	@Override
	public IJUForm getJUEditor() {
		return JU_EDITOR_IMPL;
	}

	@Override
	public IJUForm getJUViewer() {
		return JU_VIEWER_IMPL;
	}

	@Override
	public IPost getPostForm() {
		return POST_FORM;
	}

	@Override
	public ICoaTrans getCoaTrans() {
		return COA_TRANS;
	}

}
