package org.onlinetaskforce.web.frontend.window;

import org.apache.wicket.Component;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.panel.EmptyPanel;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * Class FeedbackAwareModalWindow.
 * @author jordens
 * @since 8/03/13
 */
public abstract class FeedbackAwareModalWindow extends BaseModalWindow implements ContentPanelAware {
    private static final long serialVersionUID = 832513144912186138L;


    protected FeedbackAwareModalWindow(String id) {
        super(id);
    }

    protected FeedbackAwareModalWindow(String id, IModel<?> model) {
        super(id, model);
    }

    @Override
    protected final Panel getInitialContentPanel() {
        return new FeedbackAwarePanel(this.getContentId()) {
            private static final long serialVersionUID = 3759457326700146080L;

            @Override
            protected Panel getContentPanel(String id) {
                //->Lazy loading. Content will be set (hence initialized) when this window is shown.
                return new EmptyPanel(id);
            }
        };
    }

    @Override
    public final void show(AjaxRequestTarget target) {
        //update de contentpanel
        this.update(target, new ContentPanelProvider() {
            @Override
            public Component create(String id) {
                return getContentPanel(getFeedbackAwarePanel().getContentId());
            }
        });

        super.show(target);
    }

    @Override
    public final Component update(AjaxRequestTarget target, ContentPanelProvider provider) {
        FeedbackAwarePanel feedbackAwarePanel = getFeedbackAwarePanel();
        Component currentContentPanel = feedbackAwarePanel.get(FeedbackAwarePanel.CONTENT_PANEL);
        Component newContentPanel = provider.create(FeedbackAwarePanel.CONTENT_PANEL);
        currentContentPanel.replaceWith(newContentPanel);
        target.add(feedbackAwarePanel);
        return newContentPanel;
    }

    private FeedbackAwarePanel getFeedbackAwarePanel() {
        return (FeedbackAwarePanel) this.getContent();
    }
}