package org.onlinetaskforce.web.frontend.pages.diensten;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.diensten.TrainingPageContentPanel;

public class TrainingPage extends BasicPage {
	private static final long serialVersionUID = 1L;

    private TrainingPageContentPanel contentPanel;

	public TrainingPage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new TrainingPageContentPanel("content-panel");
        contentContainer.add(contentPanel);

    }
}
