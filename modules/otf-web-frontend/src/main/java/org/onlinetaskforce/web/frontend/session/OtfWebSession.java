package org.onlinetaskforce.web.frontend.session;

import org.apache.wicket.Session;
import org.apache.wicket.injection.Injector;
import org.apache.wicket.protocol.http.WebSession;
import org.apache.wicket.request.Request;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.onlinetaskforce.business.services.GebruikerService;
import org.onlinetaskforce.common.domain.Gebruiker;
import org.onlinetaskforce.common.enumerations.Permission;

/**
 * @author jordens
 * @since 10/03/13
 */
public class OtfWebSession extends WebSession{
    @SpringBean
    private GebruikerService gebruikerService;
    private Gebruiker gebruiker;

    /**
     * Constructor. Note that {@link org.apache.wicket.request.cycle.RequestCycle} is not available until this constructor returns.
     *
     * @param request The current request
     */
    public OtfWebSession(Request request) {
        super(request);
        Injector.get().inject(this);
    }

    /**
	 * @return Current authenticated web session
	 */
	public static OtfWebSession get() {
		return (OtfWebSession) Session.get();
	}


    public Gebruiker getGebruiker() {
        return gebruiker;
    }

    public void setGebruiker(Gebruiker gebruiker) {
        this.gebruiker = gebruiker;
    }

    public boolean login(String username, String password) {
        gebruiker = gebruikerService.getGebruiker(username);
        if (gebruiker != null && gebruiker.match(username, password)) {
            return true;
        }
        return false;
    }

    public boolean isSignedIn() {
        return gebruiker != null;
    }

    public boolean hasPermission(Permission permission) {
        if (gebruiker != null) {
            switch (permission) {
                case ADMIN:
                    return gebruiker.hasRole(Permission.ADMIN);
                case RESERVEERDER:
                    return gebruiker.hasRole(Permission.RESERVEERDER);
                default:
                    return false;
            }
        }
        return false;
    }
}
