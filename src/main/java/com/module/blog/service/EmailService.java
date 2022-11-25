package com.module.blog.service;

import javax.mail.Authenticator;
import javax.mail.Session;
import java.util.Optional;
import java.util.Properties;

public interface EmailService {
        Authenticator setAuth();
        void sendEmail(Session session, String toEmail, String subject, String body);
        EmailToken save(EmailToken emailToken);
        EmailToken findByConfirmationToken(String confirmationToken);
        Properties setSMTPSession();
        String setTokenString();

}
