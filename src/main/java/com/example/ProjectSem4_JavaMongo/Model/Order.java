package com.example.ProjectSem4_JavaMongo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Document(collection = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    private String id;

    @DBRef
    private Account account;

    private String fullname;
    private String email;
    private String phone;
    private String address;

    private Date orderDate;
    private double totalAmount;

    @DBRef
    private List<OrderDetail> orderDetails = new ArrayList<>();
    @DBRef
    private Account account1; // Giữ nguyên dạng object
}