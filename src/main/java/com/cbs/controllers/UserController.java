package com.cbs.controllers;

import com.cbs.dto.ChangePasswordDTO;
import com.cbs.dto.CustomUserDetail;
import com.cbs.dto.UpdateUserDTO;
import com.cbs.model.Discount;
import com.cbs.model.SOrder;
import com.cbs.model.User;
import com.cbs.services.DiscountService;
import com.cbs.services.OrderService;
import com.cbs.services.RoleService;
import com.cbs.services.UserService;

import org.hibernate.sql.Update;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;

@Controller
public class UserController {

	private final UserService userService;
	private final DiscountService discountService;
	private final RoleService roleService;
	private CustomUserDetail loggedInUser;
	private User user;
	@Autowired
	private BCryptPasswordEncoder encoder;

	@Autowired
	public UserController(UserService userService, DiscountService discountService, RoleService roleService) {
		this.userService = userService;
		this.discountService = discountService;
		this.roleService = roleService;

	}

	// Admin - List
	@RequestMapping(value = "/admin/user", method = RequestMethod.GET)
	public String allUser(Model model) {
		model.addAttribute("users", userService.findAll());
		// return userService.findAll();
		return "/admin/user-list";
	}

	// Edit - Admin User
	@RequestMapping(value = "/admin/edit/user", method = RequestMethod.GET, params = { "id" })
	public String getUserEdit(@RequestParam Long id, Model model) {
		model.addAttribute("user", userService.findById(id));
		model.addAttribute("allRoles", roleService.getAllRole());
		return "/admin/add/user";
	}

	@RequestMapping(value = "/admin/edit/user", method = RequestMethod.POST)
	public String editUser(@Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/edit/user";
		}
		userService.update(user);
		return "redirect:/admin/user";
	}

	// Add User-Role
	@RequestMapping(value = "/admin/add/user", method = RequestMethod.GET)
	public String addUser(Model model) {
		model.addAttribute("user", new User());
		model.addAttribute("allRoles", roleService.getAllRole());
		return "/admin/add/user";
	}

	@RequestMapping(value = "/admin/add/user", method = RequestMethod.POST)
	public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/admin/add/user";
		}

		user.setActive(true);
		user.setPassword("123456");
		userService.add(user);
		return "redirect:/admin/user";
	}

	@RequestMapping(value = "/admin/deactivate/user", method = RequestMethod.GET)
	public String deactivateUser(@RequestParam Long id, Model model) {
		userService.deactivate(id);
		return "redirect:/admin/user";
	}

	@RequestMapping(value = "/admin/activate/user", method = RequestMethod.GET)
	public String activateUser(@RequestParam Long id, Model model) {
		userService.activate(id);
		return "redirect:/admin/user";
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String getUserDetails(Model model) {
		model.addAttribute("user", userService.findByUsername(getPrincipal()));
		model.addAttribute("allDiscounts", discountService.getAllDiscount());
		model.addAttribute("discount", new Discount());
		return "/user";
	}

	@RequestMapping(value = "/user/discount", method = RequestMethod.POST)
	public String addUserDiscount(@Valid Discount discount, Model model, BindingResult bindingResult) {
		if (bindingResult.hasErrors())
			return "/user?error";
		User user = userService.findByUsername(getPrincipal());
		userService.update(user);
		return "redirect:/user";
	}

	private String getPrincipal() {
		String userName;
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof UserDetails) {
			userName = ((UserDetails) principal).getUsername();
		} else {
			userName = principal.toString();
		}
		return userName;
	}

	@RequestMapping(value = "/update-profile", method = RequestMethod.POST)
	public String updateProfile(@Valid UpdateUserDTO updateForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "";
		}
		loggedInUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user = loggedInUser.getUser();
		
		if(loggedInUser.getAuthorities().stream().findFirst().get().equals(new SimpleGrantedAuthority("ADMIN")))
			return "/admin/index";
		user.setFirstName(updateForm.getFirstName());
		user.setLastName(updateForm.getLastName());
		user.setPhone(updateForm.getPhone());
		
		
		userService.update(user);
		return "redirect:/profile";
	}

	@RequestMapping(value = "/change-password", method = RequestMethod.POST)
	public String changePassword(@Valid ChangePasswordDTO pwForm, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "";
		}
		loggedInUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		
		if (BCrypt.checkpw(pwForm.getOldPassword(), user.getPassword())
				&& pwForm.getNewPassword().equals(pwForm.getCfPassword())) {
			user.setPassword(pwForm.getNewPassword());
			userService.add(user);
		}
		return "redirect:/profile";
	}

	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String viewProfile(Model model) {
		loggedInUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user = loggedInUser.getUser();

		UpdateUserDTO updateForm = new UpdateUserDTO();
		updateForm.setEmail(user.getEmail());
		updateForm.setFirstName(user.getFirstName());
		updateForm.setLastName(user.getLastName());
		updateForm.setPhone(user.getPhone());
		updateForm.setId(user.getId());
		
		model.addAttribute("updateForm", updateForm);
		ChangePasswordDTO pwForm = new ChangePasswordDTO();
		pwForm.setId(user.getId());
		model.addAttribute("pwForm", pwForm);
		
		Set<SOrder> orders = userService.findById(user.getId()).getOrders();
		model.addAttribute("orders", orders);
		return "/client/profile";
	}

}
