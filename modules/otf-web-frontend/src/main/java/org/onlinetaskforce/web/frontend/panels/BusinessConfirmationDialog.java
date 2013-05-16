package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.util.ListModel;
import org.onlinetaskforce.web.frontend.window.WindowSize;

import java.util.List;

/**
 * @author jordens
 * @since 8/03/13
 */
public abstract class BusinessConfirmationDialog extends ConfirmationDialog {

    private List<String> businessValidationList;

    /**
     * Instantiates the panel
     *
     * @param id Component's id
     */
    public BusinessConfirmationDialog(String id) {
        super(id);
    }

    @Override
    protected WindowSize getDefaultWindowSize() {
        return new WindowSize(550,200);
    }

    @Override
    protected Panel getContentPanel(String id) {
        return new BusinessConfirmationDialogPanel(id, new ListModel<String>() {
            @Override
            public List<String> getObject() {
                return businessValidationList;
            }
        });
    }

    public void setBusinessValidationList(final List<String> businessValidationList) {
        this.businessValidationList = businessValidationList;
    }
}
