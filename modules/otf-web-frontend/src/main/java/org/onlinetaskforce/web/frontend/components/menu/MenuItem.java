package org.onlinetaskforce.web.frontend.components.menu;

import org.apache.commons.lang.StringUtils;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.panel.Panel;
import org.onlinetaskforce.web.frontend.links.OtfSecureBookmarkablePageLink;

/**
 * @author jordens
 * @since 9/03/13
 */
public class MenuItem extends Panel{
    public MenuItem(String id, OtfSecureBookmarkablePageLink link, String label) {
        super(id);
        if (StringUtils.isBlank(label)) {
            label="label";
        }
        Label menuItemlabel = new Label("menu-item-label", label);
        link.add(menuItemlabel);
        add(link);
    }
}
