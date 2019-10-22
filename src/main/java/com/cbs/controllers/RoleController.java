package com.cbs.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cbs.model.Role;
import com.cbs.services.RoleService;
import com.cbs.services.RoleService;

@Controller
public class RoleController {
	private final RoleService roleService;

    @Autowired
    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @RequestMapping(value = "/admin/role", method = RequestMethod.GET)
    public String allRole(Model model, Long cinemaId) {
        model.addAttribute("roles", roleService.getAllRole());
        return "/admin/role-list";
    }

    @RequestMapping(value = "/admin/add/role", method = RequestMethod.GET)
    public String addRole(Model model) {
        model.addAttribute("role", new Role());
        return "/admin/add/role";
    }

    @RequestMapping(value = "/admin/add/role", method = RequestMethod.POST)
    public String addRole(@Valid Role role, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/role";
        }
        roleService.addRole(role);
        return "redirect:/admin/role";
    }


    @RequestMapping(value = "/admin/edit/role", method = RequestMethod.GET)
    public String editRole(@RequestParam Long id, Model model) {
        model.addAttribute("role", roleService.getRoleById(id));
        return "/admin/add/role";
    }

    @RequestMapping(value = "/admin/delete/role", method = RequestMethod.GET, params = {"id"})
    public String deleteRole(@RequestParam Long id, Model model) {
        roleService.deleteRoleByID(id);
        return "redirect:/admin/role";
    }
}
