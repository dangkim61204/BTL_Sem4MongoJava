package com.example.ProjectSem4_JavaMongo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Document(collection = "orders")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Order {
    @Id
    private String id;
    private String userId; // ID của người dùng đặt hàng
    private String userName; // Tên người dùng
    private List<CartItem> items; // Danh sách sản phẩm trong đơn hàng
    private double total; // Tổng giá
    private LocalDateTime createdAt; // Thời gian tạo
}