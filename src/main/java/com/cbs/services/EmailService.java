package com.cbs.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

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
	
	//Send email Reset password: User - user
	@Async
	public void sendEmail(User user) throws MailException {
		//send email
		SimpleMailMessage email = new SimpleMailMessage();
		email.setTo(user.getEmail());
		email.setFrom("4brotherstechvn@gmail.com");
		email.setSubject("New Password");
		email.setText("Dear you, New Password Success! Please sign in new password. Have a good time to enjoy this film");
		
		mailSender.send(email);	
	}
	
	//Send email Order Details
		@Async
		public void sendOrderEmail(User user) throws MailException {
			//send email
			SimpleMailMessage email = new SimpleMailMessage();
			email.setTo(user.getEmail());
			email.setFrom("4brotherstechvn@gmail.com");
			email.setSubject("Order Details");
			email.setText("Dear you, Thank you for ordered tickets! Please check order details information. Have a good time to enjoy this film");
			
			mailSender.send(email);	
		}
}
