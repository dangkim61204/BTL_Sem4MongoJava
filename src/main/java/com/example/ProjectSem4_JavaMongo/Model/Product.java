package com.example.ProjectSem4_JavaMongo.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "products")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Product {
    @Id
    private String productId;
    private String productName;
    private String image;
    private double price;
    private double salePrice;
    private String desciption;

    @Field("category_id") // Tên trường trong MongoDB
    private String categoryId;

}
