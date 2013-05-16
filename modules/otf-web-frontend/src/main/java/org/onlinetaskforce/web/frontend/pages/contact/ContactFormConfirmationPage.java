package org.onlinetaskforce.web.frontend.pages.contact;

import org.apache.wicket.markup.head.CssHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.onlinetaskforce.common.dto.ContactFormDto;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.pages.HomeLoginPage;
import org.onlinetaskforce.web.frontend.panels.contact.ContactFormConfirmationPageContentPanel;

public class ContactFormConfirmationPage extends BasicPage {
	private static final long serialVersionUID = 1L;
    private ContactFormDto contactFormDto;

    private ContactFormConfirmationPageContentPanel contentPanel;

	public ContactFormConfirmationPage(final PageParameters parameters) {
		super(parameters);
    }

	public ContactFormConfirmationPage(ContactFormDto contactFormDto) {
        super(null);
        this.contactFormDto = contactFormDto;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new ContactFormConfirmationPageContentPanel("content-panel", contactFormDto);
        contentContainer.add(contentPanel);
        success(new StringResourceModel("email.contact.success", this, null).getString());
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);    //To change body of overridden methods use File | Settings | File Templates.
        response.render(CssHeaderItem.forReference(new CssResourceReference(HomeLoginPage.class, "../css/jquery.ui.all.css")));
    }
}
