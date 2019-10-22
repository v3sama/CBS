package com.cbs.controllers;

import com.cbs.model.Discount;
import com.cbs.model.User;
import com.cbs.services.DiscountService;
import com.cbs.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


import javax.validation.Valid;

@Controller
public class UserController {

    private final UserService userService;
    private final DiscountService discountService;

    @Autowired
    public UserController(UserService userService, DiscountService discountService) {
        this.userService = userService;
        this.discountService = discountService;
    }

    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    public String allUser(Model model) {
        model.addAttribute("users", userService.findAll());
       // return userService.findAll();
        return "/admin/user-list";
    }

    @RequestMapping(value = "/admin/edit/user", method = RequestMethod.GET, params = {"id"})
    public String getUserEdit(@RequestParam Long id, Model model) {
        model.addAttribute("user", userService.findById(id));
        return "/admin/edit/user";
    }
    
    @RequestMapping(value = "/admin/add/user", method = RequestMethod.GET)
    public String addUser(Model model) {
    	model.addAttribute("user", new User());
        return "/admin/add/user";
    }

    @RequestMapping(value = "/admin/add/user", method = RequestMethod.POST)
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {
    	if (bindingResult.hasErrors()) {
            return "redirect:/admin/user";
        }
    	user.setActive(true);
    	user.setPassword("123456");
        userService.add(user);
        return "/admin/user-list";
    }
    
    @RequestMapping(value = "/admin/edit/user", method = RequestMethod.POST)
    public String editUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/user";
        }
        userService.update(user);
        return "redirect:/admin/user-list";
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
}
