package com.taylietech.engcollege.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.taylietech.engcollege.model.security.Authority;
import com.taylietech.engcollege.model.security.UserRole;
import com.taylietech.engcollege.util.constraint.ValidPassword;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class User implements UserDetails {

    public User() {}

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long userId;


    @Column(name = "username", nullable = false)
    private String userName;

    private String password;

    private String firstName;

    private String lastName;


    @Column(name = "email", nullable = false, updatable = false)
    private String email;

    private String phone;

    private boolean enabled=true;
    private boolean subscribed;


    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private Set<UserRole> userRoles = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserPost> userPost;

    @OneToMany(mappedBy = "user")
    @JsonIgnore
    private List<UserComment> userComment;


    public Long getId() {
        return userId;
    }

    public void setId(Long id) {
        this.userId = id;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassWord(String passWord) {
        this.password = passWord;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(boolean subscribed) {
        this.subscribed = subscribed;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        userRoles.forEach( ur -> authorities.add(new Authority(ur.getRole().getName())));

        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


}
