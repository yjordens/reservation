package org.onlinetaskforce.web.frontend.pages.contact;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.pages.HomeLoginPage;
import org.onlinetaskforce.web.frontend.panels.contact.ContactFormPageContentPanel;

public class ContactFormPage extends BasicPage {
	private static final long serialVersionUID = 1L;

    private ContactFormPageContentPanel contentPanel;

	public ContactFormPage(final PageParameters parameters) {
		super(parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new ContactFormPageContentPanel("content-panel");
        contentContainer.add(contentPanel);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);    //To change body of overridden methods use File | Settings | File Templates.
        response.render(CssHeaderItem.forReference(new CssResourceReference(HomeLoginPage.class, "../css/jquery.ui.all.css")));
    }
}
