package com.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbs.model.Province;
@Repository
public interface ProvinceRepository extends JpaRepository<Province, Long> {

}
