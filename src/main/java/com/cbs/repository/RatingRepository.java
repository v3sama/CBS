package com.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.cbs.model.Payment;
import com.cbs.model.Price;
import com.cbs.model.Province;
import com.cbs.model.Rating;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    Rating findRatingByOrder_Id(long id);
    boolean existsByOrder_Id(long id);

//    @Query(value = "select r.content from rating r " +
//            "left join sorder s on r.order_id = s.id " +
//            "left join ", nativeQuery = true)
//    List<Rating> findRatingByContentEquals;

}
