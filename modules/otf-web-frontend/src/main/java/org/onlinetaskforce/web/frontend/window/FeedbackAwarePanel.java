package org.onlinetaskforce.web.frontend.window;

import org.apache.wicket.Component;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.html.panel.Panel;

/**
 * Class FeedbackAwarePanel.
 *
 * @author Geroen Dierckx
 * @version $Revision$
 * @since 3/25/11
 */
public abstract class FeedbackAwarePanel extends Panel implements FeedbackComponentAware {
    private static final long serialVersionUID = 1858908782602641580L;
    public static final String CONTENT_PANEL = "contentPanel";
    public static final String FEEDBACK_PANEL = "feedbackPanel";

    protected FeedbackAwarePanel(String id) {
        super(id);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        this.add(new FeedbackPanel(FEEDBACK_PANEL).setOutputMarkupId(true).setOutputMarkupPlaceholderTag(true));
        this.add(this.getContentPanel(CONTENT_PANEL));
    }

    protected abstract Panel getContentPanel(String id);

    public String getContentId() {
        return CONTENT_PANEL;
    }

    @Override
    public Component getFeedbackComponent() {
        return this.get(FEEDBACK_PANEL);
    }
}
