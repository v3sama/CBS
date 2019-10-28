package com.cbs.controllers;

import com.cbs.dto.CustomUserDetail;
import com.cbs.model.User;
import com.cbs.services.UserService;

import java.io.IOException;
import java.security.Principal;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.resource.HttpResource;
import org.springframework.ws.support.WebUtils;

@Controller
public class LoginController {

	private final UserService userService;
	private CustomUserDetail loggedInUser;
	private UserDetails userDetails;
	private String userInfo;

	@Autowired
	public LoginController(UserService userService) {
		this.userService = userService;

	}

	/*
	 * @RequestMapping(value = "/admin/", method = RequestMethod.GET) public String
	 * index(Model model) { return "/admin/index"; }
	 */

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(Model model, String error, String logout, Principal principal) {
		Authentication authentication = (Authentication) SecurityContextHolder.getContext().getAuthentication();// .getPrincipal();

		if (error != null)
			model.addAttribute("error", "Your username and password is invalid.");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully.");

		// đã login
		if (principal != null) {
			GrantedAuthority adminAuth = new SimpleGrantedAuthority("ADMIN");
			// role = "ADMIN"
			if (authentication.getAuthorities().size() != 0
					&& authentication.getAuthorities().iterator().next().equals(adminAuth))
				return "/admin/index";
			else
				return "/client/index";
		}
		return "/client/login";
	}

	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {
		if (principal != null) {
			loggedInUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			userInfo = loggedInUser.getFname();
			loggedInUser.getUserId();
			model.addAttribute("userInfo", userInfo);
			String message = "Hi " + userInfo //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);

		}

		return "403Page";
	}

}
