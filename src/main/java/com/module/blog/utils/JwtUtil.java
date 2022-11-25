package com.module.jwt;

import com.module.jedis.RedisUtil;
import com.module.security.CookieUtil;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class JwtUtil {
    private static final String REDIS_SET_ACTIVE_SUBJECTS = "active-subjects";
    private String signingKey ="secret";
    private static final Logger logger = LoggerFactory
            .getLogger(JwtUtil.class);


    public String generateToken( String subject) {
        long nowMillis = System.currentTimeMillis();
        Date now = new Date(nowMillis);

        JwtBuilder builder = Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .signWith(SignatureAlgorithm.HS256, signingKey);

        String token = builder.compact();

        RedisUtil.INSTANCE.sadd(REDIS_SET_ACTIVE_SUBJECTS, subject);

        return token;
    }

    public String getEmailFromToken(HttpServletRequest httpServletRequest, String jwtTokenCookieName){
        String subject = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(CookieUtil.getValue(httpServletRequest, jwtTokenCookieName)).getBody().getSubject();
        return subject;
    }

    public boolean validToken(HttpServletRequest httpServletRequest, String jwtTokenCookieName, String signingKey) throws RuntimeException {

        String token = CookieUtil.getValue(httpServletRequest, jwtTokenCookieName);
        //1. check there is matched cookie with cookie name
        if (token == null) {
            logger.info("there is no matched cookie with that name");
            //this error will be caught by custom exception filter.
            throw new AuthenticationCredentialsNotFoundException("login token is null");
        }
        //2. if there is cookie check the cookie with subject name
        String subject = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token).getBody().getSubject();

        if (!RedisUtil.INSTANCE.sismember(REDIS_SET_ACTIVE_SUBJECTS, subject)) {
            logger.info("Subject is not matched");
            return false;
        }

        return true;
    }

    public boolean invalidateRelatedTokens(HttpServletRequest httpServletRequest) {

        if(httpServletRequest.getAttribute("email")!=null){
        if (RedisUtil.INSTANCE.srem(REDIS_SET_ACTIVE_SUBJECTS, (String) httpServletRequest.getAttribute("email"))) {
            return true;
        }
        }
        return false;

    }
}