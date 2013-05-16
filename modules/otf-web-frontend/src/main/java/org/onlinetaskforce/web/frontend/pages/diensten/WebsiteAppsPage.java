package org.onlinetaskforce.web.frontend.pages.diensten;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.diensten.WebsiteAppsPageContentPanel;

public class WebsiteAppsPage extends BasicPage {
	private static final long serialVersionUID = 1L;

    private WebsiteAppsPageContentPanel contentPanel;

	public WebsiteAppsPage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new WebsiteAppsPageContentPanel("content-panel");
        contentContainer.add(contentPanel);

    }
}
