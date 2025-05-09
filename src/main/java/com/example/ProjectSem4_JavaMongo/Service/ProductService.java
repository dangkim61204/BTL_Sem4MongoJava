package com.example.ProjectSem4_JavaMongo.Service;

import com.example.ProjectSem4_JavaMongo.Model.Category;
import com.example.ProjectSem4_JavaMongo.Model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ProductService {
    List<Product> getAll();
    Product getById(String id);
    Product add(Product product);
    Product update(Product product);
    void delete(String id);
    boolean findByCategoryId(String categoryId);
    Page<Product> getAll(Integer pageNo);

    //tim kiem theo name
    List<Product> searchNameProduct(String key);
    Page<Product> searchProductpage(String key, Integer pageNo);


    List<Product> findProductById(String id);

    //tim khoang gia
//    List<Product> findByPriceBetween(Double fromPrice, Double toPrice);
    List<Product> searchPriceShopfrom(double priceFrom);
    List<Product> searchPriceShopto( double priceTo);
    List<Product> searchPriceShop(double priceFrom, double priceTo);
    Page<Product>  searchPriceShopfrom(double priceFrom, Integer pageNo);
    Page<Product>  searchPriceShopto(double priceTo, Integer pageNo);
    Page<Product>  searchPriceShop(double priceFrom, double priceTo, Integer pageNo);


    Page<Product> findAll(int page, int size, Sort sort);
    Page<Product> findAll(int page, int size);
}
