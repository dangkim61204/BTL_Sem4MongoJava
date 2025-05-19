package com.example.ProjectSem4_JavaMongo.Controller.User;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Model.AccountRole;
import com.example.ProjectSem4_JavaMongo.Model.Role;
import com.example.ProjectSem4_JavaMongo.Repository.AccountRepository;
import com.example.ProjectSem4_JavaMongo.Repository.AccountRoleRepository;
import com.example.ProjectSem4_JavaMongo.Repository.RoleRepository;
import com.example.ProjectSem4_JavaMongo.Service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
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
        account.setActive(true);

        // Bước 1: Lưu account mới
        Account savedAccount = accountRepository.save(account);

        // Bước 2: Lấy role 'USER' có id = "2"
        Role role = roleRepository.findById("2").orElse(null);
        if (role == null) {
            model.addAttribute("msg", "Không tìm thấy quyền USER");
            return "/user/home";
        }

        // Bước 3: Tạo accountRole
        AccountRole accountRole = new AccountRole();
        accountRole.setAccount(savedAccount);
        accountRole.setRole(role);

        AccountRole savedAccountRole = accountRoleRepository.save(accountRole);

        // Bước 4: Gán role vào account và role (để đồng bộ 2 chiều nếu cần)
        savedAccount.getAccountRoles().add(savedAccountRole);
        accountRepository.save(savedAccount);
        role.getRoleAccounts().add(savedAccountRole);
        roleRepository.save(role);

        HttpSession session = req.getSession();
        session.setAttribute("account", savedAccount);

        model.addAttribute("msgt", "Đăng ký thành công. Vui lòng đăng nhập.");
        return "redirect:/dang-nhap";
    }

}



