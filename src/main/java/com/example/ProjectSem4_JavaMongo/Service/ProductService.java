package com.example.ProjectSem4_JavaMongo.Service;

import com.example.ProjectSem4_JavaMongo.Model.Category;
import com.example.ProjectSem4_JavaMongo.Model.Product;
import org.springframework.data.domain.Page;
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

}
