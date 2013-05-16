package org.onlinetaskforce.web.frontend.button;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.web.frontend.application.OtfApplication;
import org.onlinetaskforce.web.frontend.window.FeedbackComponentAware;

/**
 * This buttons handles a certain action, and handles possible businessexceptions and validationerrors when they occur.
 * @author jordens
 * @since 8/03/13
 */
public abstract class DefaultAjaxButton extends AjaxButton {
    protected DefaultAjaxButton(String id) {
        super(id);
    }

    protected DefaultAjaxButton(String id, IModel<String> model) {
        super(id, model);
    }

    protected DefaultAjaxButton(String id, Form<?> form) {
        super(id, form);
    }

    protected DefaultAjaxButton(String id, IModel<String> model, Form<?> form) {
        super(id, model, form);
    }

    @Override
    protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
        try {
            onClick(target, form);

            this.repaintFeedbackPanel(target);
            target.add(form);
        } catch (BusinessException e) {
            error(new StringResourceModel(OtfApplication.BUSINESSEXCEPTION_PREFIX + '.' + e.getMessageKey(), this, null).getString());

            onError(target, form);
        }
    }

    @Override
    protected void onError(AjaxRequestTarget target, Form<?> form) {
        this.repaintFeedbackPanel(target);
        target.add(form);
    }

    protected abstract void onClick(AjaxRequestTarget target, Form<?> form) throws BusinessException;

    private void repaintFeedbackPanel(AjaxRequestTarget target) {
        target.add(this.findParent(FeedbackComponentAware.class).getFeedbackComponent());
    }
}
