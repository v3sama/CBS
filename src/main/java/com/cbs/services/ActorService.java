package com.cbs.services;

import com.cbs.model.Actor;
import com.cbs.repository.ActorRepository;
import com.cbs.services.ActorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

	
	@Value("${spring.data.rest.default-page-size}")
	private Integer pageSize;

	private final ActorRepository actorRepository;

	@Autowired
	public ActorService(ActorRepository actorRepository) {
		this.actorRepository = actorRepository;
	}

	public void addActor(Actor actor) {
		actorRepository.saveAndFlush(actor);
	}

	public List<Actor> getAllActors() {
		return actorRepository.findAll();
	}
	
	public Page<Actor> getAllActorsPage(Integer pageNumber) {
		PageRequest request =  PageRequest.of(pageNumber - 1, pageSize, Sort.Direction.ASC, "lastName");
		return actorRepository.findAll(request);
	}

	public Optional<Actor> getActorByID(Long id) {
		return actorRepository.findById(id);
	}

	public void deleteActorByID(Long id) {
		if (actorRepository.findById(id) != null) {
			actorRepository.deleteById(id);
		}
	}

}
