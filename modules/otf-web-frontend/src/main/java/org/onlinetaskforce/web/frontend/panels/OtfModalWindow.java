package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.model.IModel;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.web.frontend.window.BaseModalWindow;
import org.onlinetaskforce.web.frontend.window.WindowSize;

/**
 * @author jordens
 * @since 8/03/13
 */
public abstract class OtfModalWindow extends BaseModalWindow {
    /**
     * Instantiates the window
     *
     * @param id Component id
     */
    public OtfModalWindow(String id) {
        super(id);
    }

    /**
     * Instantiates the window
     *
     * @param id    Component id
     * @param model The model
     */
    public OtfModalWindow(String id, IModel model) {
        super(id, model);
    }

    @Override
    protected WindowSize getDefaultWindowSize() {
        return new WindowSize(450, 300);
    }

    protected abstract void onSubmitAction(AjaxRequestTarget target, IModel model) throws BusinessException;
}
