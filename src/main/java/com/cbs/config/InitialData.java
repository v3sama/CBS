package com.cbs.config;

import com.cbs.model.*;
import com.cbs.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
public class InitialData implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	FormatTypeRepository formatTypeRepository;

	@Autowired
	MovieRepository movieRepository;

	@Autowired
	GenreRepository genreRepository;

	@Autowired
	ActorRepository actorRepository;



	@Override
	public void run(String... args) throws Exception {

//		FormatType formatType = new FormatType();
//		formatType.setName("2D");
//		formatTypeRepository.save(formatType);
//
//		FormatType formatType1 = new FormatType();
//		formatType1.setName("3D");
//		formatTypeRepository.save(formatType1);
//
//		Genre genre1 = new Genre();
//		genre1.setName("Comedy");
//		genreRepository.save(genre1);
//
//		Genre genre2 = new Genre();
//		genre2.setName("Horror");
//		genreRepository.save(genre2);
//
//		Genre genre3 = new Genre();
//		genre3.setName("Romance");
//		genreRepository.save(genre3);
//
//		Genre genre4 = new Genre();
//		genre4.setName("Drama");
//		genreRepository.save(genre4);
//
//		List<Actor> actors = new ArrayList<>();
//		Actor ac1 = new Actor("Joaquin Phoenix");
//		actors.add(ac1);
//
//		Actor ac2 = new Actor("Robert De Niro");
//		actors.add(ac2);
//
//		Actor ac3 = new Actor("Zazie Beetz");
//		actors.add(ac3);
//
//		Actor ac4 = new Actor("Frances Conroy");
//		actors.add(ac4);
//		actorRepository.saveAll(actors);

		System.out.println("finish");

	}
}