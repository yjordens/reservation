package org.onlinetaskforce.web.frontend.filters;

import org.onlinetaskforce.persistence.utils.ThreadContextInfo;
import org.onlinetaskforce.web.frontend.session.OtfWebSession;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * This filter is mapped to all webapp/ requests
 * This class will set the userDetails on the ThreadContextInfo
 *
 * @author jordens
 * @since 23/04/13
 */
public class ThreadContextInfoFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        if(OtfWebSession.exists()) {
            OtfWebSession session = OtfWebSession.get();

            try {
                ThreadContextInfo.getInstance().clear();
                ThreadContextInfo cInfo = ThreadContextInfo.getInstance();

                // save current user id and ROLE
                if(session.isSignedIn()) {
                    cInfo.setCurrentGebruikerId(session.getGebruiker().getId());
                }

                chain.doFilter(request, response);
            } finally {
                ThreadContextInfo.getInstance().clear();
            }
        } else {
            chain.doFilter(request, response);
        }
    }

    @Override
    public void destroy() {

    }

}
