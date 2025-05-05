package com.example.ProjectSem4_JavaMongo.Repository;

import com.example.ProjectSem4_JavaMongo.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, String> {
    Product findByCategoryId(String categoryId); //tìm kiếm sản phẩm theo id danh muc
    //tim kiem theo ten ơ admin
    @Query("{ 'productName': { $regex: ?0, $options: 'i' } }")
    List<Product> searchNameProduct(String key);
//ktra ton tại
//    boolean findByCategory(String category);
}
