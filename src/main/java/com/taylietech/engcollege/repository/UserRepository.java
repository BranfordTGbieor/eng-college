package com.taylietech.engcollege.repository;

import com.taylietech.engcollege.model.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUserName(String username);

    User findByEmail(String email);
}
