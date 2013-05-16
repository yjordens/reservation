package org.onlinetaskforce.web.frontend.link;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.web.frontend.application.OtfApplication;

import java.io.Serializable;

/**
 * Basic AjaxLink wrapper around user interactions and logic with hooks to handle Checked exceptions
 * @author jordens
 * @since 8/03/13
 */
public abstract class BasicAjaxLink<T> extends AjaxLink<T> {

    /**
     * Instantiates the BasicLink
     *
     * @param id Component's id
     */
    public BasicAjaxLink(String id) {
        super(id);
    }

    /**
     * Instantiates the BasicLink
     *
     * @param id     Component's id
     * @param iModel COmpontns's model
     */
    public BasicAjaxLink(String id, IModel<T> iModel) {
        super(id, iModel);
    }

    /**
     * Show business exception message
     *
     * @param messgeKey The message key
     */
    public void showBusinessException(final Serializable messgeKey) {
        // append prefix to error message
        error(new StringResourceModel(OtfApplication.BUSINESSEXCEPTION_PREFIX + '.' + messgeKey, this, null).getString());
    }

    /**
     * Show success message from resource bundle
     *
     * @param messageKey The message key
     */
    public void showSuccessMessage(String messageKey) {
        success(new StringResourceModel(messageKey, this, null).getString());
    }

    @Override
    public final void onClick(AjaxRequestTarget target) {
        try {
            onClickAction(target);
        } catch (BusinessException be) {
            showBusinessException(be.getMessage());
        }
    }

    /**
     * Subclass must provide a imlplementation of the on click action
     *
     * @param target Ajax target
     * @throws BusinessException Checked Business Exception
     * @throws RuntimeException  Runtime exception can also be thrown by the impl
     */
    public abstract void onClickAction(AjaxRequestTarget target) throws BusinessException, RuntimeException;
}
