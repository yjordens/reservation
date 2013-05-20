package org.onlinetaskforce.web.frontend.panels.reservatiebeheer;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.GebruikerService;
import org.onlinetaskforce.business.services.ReservatieBeheerService;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.domain.Reservatie;
import org.onlinetaskforce.common.domain.WagenOntvangst;
import org.onlinetaskforce.common.enumerations.TijdEnum;
import org.onlinetaskforce.web.frontend.components.datepicker.DatePickerField;
import org.onlinetaskforce.web.frontend.dropdown.EnumDropDownChoice;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;
import org.onlinetaskforce.web.frontend.validators.WagenOntvangstValidator;

/**
 * Reusable basic DetailReservatiePageContentPanel
 * @author jordens
 * @since 8/03/13
 */
public class OntvangstWagenPageContentPanel extends BasicPanel {
    private OntvangWagenForm form;
    private Gebruiker reserveerder;

    @SpringBean
    ReservatieBeheerService reservatieBeheerService;
    @SpringBean
    GebruikerService gebruikerService;


    public OntvangstWagenPageContentPanel(String s, Model<WagenOntvangst> wagenOntvangstModel, IModel<Reservatie> reservatieModel) {
        super(s, wagenOntvangstModel);
        reserveerder = gebruikerService.getGebruikerById(reservatieModel.getObject().getCreatieGebruikerId());
        wagenOntvangstModel.getObject().setReserveerder(reserveerder);
        wagenOntvangstModel.getObject().setKilometerStand(reservatieModel.getObject().getWagen().getKilometerStand());
        form = new OntvangWagenForm("ontvang-wagen-form", wagenOntvangstModel, reservatieModel);
        add(form);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    public static final class OntvangWagenForm extends Form {

        public OntvangWagenForm(String id, IModel<WagenOntvangst> model, IModel<Reservatie> reservatieModel) {
            super(id, model);

            setModel(new CompoundPropertyModel(model));
            add(new Label("reservatie.reservatieNummer", reservatieModel.getObject().getReservatieNummer()));
            add(new Label("wagen.nummerplaat", reservatieModel.getObject().getWagen().getNummerplaat()));
            add(new Label("reserveerder.FullName"));
            //DateTextField ontvangstTijdstip = new DateTextField("OntvangstTijdstip", new PatternDateConverter("dd/MM/yyyy", true));
            DatePickerField dpf = new DatePickerField("OntvangstTijdstip");
            dpf.setRequired(true);
            add(dpf);

            add(new RequiredTextField<Long>("KilometerStand"));
            DropDownChoice<TijdEnum> tijden = new EnumDropDownChoice<TijdEnum>("ontvangsttijd", TijdEnum.class);
            tijden.setRequired(true);
            add(tijden);
            add(new TextArea<String>("Opmerking"));

            FormComponent<?>[] components = new FormComponent<?>[2];
            components[0] = dpf;
            components[1] = tijden;
            add(new WagenOntvangstValidator(components));
        }
    }

    public OntvangWagenForm getForm() {
        return form;
    }

    public void setForm(OntvangWagenForm form) {
        this.form = form;
    }
}

