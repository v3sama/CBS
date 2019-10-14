package com.cbs.services;

import com.cbs.model.Row;
import com.cbs.repository.RowRepository;
import com.cbs.services.RowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RowService {

    private final RowRepository rowRepository;


    @Autowired
    public RowService(RowRepository rowRepository) {
        this.rowRepository = rowRepository;
    }

    public Row addRow(Row row) {
        return rowRepository.saveAndFlush(row);
    }


    public List<Row> getAllRow() {
        return rowRepository.findAll();
    }
}
