package org.onlinetaskforce.web.frontend.pages;

import org.apache.wicket.Application;
import org.apache.wicket.Session;
import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;
import org.onlinetaskforce.web.frontend.panels.LoginPanel;
import org.onlinetaskforce.web.frontend.panels.UserPanel;
import org.onlinetaskforce.web.frontend.session.OtfWebSession;

/**
 * @author jordens
 * @since 8/03/13
 */
public class BasicPage extends AbstractBasicPage {
    private static final CssResourceReference OTF_LOGIN_CSS = new CssResourceReference(HomePage.class, "../css/login.css");

    private BasicPanel sidePanel;
    protected WebMarkupContainer contentContainer;

    public BasicPage(final PageParameters parameters) {
        super(parameters);

        Application.get().getMarkupSettings().setStripWicketTags(true);
        contentContainer = new WebMarkupContainer("content-container");
        add(contentContainer);


    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        OtfWebSession session = (OtfWebSession) Session.get();
        Gebruiker gebruiker = session.getGebruiker();

        if (gebruiker != null) {
            sidePanel = new UserPanel("user");
        } else {
            sidePanel = new LoginPanel("user");
        }
        add(sidePanel);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(CssHeaderItem.forReference(OTF_LOGIN_CSS));
    }

}
