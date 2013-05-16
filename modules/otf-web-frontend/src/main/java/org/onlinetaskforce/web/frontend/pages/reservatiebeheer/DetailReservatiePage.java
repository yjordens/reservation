package org.onlinetaskforce.web.frontend.pages.reservatiebeheer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.ReservatieBeheerService;
import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.dto.ZoekReservatieDto;
import org.onlinetaskforce.common.enumerations.Permission;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.web.frontend.authorisation.SecurePage;
import org.onlinetaskforce.web.frontend.pages.AbstractBasicPage;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.reservatiebeheer.DetailReservatiePageContentPanel;
import org.onlinetaskforce.web.frontend.security.Permissions;

import java.util.Date;

public class DetailReservatiePage extends BasicPage implements SecurePage {
	private static final long serialVersionUID = 1L;

    private DetailReservatiePageContentPanel contentPanel;
    private  boolean komendeVanOverview;
    @SpringBean
    private ReservatieBeheerService reservatieBeheerService;

    public DetailReservatiePage(final PageParameters parameters) {
        this(new Model<Reservatie>(), new Model<ZoekReservatieDto>(new ZoekReservatieDto()), false);
    }

    public DetailReservatiePage(final IModel<Reservatie> model, final IModel<ZoekReservatieDto> zoekReservatieModel, boolean b) {
        super(null);

        contentPanel = new DetailReservatiePageContentPanel("content-panel", model);
        contentContainer.add(contentPanel);

        final AjaxButton verzendBtn = new AjaxButton("verzend-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    reservatieBeheerService.annuleerReservatie(model.getObject());
                    setResponsePage(new OverviewReservatiePage(new StringResourceModel("annulatie.reservatie.success", this, null).getString()));
                } catch (BusinessException e) {
                    setResponsePage(new OverviewReservatiePage(new StringResourceModel("annulatie.reservatie.bevestiging.email.failure", this, null).getString()));
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                AbstractBasicPage page = (AbstractBasicPage)getPage();
                target.add(page);
            }

            @Override
            protected void onConfigure() {
                super.onConfigure();
                if (model.getObject().getBeginDatum().before(new Date())) {
                    setVisible(false);
                }
            }
        };
        contentPanel.getForm().add(verzendBtn);

        final AjaxButton terugBtn = new AjaxButton("terug-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                setResponsePage(new OverviewReservatiePage(zoekReservatieModel));
            }
        };
        contentPanel.getForm().add(terugBtn);
        if (!komendeVanOverview) {
            terugBtn.setVisible(true);
        }

        final AjaxButton ontvangBtn = new AjaxButton("ontvang-wagen-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                setResponsePage(new OntvangstWagenPage(model));
            }
        };
        Permissions.of(ontvangBtn)
                .render(Permission.ADMIN)
                .enable(Permission.ADMIN);

        contentPanel.getForm().add(ontvangBtn);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }
}
