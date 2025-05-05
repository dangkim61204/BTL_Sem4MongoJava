package com.example.ProjectSem4_JavaMongo.Service;

import com.example.ProjectSem4_JavaMongo.Model.Category;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {
    List<Category> getAll();
    Category getById(String id);
    Category add(Category category);
    Category update(Category category);
    void delete(String id);
    boolean findsByName(String name);
    Page<Category> getAll(Integer pageNo);
}
