package com.example.ProjectSem4_JavaMongo.Controller.User;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Model.CartItem;
import com.example.ProjectSem4_JavaMongo.Model.Order;
import com.example.ProjectSem4_JavaMongo.Service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class CheckOutController {
    @Autowired
    private AccountService accountService;
    @RequestMapping("/checkout")
    public String checkout(Model model, HttpServletRequest req) {
        HttpSession session = req.getSession();
        List<CartItem> carts = (List<CartItem>) session.getAttribute("cart");
        Account account = (Account) session.getAttribute("account_id");

        Account acc = new Account();
        acc.setFullName((String) session.getAttribute("fullname"));
        acc.setEmail((String) session.getAttribute("email"));
        acc.setPhone((String) session.getAttribute("phone"));
        acc.setAddress((String) session.getAttribute("address"));

//        model.addAttribute("account", acc);
        model.addAttribute("carts", carts);
        return "/user/checkout";
    }
//    @PostMapping("/checkout")
//    public String updateAccountInfo(@ModelAttribute("account") Account account, HttpServletRequest request) {
//        HttpSession session = request.getSession();
//        String accountId = (String) session.getAttribute("account_id");
//
//        // Lấy tài khoản từ DB
//        Account acc = accountService.getById(accountId);
//        if (acc != null) {
//            acc.setFullName(account.getFullName());
//            acc.setEmail(account.getEmail());
//            acc.setPhone(account.getPhone());
//            acc.setAddress(account.getAddress());
//
//            accountService.save(account);
//
//            // Cập nhật lại session
//            session.setAttribute("fullname", acc.getFullName());
//            session.setAttribute("email", acc.getEmail());
//            session.setAttribute("phone", acc.getPhone());
//            session.setAttribute("address", acc.getAddress());
//        }
//
//        return "redirect:/checkout?success=true";
//    }

}
