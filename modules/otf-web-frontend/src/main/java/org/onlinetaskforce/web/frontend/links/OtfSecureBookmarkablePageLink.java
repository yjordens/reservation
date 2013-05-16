package org.onlinetaskforce.web.frontend.links;

import org.apache.wicket.Page;
import org.apache.wicket.Session;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.PackageResourceReference;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.web.frontend.panels.feedback.OtfPageFeedbackPanel;
import org.onlinetaskforce.web.frontend.session.OtfWebSession;
import org.wicketstuff.security.components.markup.html.links.SecureBookmarkablePageLink;

/**
 * @author jordens
 * @since 20/03/13
 */
public class OtfSecureBookmarkablePageLink<T> extends SecureBookmarkablePageLink<T> {
    private Image icon;
    private PackageResourceReference closedLockResource;
    private PackageResourceReference openLockResource;

    public OtfSecureBookmarkablePageLink(String id, Class<? extends Page> pageClass) {
        super(id, pageClass);
        closedLockResource = new PackageResourceReference(OtfPageFeedbackPanel.class, "../../images/lock-closed.png");
        openLockResource = new PackageResourceReference(OtfPageFeedbackPanel.class, "../../images/lock-open.png");
        icon = new Image("secure-icon", closedLockResource);

        add(icon);
    }

    public OtfSecureBookmarkablePageLink(String id, Class<? extends Page> pageClass, PageParameters parameters) {
        super(id, pageClass, parameters);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();

        OtfWebSession session = (OtfWebSession) Session.get();
        Gebruiker gebruiker = session.getGebruiker();
        if (gebruiker != null) {
            icon.setImageResourceReference(openLockResource);
        } else {
            icon.setImageResourceReference(closedLockResource);
        }
    }

    public Image getIcon() {
        return icon;
    }

    public void setIcon(Image icon) {
        this.icon = icon;
    }
}
