package com.example.ProjectSem4_JavaMongo.Repository;

import com.example.ProjectSem4_JavaMongo.Model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RoleRepository extends MongoRepository<Role, String> {
}
