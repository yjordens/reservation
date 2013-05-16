package org.onlinetaskforce.web.frontend.window;

import org.apache.wicket.Component;

/**
 * Interface that can be implemented by components containing a feedbackpanel.
 *
 * @author Geroen Dierckx
 * @version $Revision$
 * @since 3/25/11
 */
public interface FeedbackComponentAware {
    Component getFeedbackComponent();
}
