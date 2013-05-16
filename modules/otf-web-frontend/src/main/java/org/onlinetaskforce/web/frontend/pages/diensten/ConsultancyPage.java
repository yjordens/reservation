package org.onlinetaskforce.web.frontend.pages.diensten;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.diensten.ConsultancyPageContentPanel;

public class ConsultancyPage extends BasicPage {
	private static final long serialVersionUID = 1L;

    private ConsultancyPageContentPanel contentPanel;

	public ConsultancyPage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new ConsultancyPageContentPanel("content-panel");
        contentContainer.add(contentPanel);

    }
}
