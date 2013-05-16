package org.onlinetaskforce.web.frontend.models;

import org.apache.wicket.model.IModel;

/**
 * @author jordens
 * @since 15/03/13
 */
public class LoginModel implements IModel{
    private String username;
    private String password;

    public LoginModel() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Object getObject() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setObject(Object object) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void detach() {
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
