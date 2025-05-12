package com.example.ProjectSem4_JavaMongo.Service;

import com.example.ProjectSem4_JavaMongo.Model.Order;
import org.springframework.stereotype.Service;

@Service
public interface OrderService {
    Order saveOrder(Order order);
}
