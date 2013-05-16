package org.onlinetaskforce.common.email;

import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Session;

/**
 * @author jordens
 * @since 28/03/13
 */
public class OtfMailSenderImpl extends JavaMailSenderImpl{

    public void setSession(Session session) {
        super.setSession(session);
        setPassword("yjo17263");
    }
}
