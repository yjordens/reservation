package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.onlinetaskforce.web.frontend.window.WindowSize;

/**
 * @author vanlooni
 * @since 22/03/12
 */
public abstract class ConfirmationDialog extends OtfModalWindow {

    private IModel<String> messageModel;

    /**
     * Constructs the dialog
     *
     * @param id Component's id
     */
    protected ConfirmationDialog(String id) {
        super(id);
    }

    /**
     * Instantiates the panel
     *
     * @param id           Component's id
     * @param messageModel The model
     */
    protected ConfirmationDialog(String id, IModel<String> messageModel) {
        super(id);
        this.setMessageModel(messageModel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.setTitle(new StringResourceModel("label.confirmation.dialog", this, null));
    }

    @Override
    protected WindowSize getDefaultWindowSize() {
        return new WindowSize(super.getDefaultWindowSize().getHeight(), 120);
    }

    @Override
    protected Panel getContentPanel(String id) {
        return new ConfirmationDialogPanel(id, getMessageModel());
    }

    public IModel<String> getMessageModel() {
        return this.messageModel;
    }

    public void setMessageModel(IModel<String> messageModel) {
        this.messageModel = messageModel;
    }
}
