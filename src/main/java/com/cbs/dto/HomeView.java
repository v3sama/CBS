package com.cbs.dto;

import com.cbs.model.Movie;
import com.fasterxml.jackson.annotation.JsonView;

import java.util.List;

public class HomeView {
    String msg;

    List<Movie> movies;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public void setMovies(List<Movie> movies) {
        this.movies = movies;
    }
}
