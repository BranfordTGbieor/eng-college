package com.taylietech.engcollege.service;

import com.taylietech.engcollege.model.User;
import com.taylietech.engcollege.model.security.PasswordResetToken;
import com.taylietech.engcollege.model.security.UserRole;

import java.util.Optional;
import java.util.Set;

public interface UserService {

    PasswordResetToken getPasswordResetToken(final String token);

    void createResetTokenForUser(final User user, final String token);

    User findByUserName(String username);

    User findByEmail(String email);

    Optional<User> findById(Long id);

    User createUser(User user, Set<UserRole> userRoles) throws Exception;

    User save(User user);

    void createPasswordResetTokenForUser(final User user, final String token);
}
