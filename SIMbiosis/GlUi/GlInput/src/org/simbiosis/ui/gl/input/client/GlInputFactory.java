package org.simbiosis.ui.gl.input.client;

import org.kembang.module.client.mvp.KembangClientFactory;
import org.simbiosis.ui.gl.input.client.coatrans.ICoaTrans;
import org.simbiosis.ui.gl.input.client.juinput.IJUForm;
import org.simbiosis.ui.gl.input.client.julist.IJUList;
import org.simbiosis.ui.gl.input.client.postlist.IPost;

public interface GlInputFactory extends KembangClientFactory {

	IJUList getJUListForm();

	IJUForm getJUEditor();

	IJUForm getJUViewer();

	IPost getPostForm();

	ICoaTrans getCoaTrans();
}
