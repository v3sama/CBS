package com.cbs.repository;

import com.cbs.model.Actor;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository

public interface ActorRepository extends JpaRepository<Actor, Long> {

//	@Query(value = "SELECT * FROM Actor WHERE name = ?1", nativeQuery = true)
	Actor findActorByName(String name);
}
