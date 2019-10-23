package com.cbs.dto;

import org.springframework.web.multipart.MultipartFile;

import com.cbs.model.Movie;

public class MovieCreationDTO {
	private Movie movie = new Movie();

	// Upload files.
	private MultipartFile thumbnail;
	private MultipartFile image;

	public MultipartFile getImage() {
		return image;
	}

	public void setImage(MultipartFile image) {
		this.image = image;
	}

	public MultipartFile getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(MultipartFile thumbnail) {
		this.thumbnail = thumbnail;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}
}
