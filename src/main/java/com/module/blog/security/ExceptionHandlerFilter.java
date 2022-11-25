package com.module.blog.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.module.error.ApiError;
import com.nimbusds.oauth2.sdk.ErrorResponse;
import org.hibernate.annotations.Filter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    public void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            filterChain.doFilter(request, response);
    } catch (Exception e) {
            ResponseEntity responseEntity = ApiError.buildApiError(
                            ApiError.builder().
                            status(HttpStatus.UNAUTHORIZED).
                            timestamp(LocalDateTime.now()).
                            message(e.getMessage()).
                            build());

            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(convertObjectToJson(responseEntity.getBody()));
        }
    }

    public String convertObjectToJson(Object object) throws JsonProcessingException {
        if (object == null) {
            return null;
        }
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);

        return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
    }
}