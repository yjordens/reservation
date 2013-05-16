package org.onlinetaskforce.web.frontend.pages.wagenbeheer;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.WagenBeheerService;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.dto.ZoekWagenDto;
import org.onlinetaskforce.web.frontend.authorisation.SecurePage;
import org.onlinetaskforce.web.frontend.models.LazyListDataProvider;
import org.onlinetaskforce.web.frontend.pages.AbstractBasicPage;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.wagenbeheer.OverviewWagenPageContentPanel;
import org.onlinetaskforce.web.frontend.panels.wagenbeheer.WagenListPanel;
import org.onlinetaskforce.web.frontend.panels.wagenbeheer.ZoekWagenPanel;

import java.util.Collections;
import java.util.List;

public class OverviewWagenPage extends BasicPage implements SecurePage {
	private static final long serialVersionUID = 1L;

    private IModel<ZoekWagenDto> zoekWagenModel;

    private OverviewWagenPageContentPanel contentPanel;

    @SpringBean
    private WagenBeheerService wagenBeheerService;

	public OverviewWagenPage(final PageParameters parameters) {
		super(parameters);
        zoekWagenModel = new Model<ZoekWagenDto>(new ZoekWagenDto());
    }

	public OverviewWagenPage(IModel<ZoekWagenDto> zoekWagenModel) {
        super(null);
        this.zoekWagenModel = zoekWagenModel;
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        contentPanel = new OverviewWagenPageContentPanel("content-panel");

        final ZoekWagenPanel critPanel = new ZoekWagenPanel("zoek-wagen", zoekWagenModel);
        critPanel.setOutputMarkupId(true);
        contentPanel.add(critPanel);

        final WagenListPanel wagenListPanel = new WagenListPanel("wagen-list", new LazyListDataProvider<Wagen>() {
            @Override
            protected List<Wagen> getData() {
                return getListData();
            }
        }, zoekWagenModel) {
            @Override
            protected void onInitialize() {
                if (zoekWagenModel.getObject() == null) {
                    setVisible(false);
                    zoekWagenModel.setObject(new ZoekWagenDto());
                }
                super.onInitialize();
            }
        };
        wagenListPanel.setOutputMarkupId(true);
        wagenListPanel.setOutputMarkupPlaceholderTag(true);
        contentPanel.add(wagenListPanel);


        AjaxButton zoekBtn = new AjaxButton("zoek-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                wagenListPanel.setVisible(true);
                target.add(wagenListPanel);
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
                zoekWagenModel.setObject(new ZoekWagenDto());
                form.clearInput();
                form.setDefaultModelObject(zoekWagenModel.getObject());
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

    private List<Wagen> getListData() {
        List<Wagen> wagens = wagenBeheerService.overview(zoekWagenModel.getObject());
        if (wagens == null) {
            return Collections.emptyList();
        }
        return wagens;
    }
}
