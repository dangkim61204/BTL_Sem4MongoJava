package com.example.ProjectSem4_JavaMongo.Controller.User;

import com.example.ProjectSem4_JavaMongo.Model.Account;
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
        HttpSession session = req.getSession();
        List<CartItem> carts = (List<CartItem>) session.getAttribute("cart");
        Account account = (Account) session.getAttribute("account");
        model.addAttribute("account", account);
        model.addAttribute("carts", carts);
        System.out.println(account);
        return "/user/checkout";
    }
}
