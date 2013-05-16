package org.onlinetaskforce.web.frontend.panels.reservatiebeheer;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.onlinetaskforce.common.dto.ZoekReservatieDto;
import org.onlinetaskforce.common.enumerations.Permission;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;
import org.onlinetaskforce.web.frontend.security.Permissions;

/**
 * @author jordens
 * @since 23/04/13
 */
public class ZoekReservatiePanel extends BasicPanel{
    private IModel<ZoekReservatieDto> zoekReservatieDtoIModel;
    private ZoekReservatieForm form;

    public ZoekReservatiePanel(String id, IModel<ZoekReservatieDto> zoekReservatieModel) {
        super(id, zoekReservatieModel);
        zoekReservatieDtoIModel = zoekReservatieModel;
        form = new ZoekReservatieForm("zoek-reservatie-form", zoekReservatieDtoIModel);
        add(form);
    }

    public static final class ZoekReservatieForm extends Form {
         public ZoekReservatieForm(String id, IModel<ZoekReservatieDto> zoekReservatieDtoIModel) {
             super(id);
             setModel(new CompoundPropertyModel(zoekReservatieDtoIModel.getObject()));
             TextField<String> gebruikerNaamfield = new TextField<String>("Gebruikersnaam");
             Permissions.of(gebruikerNaamfield)
                     .enable(Permission.ADMIN);
             add(gebruikerNaamfield);
             add(new TextField<Long>("ReservatieNummer"));
             add(new TextField<String>("Nummerplaat"));
             add(new CheckBox("Overtijd"));
         }
     }

    public ZoekReservatieForm getForm() {
        return form;
    }

    public void setForm(ZoekReservatieForm form) {
        this.form = form;
    }
}
