package com.example.ProjectSem4_JavaMongo.Service.Impl;

import com.example.ProjectSem4_JavaMongo.Model.Category;
import com.example.ProjectSem4_JavaMongo.Repository.CategoryRepository;
import com.example.ProjectSem4_JavaMongo.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    public CategoryRepository categoryRepository;


    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Category getById(String id) {
        return categoryRepository.findById(id).orElse(null);
    }

    @Override
    public Category add(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(String id) {
        categoryRepository.deleteById(id);

    }

    @Override
    public boolean findsByName(String name) {
        return categoryRepository.findByName(name.trim()) != null;
    }

    @Override
    public Page<Category> getAll(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,10);
        return this.categoryRepository.findAll(pageable);
    }
}
