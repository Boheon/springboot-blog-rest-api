package com.module.blog.provider;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;

@RequiredArgsConstructor
public class GroupUserAuthenticationProvider implements GroupProvider {
    @Override
    public Authentication authenticate(Authentication authentication)
            throws AuthenticationException {

        //this authentication should make admin can pass as well so add the condition
        if (authentication.
                getAuthorities().
                stream().
                findFirst().
                filter(s -> s.getAuthority().matches("ROLE_GROUP_USER")).
                isEmpty() && authentication.
                getAuthorities().
                stream().
                findFirst().
                filter(s -> s.getAuthority().matches("ROLE_GROUP_ADMIN")).
                isEmpty()){
            throw new BadCredentialsException("you are not user or admin, you are: " + authentication.getAuthorities());

        }

        return authentication;
    }


    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}