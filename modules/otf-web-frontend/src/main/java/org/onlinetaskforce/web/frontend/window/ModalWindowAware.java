package org.onlinetaskforce.web.frontend.window;

import org.apache.wicket.extensions.ajax.markup.html.modal.ModalWindow;

/**
 * Class ModalWindowAware.
 *
 * @author Sophie Vercruyssen
 * @version $Revision$
 * @since 14/04/11
 */
public interface ModalWindowAware {
    ModalWindow getWindow();
}
