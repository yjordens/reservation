package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.markup.html.WebComponent;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.model.IModel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author vanlooni
 * @since 21/03/12
 */
public class ConfirmationDialogPanel extends OtfModalWindowContent {

    /**
     * Instantiates the panel
     * @param id Component's id
     * @param model The model
     */
    public ConfirmationDialogPanel(String id, IModel model) {
        super(id, model);
    }

    @Override
    protected List<FormComponent> getFormFields() {
        return null;
    }

    @Override
    protected List<WebComponent> getFormLabels() {
        List<WebComponent> labels = new ArrayList<WebComponent>();
        labels.add(new Label("message", getDefaultModel()));
        return labels;
    }
}
