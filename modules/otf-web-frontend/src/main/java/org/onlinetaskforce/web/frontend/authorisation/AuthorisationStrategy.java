package org.onlinetaskforce.web.frontend.authorisation;

import org.apache.wicket.Component;
import org.apache.wicket.Page;
import org.apache.wicket.RestartResponseAtInterceptPageException;
import org.apache.wicket.Session;
import org.apache.wicket.authorization.Action;
import org.apache.wicket.authorization.IAuthorizationStrategy;
import org.apache.wicket.request.component.IRequestableComponent;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.onlinetaskforce.web.frontend.pages.HomeLoginPage;
import org.onlinetaskforce.web.frontend.security.Permissions;
import org.onlinetaskforce.web.frontend.session.OtfWebSession;

/**
 * @author jordens
 * @since 10/03/13
 */
public class AuthorisationStrategy implements IAuthorizationStrategy{
    @Override
    public <T extends IRequestableComponent> boolean isInstantiationAuthorized(Class<T> componentClass) {
        if (!Page.class.isAssignableFrom(componentClass)) {
            return true;
        }
        if (!SecurePage.class.isAssignableFrom(componentClass)) {
            return true;
        }
        if (((OtfWebSession)Session.get()).getGebruiker() == null) {
            PageParameters pageParameters = new PageParameters();
            pageParameters.add(HomeLoginPage.NEED_TO_LOGIN, "TRUE");
            throw new RestartResponseAtInterceptPageException(HomeLoginPage.class, pageParameters);
        }
        return true;
    }

    @Override
    public boolean isActionAuthorized(Component component, Action action) {
        if (action == Component.RENDER) {
            return Permissions.of(component).canRender();
        } else if (action == Component.ENABLE) {
            return Permissions.of(component).canEnable();
        }
        return true;
    }
}
