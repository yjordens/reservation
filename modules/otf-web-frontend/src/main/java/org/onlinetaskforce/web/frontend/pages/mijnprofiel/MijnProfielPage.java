package org.onlinetaskforce.web.frontend.pages.mijnprofiel;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.onlinetaskforce.web.frontend.authorisation.SecurePage;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.gebruikersbeheer.PictureUserPageContentPanel;

public class MijnProfielPage extends BasicPage implements SecurePage {
	private static final long serialVersionUID = 1L;

    private PictureUserPageContentPanel contentPanel;

	public MijnProfielPage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new PictureUserPageContentPanel("content-panel");
        contentContainer.add(contentPanel);

    }
}
