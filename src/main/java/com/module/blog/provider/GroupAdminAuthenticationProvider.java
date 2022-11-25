package com.module.blog.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

import java.util.Collections;

@RequiredArgsConstructor
public class GroupAdminAuthenticationProvider implements GroupProvider {
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        if(!authentication.
                getAuthorities().
                stream().
                findFirst().
                filter(s->s.getAuthority().matches("ROLE_GROUP_ADMIN")).
                isPresent()){
            throw new BadCredentialsException("you are not admin, you are: " + authentication.getAuthorities());
        }

        return authentication;
    }



    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}
