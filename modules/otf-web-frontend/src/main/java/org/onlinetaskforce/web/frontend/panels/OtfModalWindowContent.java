package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.web.frontend.forms.BasicForm;
import org.onlinetaskforce.web.frontend.window.FeedbackComponentAware;

import java.util.List;

/**
 * @author jordens
 * @since 8/03/13
 */
public abstract class OtfModalWindowContent extends BasicPanel implements FeedbackComponentAware {
    /**
     * Initiates the content panel
     *
     * @param id    Component's id
     * @param model Model
     */
    public OtfModalWindowContent(String id, IModel model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        final FeedbackPanel feedback = new FeedbackPanel("modalFeedback");
        feedback.setOutputMarkupId(true);
        add(feedback);

        // Create the form, to use later for the buttons
        Form form = new BasicForm("form", getDefaultModel());
        if (getFormFields() != null) {
            for (FormComponent comp : getFormFields()) {
                form.add(comp);
            }
        }
        if (getFormLabels() != null) {
            for (WebComponent comp : getFormLabels()) {
                form.add(comp);
            }
        }
        add(form);

        form.add(createSubmitButton());
        form.add(createCloseButton());
    }

    private AjaxButton createCloseButton() {
        return new AjaxButton("btnClose") {
            @Override
            public void onSubmit(AjaxRequestTarget target, Form form) {
                onCancel(target);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                onCancel(target);
            }
        };
    }

    private AjaxButton createSubmitButton() {
        return new AjaxButton("btnSubmit") {
                @Override
                protected void onSubmit(AjaxRequestTarget target, Form form) {
                    OtfModalWindowContent.this.onSubmitHandleExceptions(target, form);
                    target.add(findParent(FeedbackComponentAware.class).getFeedbackComponent());
                }

                @Override
                protected void onError(AjaxRequestTarget target, Form<?> form) {
                    target.add(findParent(FeedbackComponentAware.class).getFeedbackComponent());
                }
            };
    }

    private void onSubmitHandleExceptions(AjaxRequestTarget target, Form form) {
        try {
            form.setEnabled(false);
            getModalWindow().onSubmitAction(target, form.getModel());
            form.setEnabled(true);
        } catch (BusinessException e) {
            showBusinessException(e.getMessage());
            form.setEnabled(true);
        }
    }

    private void onCancel(AjaxRequestTarget target) {
        getModalWindow().close(target);
    }

    private OtfModalWindow getModalWindow() {
        return this.findParent(OtfModalWindow.class);
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
    protected List<WebComponent> getFormLabels() {
        return null;
    }

    public Form getForm() {
        return (Form) this.get("form");
    }

    @Override
    public Component getFeedbackComponent() {
        return this.get("modalFeedback");
    }
}
