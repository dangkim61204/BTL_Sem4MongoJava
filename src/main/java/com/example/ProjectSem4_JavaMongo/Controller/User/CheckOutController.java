package com.example.ProjectSem4_JavaMongo.Controller.User;

import com.example.ProjectSem4_JavaMongo.Model.CartItem;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckOutController {
    @RequestMapping("/checkout")
    public String checkout(Model model, HttpServletRequest req){
        model.addAttribute("page", "cart");

        HttpSession session = req.getSession();
        List<CartItem> carts = (List<CartItem>) session.getAttribute("cart");

        if (carts == null || carts.isEmpty()) {
            return "redirect:/cart"; // Không có sản phẩm, chuyển về giỏ hàng
        }

        double total = carts.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Add cart info
        model.addAttribute("carts", carts);
        model.addAttribute("total", total);

        // Add user info from session
        model.addAttribute("username", session.getAttribute("username"));
        model.addAttribute("fullname", session.getAttribute("fullname"));
        model.addAttribute("email", session.getAttribute("email"));
        model.addAttribute("phone", session.getAttribute("phone"));
        model.addAttribute("address", session.getAttribute("address"));

        return "/user/checkout";
    }
}
