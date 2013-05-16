package org.onlinetaskforce.web.frontend.panels;

import org.apache.wicket.Session;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.markup.html.AjaxLink;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.resource.ByteArrayResource;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.web.frontend.pages.HomeLoginPage;
import org.onlinetaskforce.web.frontend.session.OtfWebSession;

import java.util.Date;

/**
 * Reusable basic UserPanel
 * @author jordens
 * @since 8/03/13
 */
public class UserPanel extends BasicPanel {
    private String status;
    private Date today;
    private AjaxLink signOutLnk;
    private Image picure;

    /**
     * Initializes the view
     *
     * @param id The id
     */
    public UserPanel(String id) {
        super(id);

        final OtfWebSession session = (OtfWebSession) Session.get();
        add(new Label("status", Model.of("Welkom " + session.getGebruiker().getFullName())));
        today = new Date();
        add(new Label("dateTime", Model.of(today)));

        Gebruiker gebruiker = session.getGebruiker();
        if (gebruiker.getPicture() == null) {
            picure = new Image("picture", new PackageResourceReference(UserPanel.class, "../images/no-img-available.jpg"));
        } else {
            ByteArrayResource byteArrayResource = new ByteArrayResource("image", session.getGebruiker().getPicture());
            picure = new Image("picture", byteArrayResource);
        }
        add(picure);

        signOutLnk = new AjaxLink("sign-out-lnk") {

            @Override
            public void onClick(AjaxRequestTarget target) {
                session.setGebruiker(null);
                setResponsePage(HomeLoginPage.class);
            }
        };
        add(signOutLnk);
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

    public AjaxLink getSignOutLnk() {
        return signOutLnk;
    }

    public void setSignOutLnk(AjaxLink signOutLnk) {
        this.signOutLnk = signOutLnk;
    }

    public Image getPicure() {
        return picure;
    }

    public void setPicure(Image picure) {
        this.picure = picure;
    }
}

