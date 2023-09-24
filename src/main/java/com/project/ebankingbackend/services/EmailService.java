package com.project.ebankingbackend.services;

import jakarta.mail.MessagingException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface EmailService {
    String sendMail(String to,String toName, String subject, String body) throws MessagingException, IOException;
}
