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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
//        model.addAttribute("account", new Account());
        return "/user/register";
    }
    //đăng ký trang người dùng
    @RequestMapping("/dang-ky")
    public String dang_ky(String password, String email, String address, String phone, String username , String fullname, Model model, HttpServletRequest req){
//        if (bindingResult.hasErrors()) {
////            model.addAttribute("account", account);
//            return "/user/register";
//        }
//        @Valid  @ModelAttribute("account") Account account, BindingResult bindingResult,
        Account acc = this.accountService.findByEmail(email);
        String pass = passwordEncoder.encode(password);
        if(acc != null) {
            model.addAttribute("msg", "Email đã tồn tại");
            return "/user/home";
        }
        System.out.println("873468734");
        Account ac = new Account();
        String uuidString = UUID.randomUUID().toString();
        ac.setAccountId(uuidString.substring(0, 3));
        ac.setUserName(username);
        ac.setFullName(fullname);
        ac.setEmail(email);
        ac.setAddress(address);
        ac.setPassword(pass);
        ac.setPhone(phone);

        Account user = accountRepository.save(ac);
        AccountRole role = new AccountRole();
        role.setAccount(user);
        Role role1 = roleRepository.findById("2").get();
        role.setRole(role1);
        accountRoleRepository.save(role);

        return "/user/login";
    }
}



