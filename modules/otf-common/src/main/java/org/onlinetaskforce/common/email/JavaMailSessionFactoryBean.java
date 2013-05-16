package org.onlinetaskforce.common.email;

import org.springframework.beans.factory.FactoryBean;

import javax.mail.Session;
import java.util.Properties;

/**
 * With this JavaMail Session factory we centralize the mail configuration.
 *
 * @author jordens
 * @since 28/03/13
 */
public class JavaMailSessionFactoryBean implements FactoryBean {

    private Session session;

    /**
     * The JavaMail properties of the mail Session.
     * A new Session is created with this properties.
     * Check JavaMail documentation for an overview of the possible properties.
     *
     * @param javaMailProperties see description
     */
    public void setJavaMailProperties(Properties javaMailProperties) {
        this.session = Session.getInstance(javaMailProperties);
    }

    public Object getObject() {
        return session;
    }

    public Class getObjectType() {
        return Session.class;
    }

    public boolean isSingleton() {
        return true;
    }
}
