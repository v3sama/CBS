package com.cbs.controllers;

import com.cbs.model.Actor;
import com.cbs.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.util.StringUtils;

import javax.validation.Valid;

@Controller
public class ActorController {

	private final ActorService actorService;

	@Autowired
	public ActorController(ActorService actorService) {
		this.actorService = actorService;
	}

	@RequestMapping(value = "/admin/actor", method = RequestMethod.GET)
	public String allActor(Model model) {
		model.addAttribute("actors", actorService.getAllActors());
		return "/admin/actor-list";
	}

	@RequestMapping(value = "/actor", method = RequestMethod.GET)
	public String allActorUser(@RequestParam(required = false, defaultValue = "1") Integer page, Model model) {
		Page<Actor> pages = actorService.getAllActorsPage(page);
		model.addAttribute("allActor", pages);
		return "/actor";
	}

	@RequestMapping(value = "/admin/delete/actor", method = RequestMethod.GET)
	public String deleteActor(@RequestParam Long actorId) {
		actorService.deleteActorByID(actorId);
		return "redirect:/admin/actor";
	}

	@RequestMapping(value = "/admin/edit/actor", method = RequestMethod.GET, params = { "actorId" })
	public String editActor(@RequestParam Long actorId, Model model) {
		model.addAttribute("actor", actorService.getActorByID(actorId));
		return "/admin/add/actor";
	}

	@RequestMapping(value = "/admin/add/actor", method = RequestMethod.GET)
	public String addActor(Model model) {
		model.addAttribute("actor", new Actor());
		return "/admin/add/actor";
	}

	@RequestMapping(value = "/admin/add/actor", method = RequestMethod.POST, params = { "name" })
	public String addActor(@Valid Actor actor, BindingResult bindingResult, Model model, @RequestParam String name) {

		if (name.trim().isEmpty()) {
			model.addAttribute("error", "Actor name must not be blank.");
			return "admin/add/actor";
		}

		if (bindingResult.hasErrors()) {
			return "admin/add/actor";
		}
		try {
			actorService.addActor(actor);
		} catch (Exception e) {
			model.addAttribute("error", "Actor name must be uinique.");
			return "/admin/add/actor";
		}
		return "redirect:/admin/actor";
	}

	@RequestMapping(value = "/details/actor", method = RequestMethod.GET)
	public String getActor(@RequestParam Long actorId, Model model) {
		model.addAttribute("actor", actorService.getActorByID(actorId));
		return "/details/actor";
	}

}
