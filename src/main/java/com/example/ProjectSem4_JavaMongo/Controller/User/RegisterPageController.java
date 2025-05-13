package com.example.ProjectSem4_JavaMongo.Controller.User;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Model.AccountRole;
import com.example.ProjectSem4_JavaMongo.Model.Role;
import com.example.ProjectSem4_JavaMongo.Repository.AccountRepository;
import com.example.ProjectSem4_JavaMongo.Repository.AccountRoleRepository;
import com.example.ProjectSem4_JavaMongo.Repository.RoleRepository;
import com.example.ProjectSem4_JavaMongo.Service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.UUID;

@Controller
public class RegisterPageController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private AccountRoleRepository accountRoleRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/registerUser")
    public String registerUser(Model model){
        model.addAttribute("account", new Account());
        return "/user/register";
    }
    @PostMapping("/registerUser")
    public String dang_ky(@ModelAttribute("account") Account account, Model model, HttpServletRequest req) {
//        System.out.println(account.getUserName());
        Account acc = this.accountService.findByEmail(account.getEmail());
        String pass = passwordEncoder.encode(account.getPassword());
        if(acc != null) {
            model.addAttribute("msg", "Email đã tồn tại");
            return "/user/home";
        }
        account.setPassword(pass);
//        Account ac = new Account();
////        String uuidString = UUID.randomUUID().toString();
////        ac.setAccountId(uuidString.substring(0, 3));
//        ac.setUserName(username);
//        ac.setFullName(fullname);
//        ac.setEmail(email);
//        ac.setAddress(address);
//        ac.setPassword(pass);
//        ac.setPhone(phone);

        Account user = accountRepository.save(account);
        System.out.println(user);
        AccountRole role = new AccountRole();
        role.setAccount(user);
        Role role1 = roleRepository.findById("2").get(); // Role user
        role.setRole(role1);
        accountRoleRepository.save(role);

        model.addAttribute("msgt", "Đăng ký thành công. Vui lòng đăng nhập.");
        return "/user/login";
    }

}



