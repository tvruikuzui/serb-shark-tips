package com.example.Service;

import com.example.Entity.Signal;
import com.sun.mail.smtp.SMTPSendFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

/**
 * Created by User on 09/06/2017.
 */

@Service
public class MailService {
    private JavaMailSender mailSender;

    @Autowired
    public MailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Async
    public void sendMail(ArrayList<String> mails, String body, String title){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("ahron901@gmail.com");
        mailMessage.setSubject(title);
        mailMessage.setText(body);
        for (String to :
                mails) {
            mailMessage.setTo(to);
            mailSender.send(mailMessage);
        }

    }
}
