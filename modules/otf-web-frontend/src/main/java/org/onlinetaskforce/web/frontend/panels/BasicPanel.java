package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.extensions.ajax.markup.html.AjaxIndicatorAppender;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.onlinetaskforce.web.frontend.application.OtfApplication;

import java.io.Serializable;

/**
 * @author jordens
 * @since 10/03/13
 */
public abstract class BasicPanel extends Panel {
    AjaxIndicatorAppender ajaxIndicatorAppender;

    /**
     * Instantiates the BasicPanel
     * @param id Component id
     */
    public BasicPanel(String id) {
        super(id);
    }

    /**
     * Instantiates the BasicPanel
     * @param id Component id
     * @param model Model
     */
    public BasicPanel(String id, IModel<?> model) {
        super(id, model);
    }

    /**
     * Show business exception message
     * @param messgeKey The message key
     */
    public void showBusinessException(final Serializable messgeKey) {
        // append prefix to error message
        String resourceKey = new StringBuilder().append(OtfApplication.BUSINESSEXCEPTION_PREFIX).append('.').append(messgeKey).toString();
        error(new StringResourceModel(resourceKey, this, null).getString());
    }

    protected void showBusyIndicator() {
        ajaxIndicatorAppender = new AjaxIndicatorAppender();
        add(ajaxIndicatorAppender);
    }

    protected void hideBusyIndicator() {
        remove(ajaxIndicatorAppender);
    }
}
