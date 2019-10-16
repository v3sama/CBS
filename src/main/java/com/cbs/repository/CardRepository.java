package com.cbs.repository;

import com.cbs.model.CardInformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface CardRepository extends JpaRepository<CardInformation, Long> {
}
