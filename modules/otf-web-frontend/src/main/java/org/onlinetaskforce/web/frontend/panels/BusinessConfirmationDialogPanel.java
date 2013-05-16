package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

import java.util.List;

/**
 * @author vanlooni
 * @since 21/03/12
 */
public class BusinessConfirmationDialogPanel extends OtfModalWindowContent {

    /**
     * Instantiates the panel
     * @param id Component's id
     * @param model The model
     */
    public BusinessConfirmationDialogPanel(String id, IModel<List<String>> model) {
        super(id, model);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        ListView<String> confirmationList = new ListView<String>("messages", (IModel<List<String>>) getDefaultModel()) {
            @Override
            protected void populateItem(ListItem<String> item) {
                item.add(new Label("message", new StringResourceModel("BusinessExceptionKeys." + item.getModelObject() + ".Confirmation", this, null)));
            }
        };

        getForm().add(confirmationList);
    }

    @Override
    protected List<FormComponent> getFormFields() {
        return null;
    }

    @Override
    protected List<WebComponent> getFormLabels() {
        return null;
    }
}
