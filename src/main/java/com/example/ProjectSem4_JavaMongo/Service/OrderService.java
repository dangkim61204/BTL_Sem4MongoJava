package com.example.ProjectSem4_JavaMongo.Service;

import com.example.ProjectSem4_JavaMongo.Model.Order;
import com.example.ProjectSem4_JavaMongo.Model.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {
    List<Order> getAll();
    Order getById(Integer id);
    //don hnagf
    public List<OrderDetail> getId(String id);
    //	public boolean delete_don_hang(int id);
    Boolean add(Order order);
    Boolean update(Order order);
    Boolean delete(Integer id);
    public boolean insertOrderDetail(Order order, List<OrderDetail> details );


    List<Order> searchOrder(String key);
    Page<Order> getAll(Integer pageNo);
    Page<Order>  searchOrder(String key, Integer pageNo);
}
