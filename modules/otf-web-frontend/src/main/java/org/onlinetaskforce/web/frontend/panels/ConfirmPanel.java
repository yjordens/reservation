package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;

/**
 * A basic confirm dialog that needs to be subclassed to specify the Confirm action
 * The delete action will take you to the previous page (can be extended)
 *
 * @author vanlooni
 * @since 15/11/11
 */
public abstract class ConfirmPanel extends BasicPanel {

    private final WebMarkupContainer previous;

    /**
     * Instantiates the ConfirmPanel
     *
     * @param parent The owner of the message
     * @param message The confirmation message (key from resource bundel)
     */
    public ConfirmPanel(WebMarkupContainer parent, String message) {
        super(parent.getId());

        this.previous = parent;
        parent.replaceWith(this);

        add(new Label("message", message));

        add(new Link("confirm") { // NOSONAR
            @Override
            public void onClick() {
                onConfirm();
                ConfirmPanel.this.replaceWith(previous);
            }
        });

        add(new Link("cancel") { // NOSONAR
            @Override
            public void onClick() {
                onCancel();
            }
        });
    }

    /**
     * Action when pressed the cancel link
     */
    protected void onCancel() {
        ConfirmPanel.this.replaceWith(previous);
    }

    /**
     * Enforce concrete subclass to provide an implementation for the confirm action
     */
    protected abstract void onConfirm();
}