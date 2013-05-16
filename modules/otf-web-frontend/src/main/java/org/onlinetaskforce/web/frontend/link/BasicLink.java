package org.onlinetaskforce.web.frontend.link;

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.web.frontend.application.OtfApplication;

import java.io.Serializable;

/**
 * Basic Link wrapper around user interactions and logic with hooks to handle Checked exceptions
 *
 * @param <T> type of the model
 * @author jordens
 * @since 10/03/13
 */
public abstract class BasicLink<T> extends Link {

    /**
     * Instantiates the BasicLink
     * @param id Component's id
     */
    public BasicLink(String id) {
        super(id);
    }

    /**
     * Instantiates the BasicLink
     * @param id Component's id
     * @param iModel COmpontns's model
     */
    public BasicLink(String id, IModel<T> iModel) {
        super(id, iModel);
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

    /**
     * Show success message from resource bundle
     * @param messageKey The message key
     */
    public void showSuccessMessage(String messageKey) {
        success(new StringResourceModel(messageKey, this, null).getString());
    }

    @Override
    public final void onClick() {
        try {
            onClickAction();
        } catch(BusinessException be) {
            showBusinessException(be.getMessage());
        }
    }

    /**
     * Subclass must provide a imlplementation of the on click action
     * @throws BusinessException Checked Business Exception
     * @throws RuntimeException Runtime exception can also be thrown by the impl
     */
    public abstract void onClickAction() throws BusinessException, RuntimeException;
}
