package com.example.ProjectSem4_JavaMongo.Service.Impl;

import com.example.ProjectSem4_JavaMongo.Model.Product;
import com.example.ProjectSem4_JavaMongo.Repository.ProductRepository;
import com.example.ProjectSem4_JavaMongo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
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

    @Override
    public List<Product> findProductById(List<String> ids) {
        return productRepository.findByCategoryIds(ids);
    }




    @Override
    public List<Product> searchPriceShopfrom(double priceFrom) {
        return productRepository.searchPriceShopfrom(priceFrom);
    }

    @Override
    public List<Product> searchPriceShopto(double priceTo) {
        return productRepository.searchPriceShopto(priceTo);
    }

    @Override
    public List<Product> searchPriceShop(double priceFrom, double priceTo) {
        return productRepository.searchPriceShop(priceFrom, priceTo);
    }

    @Override
    public Page<Product> searchPriceShopfrom(double priceFrom, Integer pageNo) {
        List list = this.searchPriceShopfrom(priceFrom);

        Pageable pageable = PageRequest.of(pageNo-1,5);

        Integer start = (int) pageable.getOffset();

        Integer end =(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size():pageable.getOffset() + pageable.getPageSize());

        list = list.subList(start, end);

        return new PageImpl<Product>(list, pageable, this.searchPriceShopfrom(priceFrom).size());
    }

    @Override
    public Page<Product> searchPriceShopto(double priceTo, Integer pageNo) {
        List list = this.searchPriceShopto(priceTo);

        Pageable pageable = PageRequest.of(pageNo-1,5);

        Integer start = (int) pageable.getOffset();

        Integer end =(int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size():pageable.getOffset() + pageable.getPageSize());

        list = list.subList(start, end);

        return new PageImpl<Product>(list, pageable, this.searchPriceShopto(priceTo).size());
    }

    @Override
    public Page<Product> searchPriceShop(double priceFrom, double priceTo, Integer pageNo) {
        List list = this.searchPriceShop(priceFrom, priceTo);

        Pageable pageable = PageRequest.of(pageNo - 1, 5);

        Integer start = (int) pageable.getOffset();

        Integer end = (int) ((pageable.getOffset() + pageable.getPageSize()) > list.size() ? list.size() : pageable.getOffset() + pageable.getPageSize());

        list = list.subList(start, end);

        return new PageImpl<Product>(list, pageable, this.searchPriceShop(priceFrom, priceTo).size());
    }

    @Override
    public Page<Product> findAll(int page, int size, Sort sort) {
            Pageable pageable = PageRequest.of(page, size, sort);
            return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findAll(int page, int size) {
            Pageable pageable = PageRequest.of(page, size);
            return  productRepository.findAll(pageable);
    }


}

