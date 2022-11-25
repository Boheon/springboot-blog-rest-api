package com.module.blog.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;
import java.util.Optional;

public interface UserService extends UserDetailsService {

    User signUp(User user);
    boolean checkEmailIsDuplicated(String email);
    void deleteUser(String email);
    boolean login(String email,String password);
    User findUserByEmail(String email);
    User updateUser(User user);
    List<User> getAllUser();

}
