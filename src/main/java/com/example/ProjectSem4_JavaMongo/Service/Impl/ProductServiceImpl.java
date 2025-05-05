package com.example.ProjectSem4_JavaMongo.Service.Impl;

import com.example.ProjectSem4_JavaMongo.Model.Product;
import com.example.ProjectSem4_JavaMongo.Repository.ProductRepository;
import com.example.ProjectSem4_JavaMongo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    public ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(String id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public Product add(Product product) {
        return productRepository.save(product);
    }

    @Override
    public Product update(Product product) {
        return productRepository.save(product);
    }

    @Override
    public void delete(String id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean findByCategoryId(String categoryId) {
        return productRepository.findByCategoryId(categoryId.trim()) != null;
    }

    @Override
    public Page<Product> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,10);
        return this.productRepository.findAll(pageable);
    }

    @Override
    public List<Product> searchNameProduct(String key) { return productRepository.searchNameProduct(key);
    }

    @Override
    public Page<Product> searchProductpage(String key, Integer pageNo) {
        List list = this.searchNameProduct(key);

        Pageable pageable = PageRequest.of(pageNo-1,8);

        Integer start = (int) pageable.getOffset();

        Integer end =(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size():pageable.getOffset() + pageable.getPageSize());

        list = list.subList(start, end);

        return new PageImpl<Product>(list, pageable, this.searchNameProduct(key).size());
    }




}

