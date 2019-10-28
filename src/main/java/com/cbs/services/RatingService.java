package com.cbs.services;

import com.cbs.model.Rating;
import com.cbs.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
