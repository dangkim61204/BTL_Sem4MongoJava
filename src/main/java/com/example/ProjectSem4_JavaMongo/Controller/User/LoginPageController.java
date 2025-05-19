package com.example.ProjectSem4_JavaMongo.Controller.User;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Security.AccountDetail;
import com.example.ProjectSem4_JavaMongo.Service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller
public class LoginPageController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/dang-nhap")
    public String loginUser(HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Nếu đã đăng nhập thì chuyển hướng theo vai trò
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            boolean isUser = authorities.stream().anyMatch(auth -> auth.getAuthority().equals("USER"));
            boolean isAdmin = authorities.stream().anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

            if (isAdmin) {
                return "redirect:/admin/index";
            } else if (isUser) {
                return "redirect:/user/home";
            }
        }

        // Nếu login sai
        if (request.getParameter("error") != null) {
            model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng.");
        }

        // Nếu logout
        if (request.getParameter("logout") != null) {
            model.addAttribute("msg", "Bạn đã đăng xuất thành công.");
        }

        // Nếu bị từ chối truy cập
        if (request.getParameter("accessDenied") != null) {
            model.addAttribute("error", "Bạn không có quyền truy cập.");
        }

        return "user/login";
    }
}
