package com.taylietech.engcollege.controller;

import com.taylietech.engcollege.model.User;
import com.taylietech.engcollege.service.UserService;
import com.taylietech.engcollege.service.impl.UserSecurityService;
import com.taylietech.engcollege.util.MailConstructor;
import com.taylietech.engcollege.util.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

@Controller
public class ResetPasswordController {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailConstructor mailConstructor;

    @Autowired
    UserService userService;

    @Autowired
    UserSecurityService userSecurityService;


    //controller to reset a user password
    @RequestMapping("/resetPassword")
    public String forgotPasswordReset(HttpServletRequest request, @ModelAttribute("email") String email, Model model) {

        //model.addAttribute("classActiveForgetPassword", true);

        User user = userService.findByEmail(email);

        if (user == null) {
            model.addAttribute("emailNotExist", true);
            return "/auth/forgotPassword";
        }

        String password = SecurityUtility.randomPassword();

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassWord(encryptedPassword);

        userService.save(user);

        String token = UUID.randomUUID().toString();
        userService.createPasswordResetTokenForUser(user, token);

        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

        SimpleMailMessage newEmail = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        javaMailSender.send(newEmail);

        model.addAttribute("forgetPasswordEmailSent", "true");


        return "/auth/forgotPassword";
    }
}
