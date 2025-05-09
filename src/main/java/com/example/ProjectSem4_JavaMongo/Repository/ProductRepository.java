package com.example.ProjectSem4_JavaMongo.Repository;

import com.example.ProjectSem4_JavaMongo.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

//    //timf kieems nhieu danh muc  theo danh muc trang shop
//    @Query("{'category_id._id': { $in: ?0 }}")
//    List<Product> findByCategoryIdIn(List<String> ids);

    //timf kieems 1 danh muc  theo danh muc trang shop
    @Query("{'category_id._id': { $in: ?0 }}")
    List<Product> findCategoryId(String id);

    Page<Product> findAll(Pageable pageable);

    //tìm khoảng giá sp
    @Query("{'price': { $gte: ?0 }}")
    List<Product> searchPriceShopfrom(double priceFrom);
    @Query("{'price': { $lte: ?0 }}")
    List<Product> searchPriceShopto(double priceTo);

    @Query("{'price': { $gte: ?0, $lte: ?1 }}")
    List<Product> searchPriceShop(double priceFrom, double priceTo);
}
