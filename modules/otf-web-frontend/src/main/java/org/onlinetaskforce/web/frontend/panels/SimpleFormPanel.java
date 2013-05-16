package org.onlinetaskforce.web.frontend.panels;

import org.apache.commons.collections.CollectionUtils;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.web.frontend.forms.BasicForm;
import org.onlinetaskforce.web.frontend.validators.BaseValidator;

import java.util.List;

/**
 * Abstract SimpleFormPanel, this simple edit panel contains form fields, labels and a submit button
 *
 * @param <T> Model type
 * @author jordens
 * @since 10/03/13
 */
public abstract class SimpleFormPanel<T> extends BasicPanel {

    private Form<T> editForm;

    /**
     * Instantiates the PojoFormPanel
     *
     * @param id    The component id
     * @param model The model to show or edit
     */
    public SimpleFormPanel(String id, IModel<T> model) {
        super(id, model);

        editForm = new EditForm("editForm", new CompoundPropertyModel<T>(model));

        // add the editForm
        add(editForm);
    }

    /**
     * Override this method to add custom fields to the form
     * Don't forget to add a <<fieldname>>-feedback element for each field or Wicket will fail in markup validation
     *
     * @param form
     * @return List with fields
     */
    protected abstract List<FormComponent> getFormFields(Form<T> form);

    /**
     * Override this method to add custom validators to the form
     * Don't forget to add a <<fieldname>>-feedback element for each field or Wicket will fail in markup validation
     *
     * @return List with validators
     */
    protected abstract List<BaseValidator> getCustomValidators();

    /**
     * Override this method to add custom labels to the form
     * Labels are
     *
     * @return List with labels
     */
    protected abstract List<WebComponent> getFormLabels();

    /**
     * Override this method to create a custom implementation for the submit action
     *
     * @param model The model
     * @throws BusinessException BusinessException
     *                           xceptions are shown on the page
     * @throws RuntimeException  Runtime exceptions are catched by the VoRequestCycleListener
     */
    protected abstract void onSubmitAction(IModel<T> model) throws BusinessException, RuntimeException;

    /**
     * Indicates wether the Submit button is enabled
     *
     * @return See description
     */
    protected boolean isSubmitEnabled() {
        return true;
    }

    /**
     * Indicates wether the Reset/Cancel button is enabled
     *
     * @return See description
     */
    protected boolean isResetEnabled() {
        return true;
    }

    /**
     * @param model The model
     */
    protected void onResetAction(IModel<T> model) {
        editForm.clearInput();
    }


    private void onSubmitHandleExceptions(IModel<T> model) {
        try {
            onSubmitAction(model);
        } catch (BusinessException e) {
            showBusinessException(e);
        }
    }

    protected Button getSubmitButton() {
        // add submit button
        return new Button("btnSave", new ResourceModel("button.label.submit")) {
            @Override
            public void onSubmit() {
                onSubmitHandleExceptions((IModel<T>) getForm().getModel());
            }

            @Override
            public void onConfigure() {
                setVisible(isSubmitEnabled());
            }
        };
    }

    protected Button getResetButton() {
        // add submit button
        return new Button("btnReset", new ResourceModel("button.label.reset")) {
            @Override
            public void onSubmit() {
                onResetAction((IModel<T>) getForm().getModel());
            }

            @Override
            public void onConfigure() {
                setVisible(isResetEnabled());
            }
        };
    }

    /**
     * The editform
     */
    private class EditForm extends BasicForm<T> { // NOSONAR

        /**
         * Instantiates the EditForm
         *
         * @param id    The component id
         * @param model The model
         */
        public EditForm(String id, IModel<T> model) { // NOSONAR
            super(id, model);
        }

        @Override
        protected void onInitialize() {
            super.onInitialize();
            List<FormComponent> fields = getFormFields(EditForm.this);
            // add the fields
            if (fields != null) {
                for (FormComponent field : fields) {
                    addFormField(field);
                }
            }
            List<WebComponent> labels = getFormLabels();
            // add the labels
            if (labels != null) {
                for (WebComponent label : labels) {
                    add(label);
                }
            }

            //validators
            List<BaseValidator> validators = getCustomValidators();
            if (validators != null) {
                for (BaseValidator validator : validators) {
                    if (CollectionUtils.isNotEmpty(fields)) {
                        validator.setFormComponents(fields.toArray(new FormComponent[fields.size()]));
                    }
                    if (CollectionUtils.isNotEmpty(labels)) {
                        validator.setWebComponents(labels.toArray(new WebComponent[labels.size()]));
                    }
                    //validator.set
                    add(validator);
                }
            }


            // add submit button
            add(getSubmitButton());

            // add reset button
            add(getResetButton());
        }
    }

    public Form<T> getEditForm() {
        return editForm;
    }

    public void setEditForm(Form<T> editForm) {
        this.editForm = editForm;
    }
}
