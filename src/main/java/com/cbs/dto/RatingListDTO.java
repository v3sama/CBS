package com.cbs.dto;

import java.util.List;

public class RatingListDTO {
    int page;
    int totalPage;
    long totalElememt;
    int size;
    List<RatingDTO> reviews;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }

    public long getTotalElememt() {
        return totalElememt;
    }

    public void setTotalElememt(long totalElememt) {
        this.totalElememt = totalElememt;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<RatingDTO> getReviews() {
        return reviews;
    }

    public void setReviews(List<RatingDTO> reviews) {
        this.reviews = reviews;
    }
}
