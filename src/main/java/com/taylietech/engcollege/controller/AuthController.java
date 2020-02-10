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


//controller to register a new user
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String register(HttpServletRequest request, Model model, @ModelAttribute("email") String userEmail, @ModelAttribute("username") String userName) throws Exception {

        model.addAttribute("email", userEmail);
        model.addAttribute("username", userName);

        if(userService.findByUserName(userName) != null || userService.findByEmail(userEmail) != null) {
            model.addAttribute("userExist", true);
            return "/auth/register";
        } else {

            if (userName.isEmpty() && userEmail.isEmpty()) {
                model.addAttribute("usernameBlank", true);
                model.addAttribute("emailBlank", true);
                return "/auth/register";

            } else {

                if (userName.isEmpty()) {
                    model.addAttribute("usernameBlank", true);
                    return "/auth/register";
                } else {

                    if (userEmail.isEmpty()) {
                        model.addAttribute("emailBlank", true);
                        return "/auth/register";
                    }
                }
            }
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

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, Model model,
                        @ModelAttribute("email") String userEmail,
                        @ModelAttribute("username") String userName) throws Exception {

        model.addAttribute("email", userEmail);
        model.addAttribute("username", userName);

        if (userName.isEmpty() && userEmail.isEmpty()) {
            model.addAttribute("usernameBlank", true);
            model.addAttribute("emailBlank", true);
            return "/auth/login";

        } else {

            if (userName.isEmpty()) {
                model.addAttribute("usernameBlank", true);
                return "/auth/login";
            } else {

                if (userEmail.isEmpty()) {
                    model.addAttribute("emailBlank", true);
                    return "/auth/login";
                }
            }

            return "/";
        }
    }


//controller to reset a user password
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



//controller to add a new user to the model
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

        return "/auth/userDetails";
    }

//controller to update user details
    @RequestMapping("/updateUserDetails")
    public String updateUser(Model model, Locale locale,
                             @ModelAttribute("email") String userEmail,
                             @ModelAttribute("username") String userName,
                             @ModelAttribute("firstName") String firstName,
                             @ModelAttribute("lastName") String lastName,
                             @ModelAttribute("phone") String phone,
                             @ModelAttribute("password") String password,
                             @ModelAttribute("retypedPassword") String retypedPassword,
                             @ModelAttribute("id") Long id) {

        model.addAttribute("id", id);
        model.addAttribute("email",userEmail);
        model.addAttribute("username",userName);
        model.addAttribute("firstName",firstName);
        model.addAttribute("lastName",lastName);
        model.addAttribute("phone",phone);
        model.addAttribute("password",password);
        model.addAttribute("retypedPassword",retypedPassword);

        int phoneMinLength = 13;

        if (userService.findByUserName(userName) != null) {

            model.addAttribute("userNameExist", true);
            return "/auth/userDetails";
        }

        if (userService.findByEmail(userEmail) != null) {

            model.addAttribute("userEmailExist", true);
            return "/auth/userDetails";
        }

        if (retypedPassword != password) {

            model.addAttribute("passwordMismatch", true);
            return "/auth/userDetails";
        }

        //proper validation to be done on phone later
        if (phone.length() < phoneMinLength || phone.length() > phoneMinLength) {

            model.addAttribute("invalidPhone", true);
            return "/auth/userDetails";
        }


        User user = new User();
        user.setUserName(userName);
        user.setEmail(userEmail);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(password);
        user.setPassWord(encryptedPassword);

        userService.save(user);
        model.addAttribute("userDetailsUpdated",true);

        return "/auth/userDetails";

    }

}
