package com.cbs.services;

import com.cbs.model.Province;
import com.cbs.repository.ProvinceRepository;
import com.cbs.services.ProvinceService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProvinceService {
	private final ProvinceRepository provinceRepository;

    @Autowired
    public ProvinceService(ProvinceRepository provinceRepository) {
        this.provinceRepository = provinceRepository;
    }
    
    public List<Province> getAllProvince() {
        return provinceRepository.findAll();
    }

    public Province getProvinceByID(Long id) {
        return provinceRepository.getOne(id);
    }

    public void deleteProvinceByID(Long id) {
        if(getProvinceByID(id) != null) {
            provinceRepository.deleteById(id);
        }
    }

    public void addProvince(Province province) {
        provinceRepository.saveAndFlush(province);
    }

	public Province getProvinceById(Long id) {
		
		return provinceRepository.getOne(id);
	}
	
}
