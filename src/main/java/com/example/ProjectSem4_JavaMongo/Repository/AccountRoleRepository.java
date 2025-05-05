package com.example.ProjectSem4_JavaMongo.Repository;

import com.example.ProjectSem4_JavaMongo.Model.AccountRole;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AccountRoleRepository extends MongoRepository<AccountRole, String> {
}
