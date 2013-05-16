package org.onlinetaskforce.web.frontend.panels.contact;

import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.model.Model;
import org.onlinetaskforce.common.dto.ContactFormDto;
import org.onlinetaskforce.web.frontend.panels.BasicPanel;

/**
 * Reusable basic ContactFormPageContentPanel
 * @author jordens
 * @since 8/03/13
 */
public class ContactFormConfirmationPageContentPanel extends BasicPanel {
    /**
     * Initializes the view
     *
     * @param id The id
     */
    public ContactFormConfirmationPageContentPanel(String id, ContactFormDto contactFormDto) {
        super(id);
        add(new Label("klant", Model.of(contactFormDto.getVoornaam() + " " + contactFormDto.getNaam())));
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }
}

