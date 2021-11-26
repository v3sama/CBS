package com.cbs.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.cbs.model.Role;
import com.cbs.model.User;
import com.cbs.services.EmailService;
import com.cbs.services.RoleService;
import com.cbs.services.UserService;
import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;

@Controller
public class RegisterController {
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private UserService userService;
	private EmailService emailService;
	private RoleService roleService;

	@Autowired
	public RegisterController(UserService userService, BCryptPasswordEncoder bCryptPasswordEncoder,
			EmailService emailService, RoleService roleService) {
		// this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.userService = userService;
		this.emailService = emailService;
		this.roleService = roleService;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
	}

	// Return registration form template
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegistrationForm(ModelAndView modelAndView, User user) {
		modelAndView.addObject("user", user);
		modelAndView.setViewName("/client/register");
		return modelAndView;
	}

	// Process form input data
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView processRegistrationForm(ModelAndView modelAndView, @Valid User user,
			BindingResult bindingResult, HttpServletRequest request) {

		// Lookup user in database by e-mail, by phone
		User phoneExists = userService.findByPhone(user.getPhone());
		User userExists = userService.findByEmail(user.getEmail());

		System.out.println(userExists);

		if (userExists != null) {
			modelAndView.addObject("alreadyRegisteredMessage",
					"Oops!  There is already a user registered with the email provided.");
			modelAndView.setViewName("/client/register");
			bindingResult.reject("email");
		}
		if (phoneExists != null) {
			modelAndView.addObject("alreadyExitsPhone",
					"Oops!  There is already a user registered with the phone provided.");
			modelAndView.setViewName("/client/register");
			bindingResult.reject("phone");
		}

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("/client/register");
		} else { // new user so we create user and send confirmation e-mail

			// Disable user until they click on confirmation link in email
			user.setActive(false);

			// Set user's role to MEMBER
			Role memberRole = roleService.findByName("MEMBER");
			// Role adminRole = roleService.findByName("ADMIN");
			Set<Role> roles = new HashSet<Role>();
			roles.add(memberRole);
			// roles.add(adminRole);
			user.setRoles(roles);

			// user.setRoles(new HashSet<Role>(Arrays.asList(memberRole)));

			// Generate random 36-character string token for confirmation link
			user.setConfirmationToken(UUID.randomUUID().toString());

			userService.saveUser(user);

			String appUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();

			SimpleMailMessage registrationEmail = new SimpleMailMessage();
			registrationEmail.setTo(user.getEmail());
			registrationEmail.setSubject("Registration Confirmation");
			registrationEmail.setText("To confirm your e-mail address, please click the link below:\n" + appUrl
					+ "/confirm?token=" + user.getConfirmationToken());
			registrationEmail.setFrom("noreply@domain.com");

			emailService.sendEmail(registrationEmail);

			modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to " + user.getEmail());
			modelAndView.setViewName("/client/register");
		}

		return modelAndView;
	}

	// Process confirmation link
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {

		User user = userService.findByConfirmationToken(token);

		if (user == null) { // No token found in DB
			modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
		} else { // Token found
			modelAndView.addObject("confirmationToken", user.getConfirmationToken());
		}

		modelAndView.setViewName("/client/confirm");
		return modelAndView;
	}

	// Process confirmation link
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public ModelAndView processConfirmationForm(ModelAndView modelAndView, BindingResult bindingResult,
			@RequestParam Map requestParams, RedirectAttributes redir) {

		modelAndView.setViewName("/client/confirm");

		Zxcvbn passwordCheck = new Zxcvbn();

		Strength strength = passwordCheck.measure((String) requestParams.get("password"));

		if (strength.getScore() < 3) {
			bindingResult.reject("password");

			redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");

			modelAndView.setViewName("redirect:confirm?token=" + requestParams.get("token"));
			System.out.println(requestParams.get("token"));
			return modelAndView;
		}
		// Find the user associated with the reset token
		User user = userService.findByConfirmationToken((String) requestParams.get("token"));

		// Set new password
		user.setPassword(bCryptPasswordEncoder.encode((String) requestParams.get("password")));

		// Set user to enabled
		user.setActive(true);

		// Save user
		userService.saveUser(user);

		modelAndView.addObject("successMessage", "Your password has been set!");
		return modelAndView;
	}

}
