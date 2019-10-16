package com.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbs.model.FormatType;

@Repository
public interface FormatTypeRepository extends JpaRepository<FormatType, Long>  {

}
