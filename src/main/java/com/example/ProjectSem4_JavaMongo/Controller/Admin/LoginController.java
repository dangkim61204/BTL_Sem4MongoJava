package com.example.ProjectSem4_JavaMongo.Controller.Admin;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {
    @GetMapping("/admin")
    public String index(){
        return "admin/index";
    }

    //dang nhap admin
    @RequestMapping("/login")
    public String loginadmin(@Valid Account account , BindingResult bindingResult, Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUserName = "";
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            currentUserName = authentication.getName();
            return currentUserName;
        }

        model.addAttribute("username", currentUserName);

        return "admin/login"; // Your home page view name

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
