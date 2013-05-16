package org.onlinetaskforce.web.frontend.window;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;

/**
 * This interface can be used when you have a page or component that has a contentpanel. When you want to switch
 * contentpanels in a button or a link, you can use
 * <code>
 * this.findParent(ContentPanelAware.class).update(AjaxRequestTarget target, ContentPanelProvider provider);
 * </code>
 * <p/>
 * The page or component should then do whatever is needed to switch contentpanels.
 *
 * @author Yves Cieters
 * @version $Revision$
 * @since 16/08/11
 */
public interface ContentPanelAware {
    /**
     * Updates the current content panel with the component created by the provider.
     *
     * @param target   target used to update the contentpanel after the update
     * @param provider the provider that creates a new contentpanel
     * @return the new component (created by the provider)
     */
    Component update(AjaxRequestTarget target, ContentPanelProvider provider);
}
