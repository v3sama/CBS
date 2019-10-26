package com.cbs.repository;

import com.cbs.model.CardInformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<CardInformation, Long> {

    List<CardInformation> findCardInformationByMember_Id(long id);
}
