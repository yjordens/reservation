package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.util.ListModel;
import org.onlinetaskforce.common.exceptions.MultipleBusinessException;
import org.onlinetaskforce.web.frontend.window.WindowSize;

import java.util.List;

/**
 * @author jordens
 * @since 8/03/13
 */
public abstract class BusinessConfirmationNoFormDialog extends ConfirmationDialog {
    private BusinessConfirmationNoFormDialogPanel businessConfirmationDialogPanel;
    private List<String> businessValidationList;
    private MultipleBusinessException mbe;

    /**
     * Instantiates the panel
     *
     * @param id Component's id
     */
    public BusinessConfirmationNoFormDialog(String id) {
        super(id);
    }

    @Override
    protected WindowSize getDefaultWindowSize() {
        return new WindowSize(550, 200);
    }

    @Override
    protected Panel getContentPanel(String id) {
        if (businessConfirmationDialogPanel == null) {
                businessConfirmationDialogPanel = new BusinessConfirmationNoFormDialogPanel(id, new ListModel<String>() {
                @Override
                public List<String> getObject() {
                    return businessValidationList; //todo refactor this to use mbe.retrieveAllbusinessKeys
                }
            });
        }
        businessConfirmationDialogPanel.setOutputMarkupId(true);
        return businessConfirmationDialogPanel;
    }

    public MultipleBusinessException getMbe() {
        return mbe;
    }

    public void setMbe(MultipleBusinessException mbe) {
        this.mbe = mbe;
    }
}
