package org.onlinetaskforce.web.frontend.pages;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.onlinetaskforce.web.frontend.panels.HomePageContentPanel;

public class HomePage extends BasicPage {
	private static final long serialVersionUID = 1L;
    HomePageContentPanel contentPanel;


	public HomePage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new HomePageContentPanel("content-panel");
        contentContainer.add(contentPanel);
    }

    public HomePageContentPanel getContentPanel() {
        return contentPanel;
    }

    public void setContentPanel(HomePageContentPanel contentPanel) {
        this.contentPanel = contentPanel;
    }
}
