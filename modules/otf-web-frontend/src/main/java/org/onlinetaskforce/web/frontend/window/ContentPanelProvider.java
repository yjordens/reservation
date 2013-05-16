package org.onlinetaskforce.web.frontend.window;

import org.apache.wicket.Component;

/**
 * Provides a contentpanel to your {@link ContentPanelAware}.
 * When you use this interface (in combination with ContentPanelAware) you don't need to know about the "content panel id". You can just use the id given to the create method.
 * <p/>
 * User: Sofie Muys
 * Date: 9/26/12
 * Time: 12:09 PM
 */
public interface ContentPanelProvider {
    Component create(String id);
}
