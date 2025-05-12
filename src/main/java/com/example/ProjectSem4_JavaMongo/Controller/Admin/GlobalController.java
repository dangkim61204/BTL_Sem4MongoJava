package com.example.ProjectSem4_JavaMongo.Controller.Admin;

import com.example.ProjectSem4_JavaMongo.Security.AccountDetail;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.ArrayList;
import java.util.List;

@Controller
public class GlobalController {
    //hển thị xin chào name ở trang admin
    @ModelAttribute
    public void addUserToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            AccountDetail name = (AccountDetail) authentication.getPrincipal();
            model.addAttribute("name", name.getAccount().getFullName());
        }
    }
    //hển thị xin chào name ở trang người dùng
    @ModelAttribute
    public void addUserNameToModel(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();

            model.addAttribute("fullname", accountDetail.getAccount().getFullName());
            model.addAttribute("isLoggedIn", true); // Thêm biến để kiểm tra trạng thái đăng nhập
        } else {
            model.addAttribute("isLoggedIn", false); // Người dùng chưa đăng nhập
        }
    }
//    @ModelAttribute
//    public void addCountItem(Model model, HttpServletRequest req) {
//        List<CartItem> carts = new ArrayList<>();
//        HttpSession session = req.getSession();
//        if (session.getAttribute("cart") != null) {
//            carts = (List<CartItem>) session.getAttribute("cart");
//        }
//        var itemCount = String.valueOf(carts.size());
//        model.addAttribute("itemCount", itemCount);
//
//    }
}
