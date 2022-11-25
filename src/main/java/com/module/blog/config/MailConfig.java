package com.module.blog.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import java.util.Properties;

@Configuration
public class MailConfig {

    final String fromEmail = "rnwldnd7248@gmail.com"; //requires valid gmail id
    final String password = "exoyjqfefwlutwmy"; // correct password for gmail id


    @Bean
    public Authenticator setAuth(){
        return new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(fromEmail, password);
            }
        };
    }

    @Bean
    public Properties setSMTP(){
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true"); //enable STARTTLS
        props.put("mail.smtp.host", "smtp.gmail.com"); //SMTP Host
        props.put("mail.smtp.port", "587"); //TLS Port
        props.put("mail.smtp.auth", "true"); //enable authentication
        props.put("mail.smtp.ssl.trust", "smtp.gmail.com"); //ssl trust
        props.put("mail.smtp.ssl.protocols", "TLSv1.2"); //ssl trust
        props.put("mail.smtp.ssl.enable", "false");
        return props;
    }
    @Bean
    public SimpleMailMessage simpleMailMessage(){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setFrom("rnwldnd7248@gmail.com");
        return mailMessage;
    }

    @Bean
    public JavaMailSender javaMailSender() {
        return new JavaMailSenderImpl();
    }
}
