package org.onlinetaskforce.web.frontend.panels.reservatiebeheer;

import org.apache.wicket.datetime.PatternDateConverter;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.convert.IConverter;
import org.onlinetaskforce.business.services.GebruikerService;
import org.onlinetaskforce.business.services.ReservatieBeheerService;
import org.onlinetaskforce.business.services.WagenBeheerService;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.domain.WagenOntvangst;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;

import java.text.SimpleDateFormat;

/**
 * Reusable basic DetailReservatiePageContentPanel
 * @author jordens
 * @since 8/03/13
 */
public class DetailReservatiePageContentPanel extends BasicPanel {
    private DetailWagenForm form;
    private IModel<Reservatie> reservatieModel;

    @SpringBean
    private ReservatieBeheerService reservatieBeheerService;
    @SpringBean
    private GebruikerService gebruikerService;
    @SpringBean
    private WagenBeheerService wagenBeheerService;

    /**
     * Initializes the view
     *
     * @param id The id
     */
    public DetailReservatiePageContentPanel(String id) {
        this(id, new Model<Reservatie>());
    }

    public DetailReservatiePageContentPanel(String s, IModel<Reservatie> model) {
        super(s, model);
        reservatieModel = model;
        Gebruiker annuleerder = null;
        if (model.getObject().isAnnulatie()) {
            annuleerder = gebruikerService.getGebruikerById((model.getObject()).getAnnulatieGebruikerId());
        }
        Gebruiker ontvanger = null;
        WagenOntvangst wo = model.getObject().getWagenOntvangst();
        if (wo != null) {
            ontvanger = gebruikerService.getGebruikerById(wo.getCreatieGebruikerId());
        }
        form = new DetailWagenForm("detail-reservatie-form", model, annuleerder, wo, ontvanger);
        add(form);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    public static final class DetailWagenForm extends Form {

        public DetailWagenForm(String id, IModel<Reservatie> model, Gebruiker annuleerder, WagenOntvangst wo, Gebruiker ontvanger) {
            super(id, model);

            setModel(new CompoundPropertyModel(model));
            add(new Label("ReservatieNummer"));
            add(new Label("Doel"));
            add(new Label("BeginDatum") {
                @Override
                public <C> IConverter<C> getConverter(Class<C> type) {
                    return (IConverter<C>) new PatternDateConverter("dd/MM/yyyy HH:mm", false);
                }
            });
            add(new Label("EindDatum") {
                @Override
                public <C> IConverter<C> getConverter(Class<C> type) {
                    return (IConverter<C>) new PatternDateConverter("dd/MM/yyyy HH:mm", false);
                }
            });
            add(new Label("wagen.Nummerplaat"));
            add(new Label("wagen.Merk"));
            add(new Label("wagen.Merktype"));
            add(new Label("wagen.Brandstof"));

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            StringBuilder builder = new StringBuilder("Geannuleerd op: ");
            if (annuleerder != null) {
                builder.append(sdf.format(((Reservatie)getDefaultModelObject()).getAnnulatieTijdstip()));
                builder.append(" door ").append(annuleerder.getFullName());
            }
            Label annulatieLbl = new Label("annulatie", new Model<String>(builder.toString()));
//            if (((Reservatie)getDefaultModelObject()).isAnnulatie()) {
//                annulatieLbl.setVisible(true);
//            } else {
//                annulatieLbl.setVisible(false);
//            }
            add(annulatieLbl);

            StringBuilder ontvangstStr = new StringBuilder("Ontvangen op: ");
            if (wo != null) {
                ontvangstStr.append(sdf.format(wo.getOntvangstTijdstip()));
                ontvangstStr.append(" door ").append(ontvanger.getFullName());
            }
            Label ontvangenLbl = new Label("ontvangst", new Model<String>(ontvangstStr.toString()));
//            if (wo != null) {
//                ontvangenLbl.setVisible(true);
//            } else {
//                ontvangenLbl.setVisible(false);
//            }
            add(ontvangenLbl);

        }
    }

    public DetailWagenForm getForm() {
        return form;
    }

    public void setForm(DetailWagenForm form) {
        this.form = form;
    }
}

