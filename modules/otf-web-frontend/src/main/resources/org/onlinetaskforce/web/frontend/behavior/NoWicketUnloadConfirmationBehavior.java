package org.onlinetaskforce.web.frontend.behavior;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.request.Response;

/**
 * Sets the wicket unload confirmation to false.
 *
 * @author jordens
 * @since 12/05/13
 */
public class NoWicketUnloadConfirmationBehavior extends Behavior {
    @Override
    public void renderHead(Component component, IHeaderResponse response) {
        JavaScriptHeaderItem headerItem = new JavaScriptHeaderItem("") {
            @Override
            public Iterable<?> getRenderTokens() {
                return null;  //To change body of implemented methods use File | Settings | File Templates.
            }

            @Override
            public void render(Response response) {
                response.write("Wicket.Window.unloadConfirmation=false;");
            }
        };
        response.render(headerItem);
    }
}
