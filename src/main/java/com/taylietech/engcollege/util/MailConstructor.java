package com.taylietech.engcollege.util;

import com.taylietech.engcollege.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Locale;

@Component
public class MailConstructor {

    @Autowired
    private Environment env;

    public SimpleMailMessage constructResetTokenEmail(
            String contextPath, Locale locale, String token, User user, String password) {

        String url = contextPath + "/newUser?token="+token;
        String message = "\nPlease click on this link to verify your email and edit your personal information. Your password is: \n\n"+password;
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(user.getEmail());
        email.setSubject("Registration Completion");
        email.setText(url+message);
        email.setFrom(env.getProperty("support.email"));

        return email;

    }


    public SimpleMailMessage constructMessageEmail( String name, String userEmail, String phone, String subject, String message) {

        String userMessage = message + "\n\n\n" +name+ "\n"+phone;

        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(env.getProperty("support.email"));
        email.setSubject(subject);
        email.setFrom(userEmail);
        email.getSentDate();
        email.setText(userMessage);

        return email;

    }

    public SimpleMailMessage constructSubscribeEmail( String _email) {

        String message = "\nYou have subscription for updates has been processed successfully!. \nYou can unsubscribe by clicking:";
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(_email);
        email.setSubject("User Subscription Confirmation");
        email.setText(message);
        email.setFrom(env.getProperty("support.email"));

        return email;

    }

}

