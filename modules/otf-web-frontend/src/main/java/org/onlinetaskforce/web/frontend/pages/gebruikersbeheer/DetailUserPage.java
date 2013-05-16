package org.onlinetaskforce.web.frontend.pages.gebruikersbeheer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.GebruikerService;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.dto.ZoekGebruikerDto;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.persistence.utils.ThreadContextInfo;
import org.onlinetaskforce.web.frontend.authorisation.SecurePage;
import org.onlinetaskforce.web.frontend.pages.AbstractBasicPage;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.gebruikersbeheer.DetailUserPageContentPanel;

import java.util.Date;

public class DetailUserPage extends BasicPage implements SecurePage {
	private static final long serialVersionUID = 1L;

    private DetailUserPageContentPanel contentPanel;
    private  boolean komendeVanOverview;

    @SpringBean
    private GebruikerService gebruikerService;

    public DetailUserPage(final PageParameters parameters) {
        this(new Model<Gebruiker>(), new Model<ZoekGebruikerDto>(new ZoekGebruikerDto()), false);
    }

    public DetailUserPage(IModel<Gebruiker> model, final IModel<ZoekGebruikerDto> zoekGebruikerModel, boolean komendeVanOverview) {
        super(null);
        if (model.getObject() == null) {
            Gebruiker newGebruiker = new Gebruiker();
            newGebruiker.setCreatieTijdstip(new Date());
            newGebruiker.setCreatieGebruikerId(ThreadContextInfo.getInstance().getCurrentGebruikerId());
            model.setObject(newGebruiker);
        }

        contentPanel = new DetailUserPageContentPanel("content-panel", model);
        contentContainer.add(contentPanel);
        this.komendeVanOverview = komendeVanOverview;


        final AjaxButton verzendBtn = new AjaxButton("verzend-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    gebruikerService.addOrModify((Gebruiker)contentPanel.getForm().getDefaultModelObject());
                    success(new StringResourceModel("create.user.success", this, null).getString());
                } catch (BusinessException e) {
                    error(new StringResourceModel(e.getMessageKey(), this, null).getString());
                } finally {
                    AbstractBasicPage page = (AbstractBasicPage)getPage();
                    target.add(page);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                AbstractBasicPage page = (AbstractBasicPage)getPage();
                target.add(page);
            }
        };
        contentPanel.getForm().add(verzendBtn);

        final AjaxButton terugBtn = new AjaxButton("terug-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                setResponsePage(new OverviewUserPage(zoekGebruikerModel));
            }
        };
        contentPanel.getForm().add(terugBtn);
        if (!this.komendeVanOverview) {
            terugBtn.setVisible(false);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }
}
