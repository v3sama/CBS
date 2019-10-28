package com.cbs.controllers;

import com.cbs.dto.MovieCreationDTO;
import com.cbs.model.Movie;
import com.cbs.model.MyReponse;
import com.cbs.services.ActorService;
import com.cbs.services.FormatTypeService;
import com.cbs.services.MovieService;
import com.cbs.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MovieController {

	private final MovieService movieService;

	private final ActorService actorService;

	private final GenreService genreService;

	private final FormatTypeService formatTypeService;

	@Autowired
	public MovieController(MovieService movieService, ActorService actorService, GenreService genreService,
			FormatTypeService formatTypeService) {
		this.movieService = movieService;
		this.actorService = actorService;
		this.genreService = genreService;
		this.formatTypeService = formatTypeService;
	}

	@RequestMapping(value = "/admin/movie", method = RequestMethod.GET)
	public String allMovies(Model model) {
		model.addAttribute("movies", movieService.getAllMovies());
		return "/admin/movie-list";
	}

	@RequestMapping(value = "/admin/movie", method = RequestMethod.GET, params = { "actorId" })
	public String allMovies(Model model, Long actorId) {

		model.addAttribute("movies", actorService.getActorByID(actorId).getMovies());
		return "/admin/movie-list";
	}

//    @RequestMapping(value = "/movie", method = RequestMethod.GET)
	public String allMovieUser(@RequestParam(defaultValue = "1", required = false) Integer page, Model model) {
		Page<Movie> pages = movieService.getAllMoviesPage(page);
		model.addAttribute("allMovie", pages);
		model.addAttribute("movies", movieService.getAllMovies());
		return "/movie";
	}

//    @RequestMapping(value = "/movie", method = RequestMethod.GET, params = {"movieTitle"})
	public String searchMovie(@RequestParam String movieTitle,
			@RequestParam(defaultValue = "1", required = false) Integer page, Model model) {
		Page<Movie> searchResult = movieService.searchByTittle(movieTitle, page);
		model.addAttribute("allMovie", searchResult);
		return "/movie";
	}

	@RequestMapping(value = "/admin/add/movie", method = RequestMethod.GET)
	public String addMovie(Model model) {
		MovieCreationDTO movieDTO = new MovieCreationDTO();
		movieDTO.getMovie().setStatus(true);
		model.addAttribute("movieForm", movieDTO);
		model.addAttribute("formats", formatTypeService.getAllFormatType());
		model.addAttribute("actors", actorService.getAllActors());
		model.addAttribute("genres", genreService.getAllGenre());
		return "/admin/add/movie";
	}

	@RequestMapping(value = "/admin/add/movie", method = RequestMethod.POST)
	public String addMovie(@ModelAttribute("movieForm") MovieCreationDTO movieForm, BindingResult bindingResult,
			Model model, HttpServletRequest request) {

//		if (name.trim().isEmpty()) {
//			model.addAttribute("error", "Tile must not be blank.");
//			return "/admin/add/movie";
//		}

		if (bindingResult.hasErrors()) {
			return "/admin/add/movie";
		}

		Movie movie = movieForm.getMovie();
		movieService.addMovie(movie);
		try {
			this.doUpload(request, movie, movieForm);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			movieService.addMovie(movie);
		} catch (Exception e) {
			model.addAttribute("error", "Title must be unique.");
			return "/admin/add/movie";
		}
		return "redirect:/admin/movie";
	}

	private void doUpload(HttpServletRequest request, Movie movie, MovieCreationDTO movieForm) {
		// Thư mục gốc upload file.
		String uploadRootPath = request.getServletContext().getRealPath("upload");
		System.out.println("uploadRootPath=" + uploadRootPath);

		File uploadRootDir = new File(uploadRootPath);
		// Tạo thư mục gốc upload nếu nó không tồn tại.
		if (!uploadRootDir.exists()) {
			uploadRootDir.mkdirs();
		}
		MultipartFile[] fileDatas = { movieForm.getThumbnail(), movieForm.getImage() };
		//
		List<File> uploadedFiles = new ArrayList<File>();
		List<String> failedFiles = new ArrayList<String>();
		int i = 0;
		for (MultipartFile fileData : fileDatas) {

			// Tên file gốc tại Client.
			String name = fileData.getOriginalFilename();
			System.out.println("Client File Name = " + name);

			if (name != null && name.length() > 0) {
				try {
					// Tạo file tại Server.
					File serverPath = new File("C:" + File.separator + File.separator + "Uploads" + File.separator
							+ "images" + File.separator + "movies" + File.separator + movie.getId());
					if (!serverPath.exists())
						serverPath.mkdirs();

					// File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator +
					// name);
					File serverFile = new File(serverPath.getPath() + File.separator + name);

					BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
					stream.write(fileData.getBytes());
					stream.close();
					//
					uploadedFiles.add(serverFile);
					System.out.println("Write file: " + serverFile);
					if (i == 0)
						movie.setThumbnail(serverFile.getPath());
					else
						movie.setImage(serverFile.getPath());
					i++;
				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
					failedFiles.add(name);
				}
			}
		}

	}

	@RequestMapping(value = "/admin/delete/movie", method = RequestMethod.GET, params = { "movieId" })
	public String deleteMovie(@RequestParam Long movieId, Model model) {
		movieService.deleteMovieByID(movieId);
		return "redirect:/admin/movie";
	}

	@RequestMapping(value = "/admin/edit/movie", method = RequestMethod.GET, params = { "movieId" })
	public String editMovie(@RequestParam Long movieId, Model model) {
		MovieCreationDTO movieDTO = new MovieCreationDTO();
		movieDTO.setMovie(movieService.getMovieByID(movieId));

		model.addAttribute("movieForm", movieDTO);
		model.addAttribute("formats", formatTypeService.getAllFormatType());
		model.addAttribute("actors", actorService.getAllActors());
		model.addAttribute("genres", genreService.getAllGenre());
		return "/admin/add/movie";
	}

	@RequestMapping(value = "/admin/add/genre_to_movie", method = RequestMethod.GET, params = { "movieId" })
	public String addGenres(@RequestParam Long movieId, Model model) {
		model.addAttribute("allGenres", genreService.getAllGenre());
		model.addAttribute("movie", movieService.getMovieByID(movieId));
		return "/admin/add/genre_to_movie";
	}

	@RequestMapping(value = "/admin/add/genre_to_movie", method = RequestMethod.POST)
	public String addGenres(@Valid Movie movie, Model model, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "error";
		}
		movieService.addMovie(movie);
		return "redirect:/admin/movie";
	}

	@RequestMapping(value = "/details/movie", method = RequestMethod.GET)
	public String getMovie(@RequestParam Long movieId, Model model) {
		model.addAttribute("movie", movieService.getMovieByID(movieId));
		return "/details/movie";
	}

	@RequestMapping(value = "/admin/add/actor_to_movie", method = RequestMethod.GET, params = { "movieId" })
	public String addActors(@RequestParam Long movieId, Model model) {
		model.addAttribute("allActors", actorService.getAllActors());
		model.addAttribute("movie", movieService.getMovieByID(movieId));
		return "/admin/add/actor_to_movie";
	}

	@RequestMapping(value = "/admin/add/actor_to_movie", method = RequestMethod.POST)
	public String addActors(@Valid Movie movie, Model model, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return "error";
		}
		movieService.addMovie(movie);
		return "redirect:/admin/movie";
	}

	private void validateImage(MultipartFile image) {
		if (!image.getContentType().equals("image/jpeg")) {
			throw new RuntimeException("Only JPG images are accepted");
		}
	}

}