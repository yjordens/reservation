package org.onlinetaskforce.web.frontend.panels;

/**
 * @author vanlooni
 * @since 28/12/11
 */

import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;

/**
 * Actionpanel contains edit and delete buttons for all records
 *
 * @author jordens
 * @since 22/04/13
 * @param <T> Generic type
 */
public class ActionPanel<T, S> extends BasicPanel {

    /**
     * Construction
     *
     * @param id model id
     * @param model model for contact
     * @param gridpanel The gridpanel
     */
    public ActionPanel(String id, final IModel<T> model, final GridPanel<T, S> gridpanel) {
        super(id, model);

        Link lnkDetail = new Link("lnkDetail") { // NOSONAR
            @Override
            public void onClick() {
                gridpanel.detailAction(model);
            }
            @Override
            public void onConfigure() {
                setVisible(gridpanel.isDetailLinkEnabled());
            }
        };

        Link lnkEdit = new Link("lnkEdit") { // NOSONAR
            @Override
            public void onClick() {
                gridpanel.createNewEditPanel(model);
            }
            @Override
            public void onConfigure() {
                setVisible(gridpanel.isEditLinkEnabled());
            }
        };

        Link lnkDelete = new Link("lnkDelete") { // NOSONAR
            @Override
            public void onClick() {
                new ConfirmPanel(gridpanel, new StringResourceModel("delete.confirmation", this, null).getString()) {
                    @Override
                    protected void onConfirm() {
                        gridpanel.deleteAction(model);
                    }
                };
            }
            @Override
            public void onConfigure() {
                setVisible(gridpanel.isDeleteLinkEnabled());
            }
        };

        // add links to the panel
        add(lnkEdit);
        add(lnkDetail);
        add(lnkDelete);
    }
}