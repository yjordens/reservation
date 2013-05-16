package org.onlinetaskforce.web.frontend.panels.gebruikersbeheer;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.validation.validator.StringValidator;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;
import org.onlinetaskforce.web.frontend.session.OtfWebSession;

import java.io.Serializable;

/**
 * @author jordens
 * @since 28/04/13
 */
public class WijzigWachtwoordPanel extends BasicPanel {
    private WachtwoordForm form;
    private WijzigWachtwoordModel wijzigWachtwoordModel;

    /**
     * Default constructor
     *
     * @param id The component id
     */
    public WijzigWachtwoordPanel(String id) {
        super(id, new Model<WijzigWachtwoordModel>(new WijzigWachtwoordModel()));
        wijzigWachtwoordModel = new WijzigWachtwoordModel();
        form = new WachtwoordForm("ww-form", wijzigWachtwoordModel);
        add(form);
    }

    public static final class WachtwoordForm extends Form {

        public WachtwoordForm(String id, WijzigWachtwoordModel model) {
            super(id);
            setModel(new CompoundPropertyModel(model));
            add(new Label("Gebruiker"));
            add(new PasswordTextField("Wachtwoord"));
            PasswordTextField newWw = new PasswordTextField("NieuwWachtwoord");
            newWw.add(StringValidator.lengthBetween(6,20));
            add(newWw);
            PasswordTextField newConfirmWw = new PasswordTextField("NieuwWachtwoordBevestiging");
            newConfirmWw.add(StringValidator.lengthBetween(6,20));
            add(newConfirmWw);
            add(new EqualPasswordInputValidator(newWw, newConfirmWw));
        }
    }

    /**
     * Model that support data for the containing Edit Panel
     */
    public static class WijzigWachtwoordModel implements Serializable {
        public WijzigWachtwoordModel() {
            this.gebruiker = OtfWebSession.get().getGebruiker().getUsername();
        }

        private String gebruiker;
        private String wachtwoord;
        private String nieuwWachtwoord;
        private String nieuwWachtwoordBevestiging;

        public void reset() {
        }

        public String getGebruiker() {
            return gebruiker;
        }

        public void setGebruiker(String gebruiker) {
            this.gebruiker = gebruiker;
        }

        public String getWachtwoord() {
            return wachtwoord;
        }

        public void setWachtwoord(String wachtwoord) {
            this.wachtwoord = wachtwoord;
        }

        public String getNieuwWachtwoord() {
            return nieuwWachtwoord;
        }

        public void setNieuwWachtwoord(String nieuwWachtwoord) {
            this.nieuwWachtwoord = nieuwWachtwoord;
        }

        public String getNieuwWachtwoordBevestiging() {
            return nieuwWachtwoordBevestiging;
        }

        public void setNieuwWachtwoordBevestiging(String nieuwWachtwoordBevestiging) {
            this.nieuwWachtwoordBevestiging = nieuwWachtwoordBevestiging;
        }
    }

    public WachtwoordForm getForm() {
        return form;
    }

    public void setForm(WachtwoordForm form) {
        this.form = form;
    }

    public WijzigWachtwoordModel getWijzigWachtwoordModel() {
        return wijzigWachtwoordModel;
    }

    public void setWijzigWachtwoordModel(WijzigWachtwoordModel wijzigWachtwoordModel) {
        this.wijzigWachtwoordModel = wijzigWachtwoordModel;
    }
}
