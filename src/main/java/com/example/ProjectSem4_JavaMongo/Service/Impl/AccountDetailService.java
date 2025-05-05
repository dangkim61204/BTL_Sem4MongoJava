package com.example.ProjectSem4_JavaMongo.Service.Impl;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Model.AccountRole;
import com.example.ProjectSem4_JavaMongo.Repository.AccountRepository;
import com.example.ProjectSem4_JavaMongo.Security.AccountDetail;
import com.example.ProjectSem4_JavaMongo.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class AccountDetailService implements UserDetailsService {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    public AccountDetailService (AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByUserName(username);
        if(account == null){
            throw new UsernameNotFoundException("sai");
        }
        Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<>();
        Set<AccountRole> roles = account.getAccountRoles();
        for (AccountRole accountRole : roles){
            grantedAuthoritySet.add(new SimpleGrantedAuthority(accountRole.getRole().getRoleName()));
        }
        return new AccountDetail(account, grantedAuthoritySet);
    }
}
