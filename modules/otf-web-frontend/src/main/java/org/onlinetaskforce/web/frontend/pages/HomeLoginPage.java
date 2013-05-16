package org.onlinetaskforce.web.frontend.pages;

import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.util.string.StringValue;
import org.onlinetaskforce.web.frontend.panels.HomePageContentPanel;

public class HomeLoginPage extends BasicPage {
	private static final long serialVersionUID = 1L;
    public static final String NEED_TO_LOGIN = "NEED_TO_LOGIN";
    private static final CssResourceReference OTF_LOGIN_CSS = new CssResourceReference(HomeLoginPage.class, "../css/login.css");

    HomePageContentPanel contentPanel;

	public HomeLoginPage(final PageParameters parameters) {
		super(parameters);

        StringValue need_to_login = parameters.get("NEED_TO_LOGIN");
        if (need_to_login.toBoolean()) {
            error(new StringResourceModel("login.required", this, null).getString());
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new HomePageContentPanel("content-panel");
        contentContainer.add(contentPanel);

        setOutputMarkupId(true);
    }

    public HomePageContentPanel getContentPanel() {
        return contentPanel;
    }

    public void setContentPanel(HomePageContentPanel contentPanel) {
        this.contentPanel = contentPanel;
    }
}
