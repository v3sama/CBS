package com.cbs.controllers;

import com.cbs.dto.CustomUserDetail;
import com.cbs.model.Discount;
import com.cbs.model.User;
import com.cbs.services.DiscountService;
import com.cbs.services.RoleService;
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
    private final RoleService roleService;
    private CustomUserDetail loggedInUser;
	private User user;

    @Autowired
    public UserController(UserService userService, DiscountService discountService, RoleService roleService) {
        this.userService = userService;
        this.discountService = discountService;
        this.roleService = roleService;
       
    }

    //Admin - List
    @RequestMapping(value = "/admin/user", method = RequestMethod.GET)
    public String allUser(Model model) {
        model.addAttribute("users", userService.findAll());
       // return userService.findAll();
        return "/admin/user-list";
    }
    
   

    //Edit - Admin User
    @RequestMapping(value = "/admin/edit/user", method = RequestMethod.GET, params = {"id"})
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

 
    //Add User-Role
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
    
    //client - List - gia bo co session id - xong roi Loc xoa gium anh cai List duoi nhe
    
    @RequestMapping(value = "/client/view-user", method = RequestMethod.GET)
    public String allClientUser(Model model) {
        model.addAttribute("users", userService.findAll());
        return "/client/view-user";
    }
    
    //update-client user
    
    @RequestMapping(value = "/client/update-user", method = RequestMethod.GET, params = {"id"})
    public String getClientUserEdit(@RequestParam Long id, Model model) {
    	 loggedInUser = (CustomUserDetail) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
         user = loggedInUser.getUser();
        model.addAttribute("user", userService.findById(id));
 
        return "/client/update-user";
    }
    
    @RequestMapping(value = "/client/update-user", method = RequestMethod.POST)
    public String editClientUser(@Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/client/index";
        }
        userService.update(user);
        return "redirect:/client/view-user";
    }
    
    
    @RequestMapping(value = "/profile", method = RequestMethod.GET)
    public String viewProfile(Model model) {
    	model.addAttribute("user",	user);
        return "/client/profile";
    }
    
    
   //Update - Reset Password
//    @RequestMapping(value="/updateUserInfo", method=RequestMethod.POST)
//    public ResponseEntity profileInfo(
//                @RequestBody HashMap<String, Object> mapper
//            ) throws Exception{
//
//        int id = (Integer) mapper.get("id");
//        String email = (String) mapper.get("email");
//        String firstName = (String) mapper.get("firstName");
//        String lastName = (String) mapper.get("lastName");
//        String newPassword = (String) mapper.get("newPassword");
//        String currentPassword = (String) mapper.get("currentPassword");
//
//        User currentUser = userService.findById(Long.valueOf(id));
//
//        if(currentUser == null) {
//            throw new Exception ("User not found");
//        }
//
//        if(userService.findByEmail(email) != null) {
//            if(userService.findByEmail(email).getId() != currentUser.getId()) {
//                return new ResponseEntity("Email not found!", HttpStatus.BAD_REQUEST);
//            }
//        }
//
//        SecurityConfig securityConfig = new SecurityConfig();
//
//
//            BCryptPasswordEncoder passwordEncoder = SecurityUtility.passwordEncoder();
//            String dbPassword = currentUser.getPassword();
//
//            if(null != currentPassword)
//            if(passwordEncoder.matches(currentPassword, dbPassword)) {
//                if(newPassword != null && !newPassword.isEmpty() && !newPassword.equals("")) {
//                    currentUser.setPassword(passwordEncoder.encode(newPassword));
//                }
//                currentUser.setEmail(email);
//            } else {
//                return new ResponseEntity("Incorrect current password!", HttpStatus.BAD_REQUEST);
//            }
//        currentUser.setFirstName(firstName);
//        currentUser.setLastName(lastName);
//        userService.save(currentUser);
//
//        return new ResponseEntity("Update Success", HttpStatus.OK);
//    }
    
}
