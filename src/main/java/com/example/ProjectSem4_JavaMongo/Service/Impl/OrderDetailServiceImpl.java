package com.example.ProjectSem4_JavaMongo.Service.Impl;

import com.example.ProjectSem4_JavaMongo.Model.OrderDetail;
import com.example.ProjectSem4_JavaMongo.Repository.OrderDetailRepository;
import com.example.ProjectSem4_JavaMongo.Service.OrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> findByOrderId(String orderId) {
            return orderDetailRepository.findByOrderId(orderId);
    }

    @Override
    public void save(OrderDetail orderDetail) {
        orderDetailRepository.save(orderDetail);
    }

    @Override
    public void deleteById(String id) {
        orderDetailRepository.deleteById(id);
    }
}
