package com.taylietech.engcollege.controller;

import com.taylietech.engcollege.model.User;
import com.taylietech.engcollege.model.security.PasswordResetToken;
import com.taylietech.engcollege.service.UserService;
import com.taylietech.engcollege.service.impl.UserSecurityService;
import com.taylietech.engcollege.util.SecurityUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Locale;

@Controller
public class UserDetailsController {

    @Autowired
    UserService userService;

    @Autowired
    UserSecurityService userSecurityService;

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
                             @ModelAttribute("newPassword") String newPassword,
                             @ModelAttribute("retypeNewPassword") String retypeNewPassword,
                             @ModelAttribute("user") User user) {

        model.addAttribute("email",userEmail);
        model.addAttribute("username",userName);
        model.addAttribute("firstName",firstName);
        model.addAttribute("lastName",lastName);
        model.addAttribute("phone",phone);
        model.addAttribute("password",password);
        model.addAttribute("newPassword",newPassword);
        model.addAttribute("retypeNewPassword",retypeNewPassword);



        /*if (userService.findByUserName(userName) != null) {

            model.addAttribute("userNameExist", true);
            return "/auth/userDetails";
        }

        if (userService.findByEmail(userEmail) != null) {

            model.addAttribute("userEmailExist", true);
            return "/auth/userDetails";
        }

        if (retypeNewPassword != newPassword) {

            model.addAttribute("passwordMismatch", true);
            return "/auth/userDetails";
        }*/


        user.setUserName(userName);
        user.setEmail(userEmail);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setPhone(phone);

        String encryptedPassword = SecurityUtility.passwordEncoder().encode(newPassword);
        user.setPassWord(encryptedPassword);

        userService.save(user);
        model.addAttribute("userDetailsUpdated",true);

        return "/auth/userDetails";

    }


    @GetMapping("/userDetails")
    public String getUserDetails(Model model) {

        return "/auth/userDetails";
    }

}
