package com.taylietech.engcollege.util;

import com.taylietech.engcollege.model.User;
import org.apache.juli.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.apache.juli.logging.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Transactional
public class CustomLogoutHandler implements LogoutHandler {

    Log LOG = LogFactory.getLog(CustomLogoutHandler.class);

    @Override
    public void logout(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) {

        User user = (User) httpServletRequest.getSession().getAttribute("user");
        if (authentication != null && authentication.getDetails() != null) {
            try {
                httpServletRequest.getSession().invalidate();
                httpServletResponse.setStatus(HttpServletResponse.SC_OK);
                LOG.info("user logged out successfully!");
                httpServletResponse.sendRedirect("/");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
