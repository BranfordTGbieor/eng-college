package com.taylietech.engcollege.util;

import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class SecurityUtility {

    private static final String SALT = "salt";

    @Bean
    public static BCryptPasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder(12, new SecureRandom(SALT.getBytes()));
    }


    @Bean
    public static String randomPassword() {
        String SALT_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rand = new Random();

        while (salt.length() < 18 ) {

            int index = (int) (rand.nextFloat()*SALT_CHARS.length());
            salt.append(SALT_CHARS.charAt(index));
        }

        String saltstr = salt.toString();
        return saltstr;
    }
}
