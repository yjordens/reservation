package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.IModel;
import org.onlinetaskforce.common.exceptions.BusinessException;

/**
 * @author jordens
 * @since 8/03/13
 */
public abstract class OtfModalWindowNoFormContent extends BasicPanel {

    private IModel model;
    private FeedbackPanel feedback;

    /**
     * Initiates the content panel
     * @param id Component's id
     * @param model Model
     */
    public OtfModalWindowNoFormContent(String id, IModel model) {
        super(id, model);

        this.model = model;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        feedback = new FeedbackPanel("modalFeedback");
        feedback.setOutputMarkupId(true);
        add(feedback);

        AjaxLink btnSubmit = new AjaxLink("btnSubmit"){
            @Override
            public void onClick(AjaxRequestTarget target) {
                OtfModalWindowNoFormContent.this.onSubmitHandleExceptions(target);
            }
        };
        add(btnSubmit);

        AjaxLink btnClose =new AjaxLink("btnClose") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                onCancel(target);
            }
        };
        add(btnClose);

    }

    private void onSubmitHandleExceptions(AjaxRequestTarget target) {
        try {
            getModalWindow().onSubmitAction(target, model);
        } catch(BusinessException e) {
            showBusinessException(e.getMessage());
        }
    }

    private void onCancel(AjaxRequestTarget target) {
        getModalWindow().close(target);
    }

    private OtfModalWindow getModalWindow() {
        return this.findParent(OtfModalWindow.class);
    }

    public FeedbackPanel getFeedback() {
        return feedback;
    }
}
