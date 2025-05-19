package com.example.ProjectSem4_JavaMongo.Controller.Admin;
import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Model.CartItem;
import com.example.ProjectSem4_JavaMongo.Model.Order;
import com.example.ProjectSem4_JavaMongo.Model.OrderDetail;
import com.example.ProjectSem4_JavaMongo.Repository.OrderRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private OrderRepository orderRepository;
//    @GetMapping("/order/dat-hang")
//    public  String viewOrder(Model model){
////        model.addAttribute("order" , "dat hang thanh cong");
//
//        return "/user/home";
//    }
    @PostMapping("/order/dat-hang")
    public String datHang(@ModelAttribute("order") Order order,
                          HttpServletRequest request,
                          Model model) {
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");

        if (account == null || account.getAccountId() == null) {
            model.addAttribute("error", "Bạn chưa đăng nhập hoặc tài khoản không hợp lệ.");
            return "/user/checkout";
        }

        // Gán account cho order (đã có ID hợp lệ)
        order.setAccount(account);
        order.setOrderDate(new Date(System.currentTimeMillis()));

        orderRepository.save(order); // ✅ Không lỗi

        // Clear cart nếu cần
        session.removeAttribute("cart");

        model.addAttribute("success", "Đặt hàng thành công!");
        return "redirect:/user/home";
    }

}
