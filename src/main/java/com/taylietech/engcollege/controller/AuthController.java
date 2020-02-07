package com.taylietech.engcollege.controller;

import com.taylietech.engcollege.model.User;
import com.taylietech.engcollege.model.security.PasswordResetToken;
import com.taylietech.engcollege.model.security.Role;
import com.taylietech.engcollege.model.security.UserRole;
import com.taylietech.engcollege.service.UserService;
import com.taylietech.engcollege.service.impl.UserSecurityService;
import com.taylietech.engcollege.util.MailConstructor;
import com.taylietech.engcollege.util.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Controller
public class AuthController {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailConstructor mailConstructor;

    @Autowired
    UserService userService;

    @Autowired
    UserSecurityService userSecurityService;



    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request, Model model, @ModelAttribute("email") String userEmail, @ModelAttribute("username") String userName) throws Exception {

        model.addAttribute("email", userEmail);
        model.addAttribute("username", userName);

        if(userService.findByUserName(userName) != null || userService.findByEmail(userEmail) != null) {
            model.addAttribute("userExist", true);
            return "/auth/register";
        }

        User user = new User();
        user.setUserName(userName);
        user.setEmail(userEmail);

        String password = SecurityUtility.randomPassword();
        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassWord(encryptedPassword);

        Role role = new Role();
        role.setRoleId(1L);
        role.setName("ROLE_USER");

        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(new UserRole(user, role));

        userService.createUser(user, userRoles);


        String token = UUID.randomUUID().toString();
        userService.createResetTokenForUser(user, token);

        String appUrl = "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();

        SimpleMailMessage email = mailConstructor.constructResetTokenEmail(appUrl, request.getLocale(), token, user, password);

        javaMailSender.send(email);

        model.addAttribute("emailSent", "true");

        return "/auth/register";
    }



    @RequestMapping("/resetPassword")
    public String forgotPasswordReset( HttpServletRequest request, @ModelAttribute("email") String email, Model model) {

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




    @RequestMapping("/newUser")
    public String newUser(Locale locale, @RequestParam("token") String token, Model model) {
        PasswordResetToken passToken = userService.getPasswordResetToken(token);

        if(passToken == null) {
            String message = "Invalid Token!";
            model.addAttribute("message", message);

            return "redirect:/badRequest";
        }

        User user = passToken.getUser();
        String username = user.getUsername();

        UserDetails userDetails = userSecurityService.loadUserByUsername(username);
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authentication);

        model.addAttribute("user", user);

        return "/auth/userProfile";
    }
}
