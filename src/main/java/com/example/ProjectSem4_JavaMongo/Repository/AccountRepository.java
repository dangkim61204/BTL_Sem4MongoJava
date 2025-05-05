package com.example.ProjectSem4_JavaMongo.Repository;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface AccountRepository extends MongoRepository<Account, String> {
    @Query("{ 'userName': { $regex: ?0, $options: 'i' } }")
    List<Account> searchAccount(String key);

    Account findByUserName(String userName);
    Account findByEmail(String email);
}
