package org.onlinetaskforce.web.frontend.components;

import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * @author jordens
 * @since 13/06/12
 */
public class OtherComponent extends Panel {

    /**
     * construct
     */
    public OtherComponent() {
        super("otherComponent");
        add(new Label("name", ""));
    }

    /**
     * Construct for AjaxLink component
     *
     * @param button button
     */
    public OtherComponent(AjaxLink button) {
        super("otherComponent");
        add(button);
    }
}

