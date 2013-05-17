package org.onlinetaskforce.web.frontend.panels.gebruikersbeheer;

import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.convert.IConverter;
import org.onlinetaskforce.business.services.GebruikerService;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.enumerations.Permission;
import org.onlinetaskforce.web.frontend.converters.ActiefConverter;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * DetailUserPageContentPanel
 * @author jordens
 * @since 8/03/13
 */
public class DetailUserPageContentPanel extends BasicPanel {
    private UserForm form;
    private Image picure;

    @SpringBean
    private GebruikerService gebruikerService;

    /**
     * Initializes the view
     *
     * @param id The id
     */
    public DetailUserPageContentPanel(String id) {
        this(id, new Model<Gebruiker>());
    }

    public DetailUserPageContentPanel(String s, IModel<Gebruiker> model) {
        super(s, model);
        form = new UserForm("user-form", model);
        add(form);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    public static final class UserForm extends Form {

        public UserForm(String id, IModel<Gebruiker> model) {
            super(id, model);

            setModel(new CompoundPropertyModel(model));
            if (model.getObject().getPicture() == null) {
                add(new Image("picture", new PackageResourceReference(this.getClass(), "../../images/no-img-available.jpg")));
            } else {
                ByteArrayResource byteArrayResource = new ByteArrayResource("image", model.getObject().getPicture());
                add(new Image("picture", byteArrayResource));
            }

            add(new RequiredTextField<String>("username"));
            add(new RequiredTextField<String>("Naam"));
            add(new RequiredTextField<String>("Voornaam"));
            add(new RequiredTextField<String>("Telefoon"));
            add(new RequiredTextField<String>("Email"));
            List<Boolean> actiefList = new ArrayList<Boolean>();
            actiefList.add(Boolean.TRUE);
            actiefList.add(Boolean.FALSE);
            DropDownChoice<Permission> brandstofChoices = new DropDownChoice<Permission>("Role", Arrays.asList(Permission.values()));
            brandstofChoices.setRequired(true);
            add(brandstofChoices);
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

    public UserForm getForm() {
        return form;
    }

    public void setForm(UserForm form) {
        this.form = form;
    }
}

