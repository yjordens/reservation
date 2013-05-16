package org.onlinetaskforce.web.frontend.window;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

/**
 * Class ButtonedModalWindowContainer. The trigger can be an AjaxLink, or AjaxButton. THe markup always displays a button.
 * This class only serves as markup provider
 *
 * @author Sofie Muys
 * @version $Revision$
 * @since 10:01 AM
 */
public class ButtonedModalWindowContainer<W extends ModalWindow> extends LinkedModalWindowContainer<W> {
    private static final long serialVersionUID = -6990399996293021250L;

    public ButtonedModalWindowContainer(String id, AjaxLink trigger, W window) {
        super(id, trigger, window);
    }

    public ButtonedModalWindowContainer(String id, AjaxLink trigger) {
        super(id, trigger);
    }

    public ButtonedModalWindowContainer(String id, W window) {
        super(id, window);
    }
}