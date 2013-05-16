package org.onlinetaskforce.web.frontend.panels.gebruikersbeheer;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.onlinetaskforce.common.dto.ZoekGebruikerDto;
import org.onlinetaskforce.web.frontend.converters.ActiefConverter;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jordens
 * @since 23/04/13
 */
public class ZoekUserPanel extends BasicPanel{
    private IModel<ZoekGebruikerDto> zoekGebruikerDtoIModel;
    private ZoekGebruikerForm form;

    public ZoekUserPanel(String id, IModel<ZoekGebruikerDto> zoekGebruikerModel) {
        super(id, zoekGebruikerModel);
        zoekGebruikerDtoIModel = zoekGebruikerModel;
        form = new ZoekGebruikerForm("zoek-user-form", zoekGebruikerDtoIModel);
        add(form);
    }

    public static final class ZoekGebruikerForm extends Form {
         public ZoekGebruikerForm(String id, IModel<ZoekGebruikerDto> zoekWagenDtoIModel) {
             super(id);
             setModel(new CompoundPropertyModel(zoekWagenDtoIModel.getObject()));
             add(new TextField<String>("Gebruikersnaam"));
             add(new TextField<String>("Naam"));
             add(new TextField<String>("Voornaam"));
             List<Boolean> actiefList = new ArrayList<Boolean>();
             actiefList.add(Boolean.TRUE);
             actiefList.add(Boolean.FALSE);
             DropDownChoice<Boolean> actiefChoices = new DropDownChoice<Boolean>("Actief", actiefList) {
                 @Override
                 public <C> IConverter<C> getConverter(Class<C> type) {
                     return new ActiefConverter();
                 }
             };
             add(actiefChoices);
         }
     }

    public ZoekGebruikerForm getForm() {
        return form;
    }

    public void setForm(ZoekGebruikerForm form) {
        this.form = form;
    }
}
