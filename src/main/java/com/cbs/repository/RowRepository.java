package com.cbs.repository;

import com.cbs.model.Row;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RowRepository extends JpaRepository<Row, Long> {
}
