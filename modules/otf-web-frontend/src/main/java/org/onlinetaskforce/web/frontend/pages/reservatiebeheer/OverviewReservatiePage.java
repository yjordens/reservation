package org.onlinetaskforce.web.frontend.pages.reservatiebeheer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.ReservatieBeheerService;
import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.dto.ZoekReservatieDto;
import org.onlinetaskforce.web.frontend.authorisation.SecurePage;
import org.onlinetaskforce.web.frontend.models.LazyListDataProvider;
import org.onlinetaskforce.web.frontend.pages.AbstractBasicPage;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.reservatiebeheer.OverviewReservatiePageContentPanel;
import org.onlinetaskforce.web.frontend.panels.reservatiebeheer.ReservatieListPanel;
import org.onlinetaskforce.web.frontend.panels.reservatiebeheer.ZoekReservatiePanel;
import org.onlinetaskforce.web.frontend.session.OtfWebSession;

import java.util.Collections;
import java.util.List;

public class OverviewReservatiePage extends BasicPage implements SecurePage {
	private static final long serialVersionUID = 1L;

    private IModel<ZoekReservatieDto> zoekReservatieModel;

    private OverviewReservatiePageContentPanel contentPanel;

    @SpringBean
    private ReservatieBeheerService reservatieBeheerService;

	public OverviewReservatiePage(final PageParameters parameters) {
		super(parameters);
        zoekReservatieModel = new Model<ZoekReservatieDto>(new ZoekReservatieDto());
    }

	public OverviewReservatiePage() {
		super(null);
        zoekReservatieModel = new Model<ZoekReservatieDto>(new ZoekReservatieDto());
    }

	public OverviewReservatiePage(String message) {
        super(null);
        success(message);
        zoekReservatieModel = new Model<ZoekReservatieDto>(new ZoekReservatieDto());
    }

	public OverviewReservatiePage(IModel<ZoekReservatieDto> zoekReservatieModel) {
        super(null);
        this.zoekReservatieModel = zoekReservatieModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new OverviewReservatiePageContentPanel("content-panel");

        OtfWebSession session = (OtfWebSession)getSession();
        zoekReservatieModel.getObject().setGebruikersnaam(session.getGebruiker().getUsername());

        final ZoekReservatiePanel critPanel = new ZoekReservatiePanel("zoek-reservatie", zoekReservatieModel);
        critPanel.setOutputMarkupId(true);
        contentPanel.add(critPanel);

        final ReservatieListPanel reservatieListPanel = new ReservatieListPanel("reservatie-list", new LazyListDataProvider<Reservatie>() {
            @Override
            protected List<Reservatie> getData() {
                return getListData();
            }
        }, zoekReservatieModel) {
            @Override
            protected void onInitialize() {
                if (zoekReservatieModel.getObject() == null) {
                    setVisible(false);
                    zoekReservatieModel.setObject(new ZoekReservatieDto());
                }
                super.onInitialize();
            }
        };
        reservatieListPanel.setOutputMarkupId(true);
        reservatieListPanel.setOutputMarkupPlaceholderTag(true);
        contentPanel.add(reservatieListPanel);


        AjaxButton zoekBtn = new AjaxButton("zoek-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                reservatieListPanel.setVisible(true);
                target.add(reservatieListPanel);
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
                zoekReservatieModel.setObject(new ZoekReservatieDto());
                form.clearInput();
                form.setDefaultModelObject(zoekReservatieModel.getObject());
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

    private List<Reservatie> getListData() {
        List<Reservatie> reservaties = reservatieBeheerService.overview(zoekReservatieModel.getObject());
        if (reservaties == null) {
            return Collections.emptyList();
        }
        return reservaties;
    }
}
