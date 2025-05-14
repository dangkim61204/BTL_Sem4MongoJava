package com.example.ProjectSem4_JavaMongo.Controller.User;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Security.AccountDetail;
import com.example.ProjectSem4_JavaMongo.Service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginPageController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @RequestMapping("/loginUser")
    public String logiUser(Model model){
//        model.addAttribute("account", new Account());
        return "/user/login";
    }
    //đăng nhập trang người dùng
    @RequestMapping("/dang-nhap")
    public String dang_nhap( String password, String username, Model model, HttpServletRequest req){
        Account acc = this.accountService.findByUserName(username);
        if(acc == null ||!passwordEncoder.matches(password, acc.getPassword())) {
            model.addAttribute("msg", "Tài khoản hoặc mật khẩu không chính xác");
            return "/user/login";
        }
        HttpSession session = req.getSession();
        session.setMaxInactiveInterval(3600);
//        String uuidString = UUID.randomUUID().toString();
        session.setAttribute("account_id", acc.getAccountId());
        session.setAttribute("fullname", acc.getFullName());
        session.setAttribute("email", acc.getEmail());
        session.setAttribute("username", acc.getUserName());
        session.setAttribute("phone", acc.getPhone());
        session.setAttribute("address", acc.getAddress());

        return "redirect:/";
    }


    //GET: thoat trang user
    @RequestMapping(value = "thoat")
    public String logout(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        session.invalidate();
        return "redirect:/";
    }

}
