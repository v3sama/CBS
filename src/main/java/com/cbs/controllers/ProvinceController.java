package com.cbs.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.cbs.model.Province;
import com.cbs.services.ProvinceService;

@Controller
public class ProvinceController {
	private final ProvinceService provinceService;

    @Autowired
    public ProvinceController(ProvinceService provinceService) {
        this.provinceService = provinceService;
    }

    @RequestMapping(value = "/admin/province", method = RequestMethod.GET)
    public String allProvince(Model model, Long cinemaId) {
        model.addAttribute("provinces", provinceService.getAllProvince());
        return "/admin/province-list";
    }

    @RequestMapping(value = "/admin/add/province", method = RequestMethod.GET)
    public String addProvince(Model model) {
        model.addAttribute("province", new Province());
        return "/admin/add/province";
    }

    @RequestMapping(value = "/admin/add/province", method = RequestMethod.POST)
    public String addProvince(@Valid Province province, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()  ) {
            return "redirect:/admin/province";
        }
        if(!provinceService.isUnique(province.getName())) {
        	model.addAttribute("msg", "name already exist");
        	return "redirect:/admin/province";
        }
       
        provinceService.addProvince(province);
        return "redirect:/admin/province";
    }


    @RequestMapping(value = "/admin/edit/province", method = RequestMethod.GET)
    public String editProvince(@RequestParam Long id, Model model) {
        model.addAttribute("province", provinceService.getProvinceByID(id));
        return "/admin/add/province";
    }

    @RequestMapping(value = "/admin/delete/province", method = RequestMethod.GET, params = {"id"})
    public String deleteProvince(@RequestParam Long id, Model model) {
        provinceService.deleteProvinceByID(id);
        return "redirect:/admin/province";
    }
}
