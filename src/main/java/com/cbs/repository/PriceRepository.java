package com.cbs.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.cbs.model.Movie;
import com.cbs.model.Payment;
import com.cbs.model.Price;
import com.cbs.model.Province;
@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

//    @Query("select p from price p where u.movie_id = ?1 and u.is_holiday = ?2 and is_vip = ?3")
//    List<Price> findPriceByMovieIdAndIsHolidayAndIsVIP(long movie_id, boolean isHoliday, boolean isVIP);
    List<Price> findPriceByMovieIdAndIsHoliday(long movie_id, boolean isHoliday);


}
