package com.cbs.dto;

import org.springframework.web.multipart.MultipartFile;

import com.cbs.model.Movie;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieCreationDTO {
	private Movie movie = new Movie();

	// Upload files.
	private MultipartFile thumbnail;
	private MultipartFile image;

}
