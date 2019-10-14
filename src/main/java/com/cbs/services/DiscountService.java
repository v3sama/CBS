package com.cbs.services;

import com.cbs.model.Discount;
import com.cbs.repository.DiscountRepository;
import com.cbs.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountService  {

    private final DiscountRepository discountRepository;

    @Autowired
    public DiscountService(DiscountRepository discountRepository) {
        this.discountRepository = discountRepository;
    }

    public List<Discount> getAllDiscount() {
        return discountRepository.findAll();
    }

    public Discount getDiscountByID(Long id) {
        return discountRepository.getOne(id);
    }

    public void deleteDiscountByID(Long id) {
        if(getDiscountByID(id) != null) {
            discountRepository.deleteById(id);
        }
    }

    public void addDiscount(Discount discount) {
        discountRepository.saveAndFlush(discount);
    }
}
