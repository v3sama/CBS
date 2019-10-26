package com.cbs.repository;

import com.cbs.model.SOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface OrderRepository extends JpaRepository<SOrder, Long> {
  SOrder findSOrderById(long id);

  boolean existsSOrderById(long id);
}
