package com.cbs.services;

import java.nio.charset.StandardCharsets;

import javax.mail.internet.MimeMessage;
import org.thymeleaf.context.Context;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring5.SpringTemplateEngine;

import com.cbs.model.Mail;
import com.cbs.model.User;

@Service
public class EmailService {
	private JavaMailSender mailSender;

	@Autowired
	public EmailService(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}

	
//	Tam thoi tat nay de lam 
	@Async
	public void sendEmail(SimpleMailMessage email) {
		mailSender.send(email);
	}
	
//Reset Pass
	@Autowired
    private SpringTemplateEngine templateEngine;

    public void sendResetEmail(Mail mail) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message,
                    MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());

            Context context = new Context();
            context.setVariables(mail.getModel());
            String html = templateEngine.process("email/email-template", context);

            helper.setTo(mail.getTo());
            helper.setText(html, true);
            helper.setSubject(mail.getSubject());
            helper.setFrom(mail.getFrom());

            mailSender.send(message);
        } catch (Exception e){
            throw new RuntimeException(e);
        }
    }
	
	
	
}
