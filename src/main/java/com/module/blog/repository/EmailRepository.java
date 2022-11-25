package com.module.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<EmailToken,Integer> {
    EmailToken findByConfirmationToken(String confirmationToken);

}
