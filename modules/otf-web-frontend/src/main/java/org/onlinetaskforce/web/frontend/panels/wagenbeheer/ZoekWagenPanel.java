package org.onlinetaskforce.web.frontend.panels.wagenbeheer;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.util.convert.IConverter;
import org.onlinetaskforce.common.dto.ZoekWagenDto;
import org.onlinetaskforce.web.frontend.converters.ActiefConverter;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jordens
 * @since 23/04/13
 */
public class ZoekWagenPanel extends BasicPanel{
    private IModel<ZoekWagenDto> zoekWagenDtoIModel;
    private ZoekWagenForm form;

    public ZoekWagenPanel(String id, IModel<ZoekWagenDto> zoekWagenModel) {
        super(id, zoekWagenModel);
        zoekWagenDtoIModel = zoekWagenModel;
        form = new ZoekWagenForm("zoek-wagen-form", zoekWagenDtoIModel);
        add(form);
    }

    public static final class ZoekWagenForm extends Form {
         public ZoekWagenForm(String id, IModel<ZoekWagenDto> zoekWagenDtoIModel) {
             super(id);
             setModel(new CompoundPropertyModel(zoekWagenDtoIModel.getObject()));
             add(new TextField<String>("Nummerplaat"));
             add(new TextField<String>("Merk"));
             add(new TextField<String>("Merktype"));
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

    public ZoekWagenForm getForm() {
        return form;
    }

    public void setForm(ZoekWagenForm form) {
        this.form = form;
    }
}
