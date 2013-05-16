package org.onlinetaskforce.web.frontend.pages.wagenbeheer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptHeaderItem;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.WagenBeheerService;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.dto.ZoekWagenDto;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.common.exceptions.BusinessExceptionKeys;
import org.onlinetaskforce.common.exceptions.MultipleBusinessException;
import org.onlinetaskforce.persistence.utils.ThreadContextInfo;
import org.onlinetaskforce.web.frontend.authorisation.SecurePage;
import org.onlinetaskforce.web.frontend.pages.AbstractBasicPage;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.wagenbeheer.DetailWagenPageContentPanel;
import org.onlinetaskforce.web.frontend.window.AnnulatiesConfirmationWindow;

import java.util.Date;

public class DetailWagenPage extends BasicPage implements SecurePage {
	private static final long serialVersionUID = 1L;

    private DetailWagenPageContentPanel contentPanel;
    private AnnulatiesConfirmationWindow annulatiesConfirmationDialog;
    private  boolean komendeVanOverview;

    @SpringBean
    private WagenBeheerService wagenBeheerService;

    public DetailWagenPage(final PageParameters parameters) {
        this(new Model<Wagen>(), new Model<ZoekWagenDto>(new ZoekWagenDto()), false);
    }

    public DetailWagenPage(IModel<Wagen> model, final IModel<ZoekWagenDto> zoekWagenModel, boolean b) {
        super(null);
        if (model.getObject() == null) {
            Wagen newWagen = new Wagen();
            newWagen.setCreatieTijdstip(new Date());
            newWagen.setCreatieGebruikerId(ThreadContextInfo.getInstance().getCurrentGebruikerId());
            model.setObject(newWagen);
        }

        contentPanel = new DetailWagenPageContentPanel("content-panel", model);
        contentContainer.add(contentPanel);
        komendeVanOverview = b;

        annulatiesConfirmationDialog =  new AnnulatiesConfirmationWindow("annulatiesConfirmation");
        annulatiesConfirmationDialog.setOutputMarkupId(true);
        annulatiesConfirmationDialog.setOutputMarkupPlaceholderTag(true);

        final AjaxButton verzendBtn = new AjaxButton("verzend-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    target.appendJavaScript("Wicket.Window.unloadConfirmation=false;");
                    Wagen wagen = wagenBeheerService.addOrModify((Wagen)contentPanel.getForm().getDefaultModelObject(), null);
                    contentPanel.getForm().setDefaultModelObject(wagen);
                    success(new StringResourceModel("create.wagen.success", this, null).getString());
                    AbstractBasicPage page = (AbstractBasicPage)getPage();
                    target.add(page);
                } catch (BusinessException be) {
                    if (BusinessExceptionKeys.BE_KEY_SAVE_WAGEN_INVOLVES_CANCELLATIONS.equals(be.getMessageKey())) {
                        MultipleBusinessException mbe = new MultipleBusinessException();
                        mbe.addBusinessException(be);
                        annulatiesConfirmationDialog.setWagen((Wagen)contentPanel.getForm().getDefaultModelObject());
                        annulatiesConfirmationDialog.setMbe(mbe);
                        annulatiesConfirmationDialog.show(target);
                        target.appendJavaScript("fixWindowVertical();");
                    } else {
                        error(new StringResourceModel(be.getMessageKey(), this, null).getString());
                        AbstractBasicPage page = (AbstractBasicPage)getPage();
                        target.add(page);
                    }
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                AbstractBasicPage page = (AbstractBasicPage)getPage();
                target.add(page);
            }
        };
        verzendBtn.setOutputMarkupId(true);
        verzendBtn.setOutputMarkupPlaceholderTag(true);
        contentPanel.getForm().add(verzendBtn);
        contentContainer.add(annulatiesConfirmationDialog);

        final AjaxButton terugBtn = new AjaxButton("terug-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                setResponsePage(new OverviewWagenPage(zoekWagenModel));
            }
        };
        contentPanel.getForm().add(terugBtn);
        if (!komendeVanOverview) {
            terugBtn.setVisible(false);
        }
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptHeaderItem.forReference(new JavaScriptResourceReference(DetailWagenPage.class, "../../js/reposition-modal.js")));
    }
}
