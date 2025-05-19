package com.example.ProjectSem4_JavaMongo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "order_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderDetail {
    @Id
    private String id;
    private String orderId;
    private String productName;
    private String image;
    private String description;
    private double price;
    private int quantity;
    private double total;
}
