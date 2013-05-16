package org.onlinetaskforce.web.frontend.pages.gebruikersbeheer;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.GebruikerService;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.persistence.utils.ThreadContextInfo;
import org.onlinetaskforce.web.frontend.authorisation.SecurePage;
import org.onlinetaskforce.web.frontend.pages.AbstractBasicPage;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.gebruikersbeheer.WijzigWachtwoordPanel;
import org.onlinetaskforce.web.frontend.session.OtfWebSession;

public class WachtwoordPage extends BasicPage implements SecurePage {
	private static final long serialVersionUID = 1L;

    private WijzigWachtwoordPanel contentPanel;

    @SpringBean
    private GebruikerService gebruikerService;


    public WachtwoordPage(final PageParameters parameters) {
        super(parameters);
        contentPanel = new WijzigWachtwoordPanel("content-panel");

        final AjaxButton verzendBtn = new AjaxButton("verzend-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    WijzigWachtwoordPanel.WijzigWachtwoordModel model = (WijzigWachtwoordPanel.WijzigWachtwoordModel)form.getDefaultModelObject();
                    Gebruiker gebruiker = gebruikerService.changePassword(ThreadContextInfo.getInstance().getCurrentGebruikerId(), DigestUtils.shaHex(model.getWachtwoord()), DigestUtils.shaHex(model.getNieuwWachtwoord()));
                    OtfWebSession.get().setGebruiker(gebruiker);//Make sure the gebruiker is updated on the session
                    success(new StringResourceModel("change.ww.success", this, null).getString());
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
                reset(target, form);
            }

            private void reset(AjaxRequestTarget target, Form<?> form) {
                form.clearInput();
                form.setDefaultModelObject(new WijzigWachtwoordPanel.WijzigWachtwoordModel());
                AbstractBasicPage page = (AbstractBasicPage)getPage();
                target.add(page);
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
               reset(target,form);
            }
        };
        terugBtn.setDefaultFormProcessing(false);
        contentPanel.getForm().add(terugBtn);
        contentContainer.add(contentPanel);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }
}
