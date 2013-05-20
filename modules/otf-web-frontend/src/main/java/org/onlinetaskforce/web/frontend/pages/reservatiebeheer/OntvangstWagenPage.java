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
import org.onlinetaskforce.common.domain.WagenOntvangst;
import org.onlinetaskforce.web.frontend.pages.AbstractBasicPage;
import org.onlinetaskforce.web.frontend.pages.BasicPage;
import org.onlinetaskforce.web.frontend.panels.reservatiebeheer.OntvangstWagenPageContentPanel;

/**
 * @author jordens
 * @since 29/04/13
 */
public class OntvangstWagenPage extends BasicPage{
    private OntvangstWagenPageContentPanel contentPanel;

    @SpringBean
    private ReservatieBeheerService reservatieBeheerService;

    public OntvangstWagenPage(final PageParameters parameters) {
        super(parameters);
    }

    public OntvangstWagenPage(final IModel<Reservatie> model) {
        super(null);
        final Model<WagenOntvangst> wagenOntvangstModel = determineWagenOntvangst(model.getObject());
        contentPanel = new OntvangstWagenPageContentPanel("content-panel", wagenOntvangstModel, model);
        contentContainer.add(contentPanel);

        final AjaxButton verzendBtn = new AjaxButton("verzend-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                wagenOntvangstModel.getObject().defineOntvangstTijdstip();
                model.getObject().getWagen().setKilometerStand(wagenOntvangstModel.getObject().getKilometerStand());
                reservatieBeheerService.ontvangWagen(wagenOntvangstModel.getObject(), model.getObject());
                setResponsePage(new OverviewReservatiePage(new StringResourceModel("ontvang.wagen.success", this, null).getString()));
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
                //todo enkel voor ADMIN
            }
        };
        contentPanel.getForm().add(verzendBtn);

        final AjaxButton terugBtn = new AjaxButton("terug-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                setResponsePage(new OverviewReservatiePage());
            }
        };
        terugBtn.setDefaultFormProcessing(false);
        contentPanel.getForm().add(terugBtn);
    }

    private Model<WagenOntvangst> determineWagenOntvangst(Reservatie reservatie) {
        WagenOntvangst wagenOntvangst = reservatie.getWagenOntvangst();
        if (wagenOntvangst == null) {
            wagenOntvangst = new WagenOntvangst();
        } else {
            wagenOntvangst.determineTijden();
        }

        return new Model<WagenOntvangst>(wagenOntvangst);
    }
}
