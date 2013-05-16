package org.onlinetaskforce.web.frontend.pages.diensten;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.diensten.WebsiteToepPageContentPanel;

public class WebsiteToepPage extends BasicPage {
	private static final long serialVersionUID = 1L;

    private WebsiteToepPageContentPanel contentPanel;

	public WebsiteToepPage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new WebsiteToepPageContentPanel("content-panel");
        contentContainer.add(contentPanel);

    }
}
