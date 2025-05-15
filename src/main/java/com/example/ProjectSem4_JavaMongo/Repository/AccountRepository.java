package com.example.ProjectSem4_JavaMongo.Repository;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AccountRepository extends MongoRepository<Account, String> {
    @Query("{ 'userName': { $regex: ?0, $options: 'i' } }")
    List<Account> searchAccount(String key);

    Account findAccountByUserName(String userName);
    Account findByEmail(String email);
}
