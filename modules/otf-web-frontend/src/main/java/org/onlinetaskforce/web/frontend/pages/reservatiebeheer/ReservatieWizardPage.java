package org.onlinetaskforce.web.frontend.pages.reservatiebeheer;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.web.frontend.authorisation.SecurePage;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.reservatiebeheer.ReservatieWizardPageContentPanel;

public class ReservatieWizardPage extends BasicPage implements SecurePage {
	private static final long serialVersionUID = 1L;

    private ReservatieWizardPageContentPanel contentPanel;

    public ReservatieWizardPage(final PageParameters parameters) {
        this();
    }

    public ReservatieWizardPage() {
        super(null);

        contentPanel = new ReservatieWizardPageContentPanel("content-panel", new Model<Reservatie>(new Reservatie()));
        contentContainer.add(contentPanel);
    }

    public ReservatieWizardPage(String message) {
        this();
        success(message);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }
}
