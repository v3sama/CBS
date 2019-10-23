package com.cbs.controllers;

import com.cbs.model.User;
import com.cbs.services.UserService;

import java.security.Principal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ws.support.WebUtils;

@Controller
public class LoginController {

	private final UserService userService;

	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;
	}

	/*
	 * @RequestMapping(value = "/admin/", method = RequestMethod.GET) public String
	 * index(Model model) { return "/admin/index"; }
	 */

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout) {
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		return "/client/login";
	}

	/*
	 * @RequestMapping(value = "/registration", method = RequestMethod.GET) public
	 * String registration(Model model) { model.addAttribute("userForm", new
	 * User()); return "/registration"; }
	 * 
	 * @RequestMapping(value = "/registration", method = RequestMethod.POST) public
	 * String registration(@ModelAttribute("userForm") User userForm, BindingResult
	 * bindingResult, Model model) { if (bindingResult.hasErrors()) { return
	 * "registration"; } userService.add(userForm);
	 * 
	 * return "redirect:/"; }
	 */

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {
			User loginedUser = (User) ((Authentication) principal).getPrincipal();

			String userInfo = loginedUser.getLastName() + ' ' + loginedUser.getFirstName();
			model.addAttribute("userInfo", userInfo);

			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}

		return "403Page";
	}

}
