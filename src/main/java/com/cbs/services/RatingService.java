package com.cbs.services;

import com.cbs.model.Rating;
import com.cbs.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }

    public void saveRate(Rating rating){
        ratingRepository.save(rating);
    }

    public boolean existRatingByOrder(long id){
        return ratingRepository.existsByOrder_Id(id);
    }

    public Page<Rating> getRatingByMovie(long id, Pageable pageable){
        return ratingRepository.findRatingByMovie_IdOrderByIdDesc(id, pageable);
    }
}
