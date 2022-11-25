package com.module.blog.service.impl;

import com.module.lambda.BindParameterSupplier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    @Override
    public User signUp(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    @Override
    public boolean checkEmailIsDuplicated(String email) {
        return Optional.ofNullable(userRepository.getUserByEmail(email)).isPresent();
    }

    @Override
    public void deleteUser(String email) {
       User user = Optional.
               ofNullable(userRepository.getUserByEmail(email)).
               orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new, "there is no user by that email"));
        userRepository.delete(user);
    }
    @Override
    public boolean login(String email, String password) throws AuthenticationException {

            User user =
                    Optional.
                            ofNullable(userRepository.getUserByEmail(email)).
                            orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new,"there is no email"));

            if (bCryptPasswordEncoder.matches(password, user.getPassword())
                    && user.getEmail().equals(email)){
                return true;
        }
            return false;
    }

    @Override
    public User findUserByEmail(String email) {
        return Optional.
                ofNullable(userRepository.getUserByEmail(email)).
                orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new, "there is no user by that email"));
    }


    @Override
    public User updateUser(User user) {
        return Optional.
                ofNullable(userRepository.save(user)).
                orElseThrow(BindParameterSupplier.bind(IllegalArgumentException::new, "can't update the user"));
    }

    @Override
    public List<User> getAllUser() {
        return Optional
                .ofNullable(userRepository.findAll())
                .orElseThrow(BindParameterSupplier.bind(EntityNotFoundException::new, "there is no user"));
    }


    @Override
    public User loadUserByUsername(String email) throws UsernameNotFoundException {


        if(userRepository.getUserByEmail(email)!=null){
            return userRepository.getUserByEmail(email);
        }

        throw new UsernameNotFoundException("email or password is not matched");
    }
}

