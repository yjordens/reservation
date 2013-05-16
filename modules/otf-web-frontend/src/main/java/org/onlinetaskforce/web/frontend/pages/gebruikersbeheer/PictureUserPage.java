package org.onlinetaskforce.web.frontend.pages.gebruikersbeheer;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.onlinetaskforce.web.frontend.authorisation.SecurePage;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.pages.HomeLoginPage;
import org.onlinetaskforce.web.frontend.panels.gebruikersbeheer.PictureUserPageContentPanel;

public class PictureUserPage extends BasicPage implements SecurePage {
	private static final long serialVersionUID = 1L;

    private PictureUserPageContentPanel contentPanel;

	public PictureUserPage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new PictureUserPageContentPanel("content-panel");
        contentContainer.add(contentPanel);

    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);    //To change body of overridden methods use File | Settings | File Templates.
        response.render(CssHeaderItem.forReference(new CssResourceReference(HomeLoginPage.class, "../fileupload/bootstrap.min.css")));
        response.render(CssHeaderItem.forReference(new CssResourceReference(HomeLoginPage.class, "../fileupload/bootstrap-responsive.min.css")));
        response.render(CssHeaderItem.forReference(new CssResourceReference(HomeLoginPage.class, "../fileupload/bootstrap-image-gallery.min.css")));
        response.render(CssHeaderItem.forReference(new CssResourceReference(HomeLoginPage.class, "../fileupload/jquery.fileupload-ui.css")));
        response.render(CssHeaderItem.forReference(new CssResourceReference(HomeLoginPage.class, "../fileupload/jquery.fileupload-ui-noscript.css")));
    }
}
