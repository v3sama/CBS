package com.cbs.services;

import com.cbs.model.FormatType;
import com.cbs.repository.FormatTypeRepository;
import com.cbs.services.FormatTypeService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FormatTypeService {
	private final FormatTypeRepository formatTypeRepository;

    @Autowired
    public FormatTypeService(FormatTypeRepository formatTypeRepository) {
        this.formatTypeRepository = formatTypeRepository;
    }
    
    public List<FormatType> getAllFormatType() {
        return formatTypeRepository.findAll();
    }

    public FormatType getFormatTypeByID(Long id) {
        return formatTypeRepository.getOne(id);
    }

    public void deleteFormatTypeByID(Long id) {
        if(getFormatTypeByID(id) != null) {
            formatTypeRepository.deleteById(id);
        }
    }

    public void addFormatType(FormatType formatType) {
        formatTypeRepository.saveAndFlush(formatType);
    }

	public FormatType getFormatTypeById(Long id) {
		
		return formatTypeRepository.getOne(id);
	}
	
}
