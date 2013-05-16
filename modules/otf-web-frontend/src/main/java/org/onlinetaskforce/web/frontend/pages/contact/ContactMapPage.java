package org.onlinetaskforce.web.frontend.pages.contact;

import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.contact.ContactMapPageContentPanel;

public class ContactMapPage extends BasicPage {
	private static final long serialVersionUID = 1L;

    private ContactMapPageContentPanel contentPanel;

	public ContactMapPage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new ContactMapPageContentPanel("content-panel");
        contentContainer.add(contentPanel);
    }
}
