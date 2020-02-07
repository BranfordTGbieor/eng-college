package com.taylietech.engcollege.repository;

import com.taylietech.engcollege.model.security.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Long> {

    Role findByName(String name);
}
