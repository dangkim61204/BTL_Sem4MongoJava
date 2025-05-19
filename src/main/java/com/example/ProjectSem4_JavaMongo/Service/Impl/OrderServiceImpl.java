package com.example.ProjectSem4_JavaMongo.Service.Impl;

import com.example.ProjectSem4_JavaMongo.Model.Order;
import com.example.ProjectSem4_JavaMongo.Model.OrderDetail;
import com.example.ProjectSem4_JavaMongo.Repository.OrderDetailRepository;
import com.example.ProjectSem4_JavaMongo.Repository.OrderRepository;
import com.example.ProjectSem4_JavaMongo.Repository.ProductRepository;
import com.example.ProjectSem4_JavaMongo.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Override
    public Page<Order> getAllOrders(Pageable pageable) {
        return orderRepository.findAll(pageable);
    }

    @Override
    public Optional<Order> getOrderById(String id) {
        return orderRepository.findById(id);
    }

    @Override
    public Boolean delete(String id) {
        try {
            // Kiểm tra Order tồn tại
            Optional<Order> orderOpt = orderRepository.findById(id);
            if (!orderOpt.isPresent()) {
                return false;
            }

            // Xóa tất cả OrderDetail liên quan
            orderDetailRepository.deleteByOrderId(id);

            // Xóa Order
            orderRepository.deleteById(id);

            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
