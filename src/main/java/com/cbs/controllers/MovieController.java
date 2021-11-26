package com.cbs.controllers;

import com.cbs.dto.MovieCreationDTO;
import com.cbs.model.Movie;
import com.cbs.services.ActorService;
import com.cbs.services.FormatTypeService;
import com.cbs.services.MovieService;
import com.cbs.services.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
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

	@RequestMapping(value = "/admin/add/movie", method = RequestMethod.POST, params = { "movie.title",
			"movie.trailer_link", "movie.date_release", "movie.date_end" })
	public String addMovie(@ModelAttribute("movieForm") MovieCreationDTO movieForm, BindingResult bindingResult,
			@RequestParam("movie.title") String title, @RequestParam("movie.trailer_link") String trailerLink,
			@RequestParam("movie.date_release") String dateS, @RequestParam("movie.date_end") String dateE, Model model,
			HttpServletRequest request) throws ParseException {

		if (title.trim().isEmpty()) {
			model.addAttribute("error", "Title must not be blank.");
			MovieCreationDTO movieDTO = new MovieCreationDTO();
			movieDTO.getMovie().setStatus(true);
			model.addAttribute("movieForm", movieDTO);
			model.addAttribute("formats", formatTypeService.getAllFormatType());
			model.addAttribute("actors", actorService.getAllActors());
			model.addAttribute("genres", genreService.getAllGenre());
			return "/admin/add/movie";
		}

		if (trailerLink.trim().isEmpty()) {
			model.addAttribute("linkError", "Trailer link must not be blank.");
			MovieCreationDTO movieDTO = new MovieCreationDTO();
			movieDTO.getMovie().setStatus(true);
			model.addAttribute("movieForm", movieDTO);
			model.addAttribute("formats", formatTypeService.getAllFormatType());
			model.addAttribute("actors", actorService.getAllActors());
			model.addAttribute("genres", genreService.getAllGenre());
			return "/admin/add/movie";
		}

		System.out.println(dateE);

		Date dateStart = new SimpleDateFormat("yyyy-MM-dd").parse(dateS);
		Date dateEnd = new SimpleDateFormat("yyyy-MM-dd").parse(dateE);

		if (dateEnd.before(dateStart)) {
			model.addAttribute("dateError", "Date end must be after date release.");
			MovieCreationDTO movieDTO = new MovieCreationDTO();
			movieDTO.getMovie().setStatus(true);
			model.addAttribute("movieForm", movieDTO);
			model.addAttribute("formats", formatTypeService.getAllFormatType());
			model.addAttribute("actors", actorService.getAllActors());
			model.addAttribute("genres", genreService.getAllGenre());
			return "/admin/add/movie";
		}

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
					File serverPath = new File("D:" + File.separator + File.separator + "CBS" + File.separator + "src"
							+ File.separator + "main" + File.separator + "resources" + File.separator + "static"
							+ File.separator + "images" + File.separator + "movies" + File.separator + movie.getId());
					if (!serverPath.exists())
						serverPath.mkdirs();
					
					File serverFile = new File(serverPath + File.separator + name);
					name = getFileName(serverFile) + ".png";
					serverFile = new File(serverPath + File.separator + name);
					BufferedImage image = ImageIO.read(fileData.getInputStream());
					BufferedImage resized;
					if (i == 0)
						resized = resize(image, 600, 1440);
					else
						resized = resize(image, 318, 215);
					
					ImageIO.write(resized, "png", serverFile);
			
					uploadedFiles.add(serverFile);
					System.out.println("Write file: " + serverFile);
					if (i == 0)
						movie.setThumbnail("http://localhost:8080/images/movies/" + movie.getId() + "/" + name);
					else
						movie.setImage("http://localhost:8080/images/movies/" + movie.getId() + "/" + name);
					i++;
				} catch (Exception e) {
					System.out.println("Error Write file: " + name);
					failedFiles.add(name);
				}
			}

		}

	}

	private static BufferedImage resize(BufferedImage img, int height, int width) {
		Image tmp = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = resized.createGraphics();
		g2d.drawImage(tmp, 0, 0, null);
		g2d.dispose();
		return resized;
	}

	private static String getFileName(File file) {
        String fileName = file.getName();
        if(fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0)
        return fileName.substring(0,fileName.lastIndexOf("."));
        else return fileName;
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

//	@RequestMapping(value = "/admin/add/genre_to_movie", method = RequestMethod.GET, params = { "movieId" })
	public String addGenres(@RequestParam Long movieId, Model model) {
		model.addAttribute("allGenres", genreService.getAllGenre());
		model.addAttribute("movie", movieService.getMovieByID(movieId));
		return "/admin/add/genre_to_movie";
	}

//	@RequestMapping(value = "/admin/add/genre_to_movie", method = RequestMethod.POST)
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

//	@RequestMapping(value = "/admin/add/actor_to_movie", method = RequestMethod.GET, params = { "movieId" })
	public String addActors(@RequestParam Long movieId, Model model) {
		model.addAttribute("allActors", actorService.getAllActors());
		model.addAttribute("movie", movieService.getMovieByID(movieId));
		return "/admin/add/actor_to_movie";
	}

//	@RequestMapping(value = "/admin/add/actor_to_movie", method = RequestMethod.POST)
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