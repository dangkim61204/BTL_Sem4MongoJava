package com.example.ProjectSem4_JavaMongo.Service;

import com.example.ProjectSem4_JavaMongo.Model.OrderDetail;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface  OrderDetailService {
    List<OrderDetail> findByOrderId(String orderId);
    void save(OrderDetail orderDetail);
    void deleteById(String id);
}
