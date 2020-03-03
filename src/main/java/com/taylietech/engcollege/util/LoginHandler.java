package com.taylietech.engcollege.util;

import com.taylietech.engcollege.model.User;
import com.taylietech.engcollege.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Transactional
public class LoginHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            com.taylietech.engcollege.model.User userSession = userService.findByUserName(user.getUsername());
            HttpSession session = request.getSession();
            session.setAttribute("user", userSession);
            redirectStrategy.sendRedirect(request, response, "/");
        } catch (Exception x) {
            x.printStackTrace();
        }
    }

}
