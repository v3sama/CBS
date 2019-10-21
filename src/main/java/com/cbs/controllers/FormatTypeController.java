package com.cbs.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cbs.model.FormatType;
import com.cbs.services.FormatTypeService;

@Controller
public class FormatTypeController {
	private final FormatTypeService formatTypeService;

    @Autowired
    public FormatTypeController(FormatTypeService formatTypeService) {
        this.formatTypeService = formatTypeService;
    }

    @RequestMapping(value = "/admin/formatType", method = RequestMethod.GET)
    public String allFormatType(Model model, Long cinemaId) {
        model.addAttribute("formatTypes", formatTypeService.getAllFormatType());
        return "/admin/format-list";
    }

    @RequestMapping(value = "/admin/add/formatType", method = RequestMethod.GET)
    public String addFormatType(Model model) {
        model.addAttribute("formatType", new FormatType());
        return "/admin/add/formatType";
    }

    @RequestMapping(value = "/admin/add/formatType", method = RequestMethod.POST)
    public String addFormatType(@Valid FormatType formatType, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "redirect:/admin/formatType";
        }
        formatTypeService.addFormatType(formatType);
        return "redirect:/admin/formatType";
    }


    @RequestMapping(value = "/admin/edit/formatType", method = RequestMethod.GET)
    public String editFormatType(@RequestParam Long id, Model model) {
        model.addAttribute("formatType", formatTypeService.getFormatTypeById(id));
        return "/admin/add/formatType";
    }

    @RequestMapping(value = "/admin/delete/formatType", method = RequestMethod.GET, params = {"id"})
    public String deleteFormatType(@RequestParam Long id, Model model) {
        formatTypeService.deleteFormatTypeByID(id);
        return "redirect:/admin/formatType";
    }
}
