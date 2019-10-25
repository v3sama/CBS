package com.cbs.services;

import com.cbs.model.Cinema;
import com.cbs.model.Payment;
import com.cbs.repository.CinemaRepository;
import com.cbs.repository.PaymentRepository;
import com.cbs.services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentService {

    @Autowired
    PaymentRepository paymentRepository;

    public void savePayment(Payment payment){
        paymentRepository.save(payment);
    }
}
