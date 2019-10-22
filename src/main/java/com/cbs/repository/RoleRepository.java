package com.cbs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cbs.model.Role;

//import com.cbs.model.Payment;
//import com.cbs.model.Price;
//import com.cbs.model.Province;
//import com.cbs.model.Rating;
//import com.cbs.model.Role;
//import com.cbs.model.SOrder;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

	

}
