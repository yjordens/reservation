package org.onlinetaskforce.web.frontend.panels.reservatiebeheer;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.GebruikerService;
import org.onlinetaskforce.business.services.ReservatieBeheerService;
import org.onlinetaskforce.common.domain.Gebruiker;
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

    /**
     * Initializes the view
     *
     * @param id The id
     */
    public OntvangstWagenPageContentPanel(String id) {
        this(id, new Model<WagenOntvangst>(new WagenOntvangst()));
    }

    public OntvangstWagenPageContentPanel(String s, Model<WagenOntvangst> wagenOntvangstModel) {
        super(s, wagenOntvangstModel);
        reserveerder = gebruikerService.getGebruikerById(wagenOntvangstModel.getObject().getReservatie().getCreatieGebruikerId());
        wagenOntvangstModel.getObject().setReserveerder(reserveerder);
        form = new OntvangWagenForm("ontvang-wagen-form", wagenOntvangstModel);
        add(form);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    public static final class OntvangWagenForm extends Form {

        public OntvangWagenForm(String id, IModel<WagenOntvangst> model) {
            super(id, model);

            setModel(new CompoundPropertyModel(model));
            add(new Label("reservatie.reservatieNummer"));
            add(new Label("wagen.nummerplaat"));
            add(new Label("reserveerder.FullName"));
            //DateTextField ontvangstTijdstip = new DateTextField("OntvangstTijdstip", new PatternDateConverter("dd/MM/yyyy", true));
            DatePickerField dpf = new DatePickerField("OntvangstTijdstip");
            dpf.setRequired(true);
            add(dpf);

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

