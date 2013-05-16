package org.onlinetaskforce.web.frontend.panels.wagenbeheer;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.util.convert.IConverter;
import org.onlinetaskforce.common.domain.Wagen;
import org.onlinetaskforce.common.enumerations.BrandstofEnum;
import org.onlinetaskforce.web.frontend.converters.ActiefConverter;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Reusable basic ContactFormPageContentPanel
 * @author jordens
 * @since 8/03/13
 */
public class DetailWagenPageContentPanel extends BasicPanel {
    private WagenForm form;

    /**
     * Initializes the view
     *
     * @param id The id
     */
    public DetailWagenPageContentPanel(String id) {
        this(id, new Model<Wagen>());
    }

    public DetailWagenPageContentPanel(String s, IModel<Wagen> model) {
        super(s, model);
        form = new WagenForm("wagen-form", model);
        add(form);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    public static final class WagenForm extends Form {

        public WagenForm(String id, IModel<Wagen> model) {
            super(id, model);

            setModel(new CompoundPropertyModel(model));
            add(new RequiredTextField<String>("Nummerplaat"));
            add(new RequiredTextField<String>("Merk"));
            add(new RequiredTextField<String>("Merktype"));
            DropDownChoice<BrandstofEnum> brandstofChoices = new DropDownChoice<BrandstofEnum>("Brandstof", Arrays.asList(BrandstofEnum.values()));
            brandstofChoices.setRequired(true);
            add(brandstofChoices);
            List<Boolean> actiefList = new ArrayList<Boolean>();
            actiefList.add(Boolean.TRUE);
            actiefList.add(Boolean.FALSE);
            DropDownChoice<Boolean> actiefChoices = new DropDownChoice<Boolean>("Actief", actiefList) {
                @Override
                public <C> IConverter<C> getConverter(Class<C> type) {
                    return new ActiefConverter();
                }
            };
            actiefChoices.setRequired(true);
            add(actiefChoices);
        }
    }

    public WagenForm getForm() {
        return form;
    }

    public void setForm(WagenForm form) {
        this.form = form;
    }
}

