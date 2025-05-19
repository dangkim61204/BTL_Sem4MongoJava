package com.example.ProjectSem4_JavaMongo.Service;

import com.example.ProjectSem4_JavaMongo.Model.Order;
import com.example.ProjectSem4_JavaMongo.Model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface OrderService {
    Page<Order> getAllOrders(Pageable pageable);
    Optional<Order> getOrderById(String id);
    Boolean delete(String id);
}
