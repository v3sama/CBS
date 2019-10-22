package com.cbs.dto;

import com.cbs.model.Movie;
import com.fasterxml.jackson.annotation.JsonView;

import java.io.Serializable;
import java.util.List;

public class MovieIndexDTO implements Serializable {
    String msg;

    String movie_title;
    String movie_image;
    String movie_thumbnail;
    String trailer_link;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getMovie_title() {
        return movie_title;
    }

    public void setMovie_title(String movie_title) {
        this.movie_title = movie_title;
    }

    public String getMovie_image() {
        return movie_image;
    }

    public void setMovie_image(String movie_image) {
        this.movie_image = movie_image;
    }

    public String getMovie_thumbnail() {
        return movie_thumbnail;
    }

    public void setMovie_thumbnail(String movie_thumbnail) {
        this.movie_thumbnail = movie_thumbnail;
    }

    public String getTrailer_link() {
        return trailer_link;
    }

    public void setTrailer_link(String trailer_link) {
        this.trailer_link = trailer_link;
    }
}
