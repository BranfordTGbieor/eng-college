package com.taylietech.engcollege.util;

import com.taylietech.engcollege.controller.SubscriptionController;
import com.taylietech.engcollege.model.User;
import com.taylietech.engcollege.service.UserService;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class SubscribeHandler {

    Log LOG = LogFactory.getLog(SubscriptionController.class);

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    UserService userService;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailConstructor mailConstructor;

    Model model;

    HttpServletRequest request;

    HttpServletResponse response;

    public void subscribe(String email) throws Exception {

            User user = userService.findByEmail(email);

            if (null != user) {

                if (!(user.isSubscribed())) {

                    user.setSubscribed(true);
                    userService.save(user);

                    SimpleMailMessage userEmail = mailConstructor.constructSubscribeEmail(email);
                    javaMailSender.send(userEmail);
                    model.addAttribute("subscribeMessageSent", "true");
                    redirectStrategy.sendRedirect(request, response, "/auth/subscription");

                } else {

                    if (user.isSubscribed()) {

                        model.addAttribute("alreadySubscribed", true);
                        redirectStrategy.sendRedirect(request, response, "/auth/subscription");
                    }
                }

            } else {

                if (null == user) {

                    redirectStrategy.sendRedirect(request, response, "/auth/subscription");
                    model.addAttribute("registerToSubscribe", true);
                    throw new UsernameNotFoundException("The user will have to register to subscribe!");  //there might no need for throwing this exception. Maybe the logging the info will be best.
                }
            }

    }
}
