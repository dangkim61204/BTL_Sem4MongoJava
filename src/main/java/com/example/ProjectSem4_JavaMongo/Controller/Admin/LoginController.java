package com.example.ProjectSem4_JavaMongo.Controller.Admin;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;

@Controller

public class LoginController {
    @GetMapping("/admin")
    public String index(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String name = authentication.getName();
        model.addAttribute("name", name);
        return "admin/index";
    }

    @RequestMapping("/login")
    public String loginAdmin(HttpServletRequest request, Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Nếu đã đăng nhập
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            boolean isAdmin = authorities.stream()
                    .anyMatch(auth -> auth.getAuthority().equals("ADMIN"));

            if (isAdmin) {
                return "admin/index"; // Nếu là admin thì vào trang chính admin
            } else {
                return "redirect:/login"; // Redirect về login nếu không có quyền
            }
        }
        // Nếu login sai
        if (request.getParameter("error") != null) {
            model.addAttribute("error", "Tài khoản hoặc mật khẩu không đúng.");
        }
        // Nếu bị redirect với lý do không đủ quyền
        if (request.getParameter("accessDenied") != null) {
            model.addAttribute("error", "Bạn không có quyền truy cập trang quản trị.");
        }
        return "admin/login"; // Trang login
    }



    // thoat admin
    @RequestMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws Exception {
        SecurityContextHolder.getContext().setAuthentication(null);
        request.getSession().invalidate();
        model.addAttribute("username", null);
        // Optional: Redirect to a custom logout page
        return "redirect:admin/login"; // Redirect to login page after logout
    }
}
