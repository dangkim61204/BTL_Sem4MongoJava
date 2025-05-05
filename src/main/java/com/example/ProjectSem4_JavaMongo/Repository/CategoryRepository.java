package com.example.ProjectSem4_JavaMongo.Repository;

import com.example.ProjectSem4_JavaMongo.Model.Category;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CategoryRepository extends MongoRepository<Category, String> {
    Category findByName(String name);
}
