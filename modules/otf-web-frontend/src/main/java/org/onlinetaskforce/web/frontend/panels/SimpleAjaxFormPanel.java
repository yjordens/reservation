package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.onlinetaskforce.common.exceptions.BusinessException;

/**
 * @param <T> Type of the model
 * @author jordens
 * @since 10/03/13
 */
public abstract class SimpleAjaxFormPanel<T> extends SimpleFormPanel<T> {

    /**
     * Instantiates the PojoFormPanel
     *
     * @param id The component id
     * @param model The model to show or edit
     */
    public SimpleAjaxFormPanel(String id, IModel<T> model) {
        super(id, model);
        getEditForm().setOutputMarkupId(true);

    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Override
    protected Button getSubmitButton() {
        // add submit button
        return new AjaxButton("btnSave", new ResourceModel("button.label.submit")) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                showBusyIndicator();
                onSubmitHandleExceptions((IModel<T>) form.getModel(), target);
                hideBusyIndicator();
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                onErrorAction(target, form);
            }
        };
    }

    @Override
    protected Button getResetButton() {
        // add reset button
        return new AjaxButton("btnReset", new ResourceModel("button.label.reset")) {

            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                onResetAction((IModel<T>) form.getModel(), target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                onErrorAction(target, form);
            }
        }.setDefaultFormProcessing(false);
    }

    private void onSubmitHandleExceptions(IModel<T> model, AjaxRequestTarget target) {
        try {
            onSubmitActionAjax(model, target);
        } catch(BusinessException e) {
            hideBusyIndicator();
            showBusinessException(e);
        }
    }

    @Override
    protected final void onSubmitAction(IModel<T> model) throws BusinessException, RuntimeException {
        // default submit behaviour is left blank
    }

    @Override
    protected final void onResetAction(IModel<T> model) {
        // default submit behaviour is left blank
    }

    protected abstract void onSubmitActionAjax(IModel<T> model, AjaxRequestTarget target) throws BusinessException, RuntimeException;

    protected void onResetAction(IModel<T> model, AjaxRequestTarget target) {
        super.onResetAction(model);
    }

    protected void onErrorAction(AjaxRequestTarget target, Form form) {
        // default error behaviour is left blank
    }
}
