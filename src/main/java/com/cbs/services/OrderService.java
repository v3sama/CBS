package com.cbs.services;

import com.cbs.model.SOrder;
import com.cbs.repository.OrderRepository;
import com.cbs.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
	private final OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }
    
    public List<SOrder> getAllOrder() {
        return orderRepository.findAll();
    }

    public SOrder getOrderByID(Long id) {
        return orderRepository.getOne(id);
    }

    public void deleteOrderByID(Long id) {
        if(getOrderByID(id) != null) {
            orderRepository.deleteById(id);
        }
    }

    public void addOrder(SOrder order) {
        orderRepository.saveAndFlush(order);
    }
	
	public SOrder findOrderByID(long id){
        return orderRepository.findSOrderById(id);
    }
	
public boolean existOrderOrNot(long id){
        return orderRepository.existsSOrderById(id);
    }
	
}
