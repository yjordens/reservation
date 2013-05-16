package org.onlinetaskforce.web.frontend.panels.contact;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.form.AjaxButton;
import org.apache.wicket.markup.html.form.EmailTextField;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.MailService;
import org.onlinetaskforce.common.dto.ContactFormDto;
import org.onlinetaskforce.common.exceptions.BusinessException;
import org.onlinetaskforce.web.frontend.pages.AbstractBasicPage;
import org.onlinetaskforce.web.frontend.pages.contact.ContactFormConfirmationPage;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;

/**
 * Reusable basic ContactFormPageContentPanel
 * @author jordens
 * @since 8/03/13
 */
public class ContactFormPageContentPanel extends BasicPanel {
    private ContactForm contactForm;
    private AjaxButton verzendBtn;
    @SpringBean
    private MailService mailService;

    /**
     * Initializes the view
     *
     * @param id The id
     */
    public ContactFormPageContentPanel(String id) {
        super(id);
        contactForm = new ContactForm("contactForm");
        add(contactForm);
        verzendBtn = new AjaxButton("verzend-btn") {
            @Override
            protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
                try {
                    mailService.sendMail(contactForm.getContactFormDto());
                    setResponsePage(new ContactFormConfirmationPage(contactForm.getContactFormDto()));
                } catch (BusinessException e) {
                    error(new StringResourceModel(e.getMessageKey(), this, null).getString());
                    AbstractBasicPage page = (AbstractBasicPage)getPage();
                    target.add(page);
                }
            }

            @Override
            protected void onError(AjaxRequestTarget target, Form<?> form) {
                super.onError(target, form);
                AbstractBasicPage page = (AbstractBasicPage)getPage();
                target.add(page);
            }
        };
        contactForm.add(verzendBtn);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    public static final class ContactForm extends Form {
        private ContactFormDto contactFormDto = new ContactFormDto();

        public ContactForm(String id) {
            super(id);
            setModel(new CompoundPropertyModel(contactFormDto));
            add(new RequiredTextField<String>("Naam"));
            add(new RequiredTextField<String>("Voornaam"));
            add(new RequiredTextField<String>("Straat"));
            add(new RequiredTextField<String>("Huisnummer"));
            add(new TextField<String>("Postbus"));
            add(new RequiredTextField<String>("Gemeente"));
            add(new RequiredTextField<String>("Postcode"));
            add(new TextField<String>("Telefoon"));
            add(new TextField<String>("Gsm"));
            add(new TextField<String>("Fax"));
            add(new EmailTextField("Email"));
        }

        public ContactForm(String id, IModel iModel) {
            super(id, iModel);
        }

        public ContactFormDto getContactFormDto() {
            return contactFormDto;
        }

        public void setContactFormDto(ContactFormDto contactFormDto) {
            this.contactFormDto = contactFormDto;
        }
    }


    public Form getContactForm() {
        return contactForm;
    }

    public void setContactForm(ContactForm contactForm) {
        this.contactForm = contactForm;
    }

    public AjaxButton getVerzendBtn() {
        return verzendBtn;
    }

    public void setVerzendBtn(AjaxButton verzendBtn) {
        this.verzendBtn = verzendBtn;
    }
}

