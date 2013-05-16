package org.onlinetaskforce.web.frontend.window;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

/**
 * If you want something special as linkContent (not just text), you should override this class, and create custom markup.
 * <p/>
 * LINK_CONTENT_ID was removed since it is not needed anymore. Use new AjaxLink("linkId").setBody(Model.of("linkBodyText")) instead.
 *
 * @author Sofie Muys
 * @version $Revision$
 * @since 10:30 AM
 */
public class LinkedModalWindowContainer<W extends ModalWindow> extends ModalWindowContainer<AjaxLink, W> {
    private static final long serialVersionUID = -9043876429429289081L;

    public LinkedModalWindowContainer(String id, AjaxLink trigger, W window) {
        super(id, trigger, window);
    }

    public LinkedModalWindowContainer(String id, AjaxLink trigger) {
        super(id, trigger);
    }

    public LinkedModalWindowContainer(String id, W window) {
        super(id, window);
    }
}