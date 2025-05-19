package com.example.ProjectSem4_JavaMongo.Service;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface AccountService {
    Account findByUserName(String userName);
    Account findByEmail(String email);
    List<Account> getAll();
    Account getById(String id);
    Boolean delete(String id);
    List<Account> searchAccount(String key);
    Page<Account> getAll(Integer pageNo);
    Page<Account>  searchAccount(String key, Integer pageNo);
    Account save(Account account);
}
