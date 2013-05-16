package org.onlinetaskforce.web.frontend.components.menu;

import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.onlinetaskforce.common.enumerations.Permission;
import org.onlinetaskforce.web.frontend.links.OtfSecureBookmarkablePageLink;
import org.onlinetaskforce.web.frontend.pages.HomePage;
import org.onlinetaskforce.web.frontend.pages.gebruikersbeheer.DetailUserPage;
import org.onlinetaskforce.web.frontend.pages.gebruikersbeheer.OverviewUserPage;
import org.onlinetaskforce.web.frontend.pages.gebruikersbeheer.PictureUserPage;
import org.onlinetaskforce.web.frontend.pages.gebruikersbeheer.WachtwoordPage;
import org.onlinetaskforce.web.frontend.pages.reservatiebeheer.DetailReservatiePage;
import org.onlinetaskforce.web.frontend.pages.reservatiebeheer.OverviewReservatiePage;
import org.onlinetaskforce.web.frontend.pages.reservatiebeheer.ReservatieWizardPage;
import org.onlinetaskforce.web.frontend.pages.wagenbeheer.DetailWagenPage;
import org.onlinetaskforce.web.frontend.pages.wagenbeheer.OverviewWagenPage;
import org.onlinetaskforce.web.frontend.security.Permissions;

/**
 * @author jordens
 * @since 9/03/13
 */
public class Menu extends Panel{
    private WebMarkupContainer reservatiesMenu;
    private WebMarkupContainer gebruikerbeheerMenu;
    private WebMarkupContainer wagenbeheerMenu;

    public Menu(String id) {
        super(id);

        createHomeMenu();
        createReserevationMenu();
        createGebruikersbeheerMenu();
        createWagenbeheerMenu();
    }

    private void createHomeMenu() {
        add(new BookmarkablePageLink<HomePage>("home-menu", HomePage.class));
    }

    private void createReserevationMenu() {
        reservatiesMenu = new WebMarkupContainer("reservaties-menu");
        add(reservatiesMenu);
        add(new OtfSecureBookmarkablePageLink<OverviewReservatiePage>("overview-reservaties-menu", OverviewReservatiePage.class));
        add(new OtfSecureBookmarkablePageLink<DetailReservatiePage>("create-reservaties-menu", ReservatieWizardPage.class));
    }

    private void createGebruikersbeheerMenu() {
        gebruikerbeheerMenu = new WebMarkupContainer("user-menu");
        add(gebruikerbeheerMenu);
        OtfSecureBookmarkablePageLink<PictureUserPage> pictureMenu = new OtfSecureBookmarkablePageLink<PictureUserPage>("menu-item", PictureUserPage.class);
        MenuItem pictureMenuItem = new MenuItem("pictureMenuIem", pictureMenu, "Foto");
        pictureMenuItem.add(pictureMenu);
        add(pictureMenuItem);

        OtfSecureBookmarkablePageLink<OverviewUserPage> overviewUserMenu = new OtfSecureBookmarkablePageLink<OverviewUserPage>("menu-item", OverviewUserPage.class);
        MenuItem overviewUserMenuItem = new MenuItem("overviewUserMenuIem", overviewUserMenu, "Overzicht");
        overviewUserMenuItem.add(overviewUserMenu);
        add(overviewUserMenuItem);
        Permissions.of(overviewUserMenuItem)
                .render(Permission.ADMIN)
                .enable(Permission.ADMIN);

        OtfSecureBookmarkablePageLink<DetailUserPage> detailUserMenu = new OtfSecureBookmarkablePageLink<DetailUserPage>("menu-item", DetailUserPage.class);
        MenuItem detailUserMenuItem = new MenuItem("detailUserMenuIem", detailUserMenu, "Aanmaken");
        detailUserMenuItem.add(detailUserMenu);
        add(detailUserMenuItem);
        Permissions.of(detailUserMenuItem)
                .render(Permission.ADMIN)
                .enable(Permission.ADMIN);

        add(new OtfSecureBookmarkablePageLink<WachtwoordPage>("change-password", WachtwoordPage.class));
    }

    private void createWagenbeheerMenu() {
        wagenbeheerMenu = new WebMarkupContainer("wagen-menu");
        add(wagenbeheerMenu);
        add(new OtfSecureBookmarkablePageLink<OverviewWagenPage>("overview-wagen-menu", OverviewWagenPage.class));
        add(new OtfSecureBookmarkablePageLink<DetailWagenPage>("detail-wagen-menu", DetailWagenPage.class));
        Permissions.of(wagenbeheerMenu)
                .render(Permission.ADMIN)
                .enable(Permission.ADMIN);
    }

    @Override
    protected void onInitialize() {
        super.onInitialize();
    }

    public WebMarkupContainer getReservatiesMenu() {
        return reservatiesMenu;
    }

    public void setReservatiesMenu(WebMarkupContainer reservatiesMenu) {
        this.reservatiesMenu = reservatiesMenu;
    }

    public WebMarkupContainer getGebruikerbeheerMenu() {
        return gebruikerbeheerMenu;
    }

    public void setGebruikerbeheerMenu(WebMarkupContainer gebruikerbeheerMenu) {
        this.gebruikerbeheerMenu = gebruikerbeheerMenu;
    }

    public WebMarkupContainer getWagenbeheerMenu() {
        return wagenbeheerMenu;
    }

    public void setWagenbeheerMenu(WebMarkupContainer wagenbeheerMenu) {
        this.wagenbeheerMenu = wagenbeheerMenu;
    }
}
