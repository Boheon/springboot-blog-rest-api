package com.module.blog.provider;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;


//no need to authenticate
public class GroupAnonymousAuthenticationProvider implements GroupProvider {
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {
        return authentication;
    }


    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}

