package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.StringResourceModel;
import org.onlinetaskforce.web.frontend.pages.AbstractBasicPage;
import org.onlinetaskforce.web.frontend.pages.HomePage;
import org.onlinetaskforce.web.frontend.session.OtfWebSession;

import java.util.Date;

/**
 * Reusable basic LoginPanel
 * @author jordens
 * @since 8/03/13
 */
public class LoginPanel extends BasicPanel {
    private String status;
    private Date today;
    private LoginForm loginForm;
    AjaxButton loginBtn;

    /**
     * Initializes the view
     *
     * @param id The id
     */
    public LoginPanel(String id) {
        super(id);

        add(new Label("status", Model.of("Je bent nog niet aangemeld!")));
        today = new Date();
        add(new Label("dateTime", Model.of(today)));
        loginForm = new LoginForm("loginForm");
        loginForm.setOutputMarkupId(true);
        add(loginForm);

        loginBtn = new AjaxButton("login-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                super.onSubmit(target, form);
                OtfWebSession session = (OtfWebSession) Session.get();

                if(session.login(loginForm.getGebruikersnaam(), loginForm.getWachtwoord())) {
                    if (session.getGebruiker().getActief()) {
                        AbstractBasicPage page = (AbstractBasicPage)getPage();
                        target.add(page);
                        setResponsePage(HomePage.class);
                    } else {
                        AbstractBasicPage page = (AbstractBasicPage)getPage();
                        page.error(new StringResourceModel("login.denied", this, null).getString());
                        target.add(page);
                    }
                } else {
                    AbstractBasicPage page = (AbstractBasicPage)getPage();
                    page.error(new StringResourceModel("login.failed", this, null).getString());
                    target.add(page);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                AbstractBasicPage page = (AbstractBasicPage)getPage();
                target.add(page);
            }
        };
        loginForm.add(loginBtn);
    }

    /**
     * Login components extending the Wicket Form type
     */
    public static final class LoginForm extends Form {

        private String gebruikersnaam;
        private String wachtwoord;

        /**
         * Initializes the view
         *
         * @param id The id
         */
        public LoginForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel(this));
            add(new RequiredTextField("Gebruikersnaam").setRequired(true));
            add(new PasswordTextField("Wachtwoord").setRequired(true));
        }

        public String getGebruikersnaam() {
            return gebruikersnaam;
        }

        public void setGebruikersnaam(String gebruikersnaam) {
            this.gebruikersnaam = gebruikersnaam;
        }

        public String getWachtwoord() {
            return wachtwoord;
        }

        public void setWachtwoord(String wachtwoord) {
            this.wachtwoord = wachtwoord;
        }
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getToday() {
        return today;
    }

    public void setToday(Date today) {
        this.today = today;
    }

    public LoginForm getLoginForm() {
        return loginForm;
    }

    public void setLoginForm(LoginForm loginForm) {
        this.loginForm = loginForm;
    }
}

