package com.taylietech.engcollege.controller;

import com.taylietech.engcollege.model.User;
import com.taylietech.engcollege.model.security.Role;
import com.taylietech.engcollege.model.security.UserRole;
import com.taylietech.engcollege.service.UserService;
import com.taylietech.engcollege.service.impl.UserSecurityService;
import com.taylietech.engcollege.util.MailConstructor;
import com.taylietech.engcollege.util.SecurityUtility;
import com.taylietech.engcollege.util.validations.UserInputValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Controller
@RequestMapping("/register")
public class RegisterController {

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    MailConstructor mailConstructor;

    @Autowired
    UserService userService;

    @Autowired
    UserSecurityService userSecurityService;

    @ModelAttribute("user")
    public User user() {
        return new User();
    }

    //controller to register a new user
    @PostMapping
    public String register(@ModelAttribute("user") User user, HttpServletRequest request, Model model,
                           @ModelAttribute("email") String userEmail,
                           @ModelAttribute("username") String userName) throws Exception {

        model.addAttribute("email", userEmail);
        model.addAttribute("username", userName);

        User _user = userService.findByUserName(userName);
        User _email = userService.findByEmail(userEmail);

        if(_user != null && _email != null) {
            model.addAttribute("userExist", true);
            return "/auth/register";
        }

        if ( _user != null)
        {
            model.addAttribute("userNameExist", true);
            return "/auth/register";
        }

        if ( _email != null)
        {
            model.addAttribute("userEmailExist", true);
            return "/auth/register";
        }



        if (userName.isEmpty() && userEmail.isEmpty()) {
            model.addAttribute("usernameBlank", true);
            model.addAttribute("emailBlank", true);
            return "/auth/register";

        }

        if (userName.isEmpty()) {
                model.addAttribute("usernameBlank", true);
                return "/auth/register";
        } else {

            if (userEmail.isEmpty()) {
                    model.addAttribute("emailBlank", true);
                    return "/auth/register";
                }
            }

        if (userName.length() < 3) {

               model.addAttribute("lessCharacter", true);
               return "/auth/register";
        }



        if (UserInputValidation.isValidEmail(userEmail) == false) {

            model.addAttribute("emailNotValid", true);
            return "/auth/register";
        }


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

    @RequestMapping
    public String getRegisterPage(Model model) {
        return "/auth/register";
    }

}
