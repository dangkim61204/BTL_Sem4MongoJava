package com.example.ProjectSem4_JavaMongo;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Model.AccountRole;
import com.example.ProjectSem4_JavaMongo.Model.Role;
import com.example.ProjectSem4_JavaMongo.Repository.AccountRepository;
import com.example.ProjectSem4_JavaMongo.Repository.AccountRoleRepository;
import com.example.ProjectSem4_JavaMongo.Repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

@Configuration
public class DataSeed {
    @Bean
    CommandLineRunner initData(
            RoleRepository roleRepository,
            AccountRepository accountRepository,
            AccountRoleRepository accountRoleRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {
            if (roleRepository.count() == 0) {
                Role adminRole = new Role("1", "ADMIN", true, new java.util.HashSet<>());
                Role userRole = new Role("2", "USER", true, new java.util.HashSet<>());
                roleRepository.save(adminRole);
                roleRepository.save(userRole);
            }

            if (accountRepository.count() == 0) {
                // Tạo account admin
                Account admin = new Account();
                admin.setUserName("admin");
                admin.setPassword(passwordEncoder.encode("123")); // 🔐 Mã hóa mật khẩu
                admin.setFullName("Administrator");
                admin.setEmail("admin@example.com");
                admin.setGender(true);
                admin.setBirthday(new Date());
                admin.setAddress("Hanoi");
                admin.setPhone("0123456789");
                admin.setActive(true);
                admin.setCreateDate(new Date());
                accountRepository.save(admin);

                // Tạo account user
                Account user = new Account();
                user.setUserName("user");
                user.setPassword(passwordEncoder.encode("123")); // 🔐 Mã hóa mật khẩu
                user.setFullName("Normal User");
                user.setEmail("user@example.com");
                user.setGender(false);
                user.setBirthday(new Date());
                user.setAddress("HCM");
                user.setPhone("0987654321");
                user.setActive(true);
                user.setCreateDate(new Date());
                accountRepository.save(user);

                // Gán role
                Role adminRole = roleRepository.findById("1").get();
                Role userRole = roleRepository.findById("2").get();

                AccountRole adminAR = new AccountRole(null, admin, adminRole);
                AccountRole userAR = new AccountRole(null, user, userRole);
                accountRoleRepository.save(adminAR);
                accountRoleRepository.save(userAR);

                // Liên kết lại
                admin.getAccountRoles().add(adminAR);
                user.getAccountRoles().add(userAR);
                accountRepository.save(admin);
                accountRepository.save(user);

                adminRole.getRoleAccounts().add(adminAR);
                userRole.getRoleAccounts().add(userAR);
                roleRepository.save(adminRole);
                roleRepository.save(userRole);
            }
        };
    }

}
