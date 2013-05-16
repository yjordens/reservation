package org.onlinetaskforce.web.frontend.pages.diensten;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.diensten.HostingPageContentPanel;

public class HostingPage extends BasicPage {
	private static final long serialVersionUID = 1L;

    private HostingPageContentPanel contentPanel;

	public HostingPage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new HostingPageContentPanel("content-panel");
        contentContainer.add(contentPanel);

    }
}
