package com.project.ebankingbackend.services.impl;

import com.project.ebankingbackend.services.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Transactional
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {



    private JavaMailSender javaMailSender;

    @Override
    public String sendMail( String to,String toName, String subject, String body) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,true);
            helper.setFrom("y.lachguer01@gmail.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText( "<html>" +
                    "<head>" +
                    "    <style>" +
                    "        body {" +
                    "            font-family: Arial, sans-serif;" +
                    "            background-color: #f5f5f5;" +
                    "            margin: 0;" +
                    "            padding: 0;" +
                    "        }" +
                    "" +
                    "        .container {" +
                    "            max-width: 600px;\n" +
                    "            margin: 0 auto;\n" +
                    "            padding: 20px;\n" +
                    "            background-color: #ffffff;\n" +
                    "            border-radius: 10px;\n" +
                    "            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);\n" +
                    "        }\n" +
                    "\n" +
                    "        h2 {\n" +
                    "            color: #333333;\n" +
                    "        }\n" +
                    "\n" +
                    "        p {\n" +
                    "            font-size: 16px;\n" +
                    "            color: #555555;\n" +
                    "            line-height: 1.5;\n" +
                    "            margin: 10px 0;\n" +
                    "        }\n" +
                    "\n" +
                    "        .signature {\n" +
                    "            color: #888888;\n" +
                    "            margin-top: 20px;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <h2>Welcome to Atlas Bank Platform!</h2>\n" +
                    "        <p>Dear "+toName+",</p>\n" +
                    "        <p>"+body+"</p>\n" +
                    "        <p>Thank you for joining us and taking the first step towards an amazing journey together!</p>\n" +
                    "        <p>If you have any questions or need assistance, please don't hesitate to reach out.</p>\n" +
                    "        <p class=\"signature\">Best regards,<br>The Atlas Bank Platform Team</p>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n",true);
/*
        for (int i=0;i<file.length;i++) {
                helper.addAttachment(
                        file[i].getOriginalFilename(),
                        new ByteArrayResource(file[i].getBytes())
                );
        }
*/
            javaMailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


        return "mail sent";
    }

}
