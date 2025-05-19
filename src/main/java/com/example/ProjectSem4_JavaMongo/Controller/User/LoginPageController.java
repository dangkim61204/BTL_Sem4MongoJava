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

        // Nếu đã đăng nhập
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            boolean isUser = authorities.stream()
                    .anyMatch(auth -> auth.getAuthority().equals("USER"));
            boolean isAdmin = authorities.stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

//            if (isUser) {
//                return "redirect:/user/home"; // Chuyển đến trang user
//            } else if (isAdmin) {
//                model.addAttribute("error", "Tài khoản Admin không được phép đăng nhập tại đây.");
//                return "user/login"; // Quay lại login user kèm thông báo
//            }
        }

        // Lỗi đăng nhập
        if (request.getParameter("error") != null) {
            model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng.");
        }

        // Không đủ quyền truy cập
        if (request.getParameter("accessDenied") != null) {
            model.addAttribute("error", "Bạn không có quyền truy cập trang này.");
        }

        return "user/login"; // Trang login user
    }
}
