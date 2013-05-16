package org.onlinetaskforce.web.frontend.pages.diensten;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.diensten.WebsitePageContentPanel;

public class WebsitePage extends BasicPage {
	private static final long serialVersionUID = 1L;

    private WebsitePageContentPanel contentPanel;

	public WebsitePage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new WebsitePageContentPanel("content-panel");
        contentContainer.add(contentPanel);

    }
}
