package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.ResourceModel;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.web.frontend.forms.BasicForm;

import java.util.List;

/**
 * Abstract SimpleFormPanel, extend this for every domain pojo you want to edit/add
 * @author jordens
 * @since 10/03/13
 * @param <T> extends AbstractDomainPojo
 */
public abstract class PojoFormPanel<T> extends BasicPanel {

    private WebMarkupContainer previous;

    /**
     * Instantiates the PojoFormPanel
     *
     * @param parent The page to return to when cancel button is pressed
     * @param model The domain object we want to edit
     */
    public PojoFormPanel(WebMarkupContainer parent, IModel<T> model) {
        super(parent.getId());

        this.previous = parent;
        parent.replaceWith(this);

        Form editForm = new EditForm("editForm", model, getFormFields(), getFormLabels());

        // add the editForm
        add(editForm);
    }

    /**
     * Return to the previous page/panel
     */
    protected void returnToPreviousPage() {
        replaceWith(previous);
    }

    /**
     * Default cancel action, override this to provide you own implementation
     */
    protected void onCancelAction() {
        returnToPreviousPage();
    }

    /**
     * Override this method to add custom fields to the form
     * Don't forget to add a <<fieldname>>-feedback element for each field or Wicket will fail in markup validation
     *
     * @return List with fields
     */
    protected abstract List<FormComponent> getFormFields();

    /**
     * Override this method to add custom labels to the form
     * Labels are
     *
     * @return List with labels
     */
    protected abstract List<Label> getFormLabels();

     /**
     * Override this method to create a custom implementation for the submit action
      *
     * @param model The model
     * @throws BusinessException BusinessExceptions are shown on the page
     * @throws RuntimeException Runtime exceptions are catched by the VoRequestCycleListener
     */
    protected abstract void onSubmitAction(IModel<T> model) throws BusinessException, RuntimeException;

    private void onSubmitHandleExceptions(IModel<T> model) {
        try {
            onSubmitAction(model);
        } catch(BusinessException e) {
            showBusinessException(e);
        }
    }

    /**
     * The editform
     */
    private class EditForm extends BasicForm<T> { // NOSONAR

        /**
         * Instantiates the EditForm
         *
         * @param id The component id
         * @param model The domainPojo
         * @param fields The form Fields
         * @param labels The labels
         */
        public EditForm(String id, IModel<T> model, List<FormComponent> fields, List<Label> labels) { // NOSONAR
            super(id, new CompoundPropertyModel<T>(model));

            // add the fields
            if(fields != null) {
                for(FormComponent field : fields) {
                    addFormField(field);
                }
            }

            // add the labels
            if(labels != null) {
                for(Label label : labels) {
                    add(label);
                }
            }

            // add submit button
			add(new Button("btnSave", new ResourceModel("button.label.submit")) {
				public void onSubmit() {
					onSubmitHandleExceptions((IModel<T>) getForm().getModel());
				}
			});

            /* Add cancel button
             *
			 * Notice the setDefaultFormProcessing(false) call at the end. This
			 * tells wicket that when this button is pressed it should not
			 * perform any components processing (ie bind request values to the bean).
			 */
			add(new Button("btnCancel", new ResourceModel("button.label.cancel")) {
				public void onSubmit() {
					onCancelAction();
				}
			}.setDefaultFormProcessing(false));
        }
    }
}
