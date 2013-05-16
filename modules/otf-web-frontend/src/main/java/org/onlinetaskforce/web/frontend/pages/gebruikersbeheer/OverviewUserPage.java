package org.onlinetaskforce.web.frontend.pages.gebruikersbeheer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.GebruikerService;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.dto.ZoekGebruikerDto;
import org.onlinetaskforce.web.frontend.authorisation.SecurePage;
import org.onlinetaskforce.web.frontend.models.LazyListDataProvider;
import org.onlinetaskforce.web.frontend.pages.AbstractBasicPage;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.gebruikersbeheer.OverviewUserPageContentPanel;
import org.onlinetaskforce.web.frontend.panels.gebruikersbeheer.UserListPanel;
import org.onlinetaskforce.web.frontend.panels.gebruikersbeheer.ZoekUserPanel;

import java.util.Collections;
import java.util.List;

public class OverviewUserPage extends BasicPage implements SecurePage {
	private static final long serialVersionUID = 1L;

    private IModel<ZoekGebruikerDto> zoekGebruikerModel;

    private OverviewUserPageContentPanel contentPanel;

    @SpringBean
    private GebruikerService gebruikerService;

	public OverviewUserPage(final PageParameters parameters) {
		super(parameters);
        zoekGebruikerModel = new Model<ZoekGebruikerDto>(new ZoekGebruikerDto());
    }

	public OverviewUserPage(IModel<ZoekGebruikerDto> zoekGebruikerModel) {
        super(null);
        this.zoekGebruikerModel = zoekGebruikerModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new OverviewUserPageContentPanel("content-panel");

        final ZoekUserPanel critPanel = new ZoekUserPanel("zoek-user", zoekGebruikerModel);
        critPanel.setOutputMarkupId(true);
        contentPanel.add(critPanel);

        final UserListPanel userListPanel = new UserListPanel("user-list", new LazyListDataProvider<Gebruiker>() {
            @Override
            protected List<Gebruiker> getData() {
                return getListData();
            }
        }, zoekGebruikerModel) {
            @Override
            protected void onInitialize() {
                if (zoekGebruikerModel.getObject() == null) {
                    setVisible(false);
                    zoekGebruikerModel.setObject(new ZoekGebruikerDto());
                }
                super.onInitialize();
            }
        };
        userListPanel.setOutputMarkupId(true);
        userListPanel.setOutputMarkupPlaceholderTag(true);
        contentPanel.add(userListPanel);


        AjaxButton zoekBtn = new AjaxButton("zoek-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                userListPanel.setVisible(true);
                target.add(userListPanel);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                AbstractBasicPage page = (AbstractBasicPage)getPage();
                target.add(page);
            }
        };
        critPanel.getForm().add(zoekBtn);

        AjaxButton resetBtn = new AjaxButton("reset-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                zoekGebruikerModel.setObject(new ZoekGebruikerDto());
                form.clearInput();
                form.setDefaultModelObject(zoekGebruikerModel.getObject());
                AbstractBasicPage page = (AbstractBasicPage)getPage();
                target.add(page);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                AbstractBasicPage page = (AbstractBasicPage)getPage();
                target.add(page);
            }
        };
        critPanel.getForm().add(resetBtn);

        contentContainer.add(contentPanel);

    }

    private List<Gebruiker> getListData() {
        List<Gebruiker> gebruikers = gebruikerService.overview(zoekGebruikerModel.getObject());
        if (gebruikers == null) {
            return Collections.emptyList();
        }
        return gebruikers;
    }
}
