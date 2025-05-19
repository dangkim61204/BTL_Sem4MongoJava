package com.example.ProjectSem4_JavaMongo.Controller.User;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Model.CartItem;
import com.example.ProjectSem4_JavaMongo.Model.Order;
import com.example.ProjectSem4_JavaMongo.Model.OrderDetail;
import com.example.ProjectSem4_JavaMongo.Repository.OrderDetailRepository;
import com.example.ProjectSem4_JavaMongo.Repository.OrderRepository;
import com.example.ProjectSem4_JavaMongo.Service.AccountService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class CheckOutController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @GetMapping("/checkout")
    public String checkout(Model model, HttpServletRequest req) {
        // Lấy thông tin người dùng từ Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("error", "Vui lòng đăng nhập để tiếp tục thanh toán.");
            return "redirect:/dang-nhap";
        }

        // Lấy username từ Authentication
        String username = authentication.getName();
        Account account = accountService.findByUserName(username);
        if (account == null) {
            model.addAttribute("error", "Tài khoản không tồn tại.");
            return "redirect:/dang-nhap";
        }

        // Lấy giỏ hàng từ session
        HttpSession session = req.getSession();
        List<CartItem> carts = (List<CartItem>) session.getAttribute("cart");
        if (carts == null || carts.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng của bạn trống.");
            return "redirect:/user/cart";
        }

        // Thêm thông tin vào model
        model.addAttribute("carts", carts);
        model.addAttribute("email", account.getEmail());
        model.addAttribute("username", account.getUserName());
        model.addAttribute("phone", account.getPhone());
        model.addAttribute("address", account.getAddress());
        model.addAttribute("fullname", account.getFullName());
        model.addAttribute("order", new Order()); // Để bind form

        double total = carts.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        // Đưa danh sách sản phẩm và tổng giá trị vào model

        model.addAttribute("total", total);
        // Lưu account vào session để sử dụng trong datHang (đồng bộ)
        session.setAttribute("account", account);

        return "/user/checkout";
    }

    @GetMapping("/order/dat-hang")
    public String viewOrder(Model model) {
        model.addAttribute("error", "Vui lòng đặt hàng qua form thanh toán.");
        return "/user/home";
    }

    @PostMapping("/order/dat-hang")
    public String datHang(@ModelAttribute("order") Order order, HttpServletRequest request, Model model) {
        // Lấy thông tin người dùng từ Spring Security
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || authentication instanceof AnonymousAuthenticationToken) {
            model.addAttribute("error", "Vui lòng đăng nhập để đặt hàng.");
            return "/user/checkout";
        }

        // Lấy account từ session (đã lưu trong checkout)
        HttpSession session = request.getSession();
        Account account = (Account) session.getAttribute("account");
        List<CartItem> carts = (List<CartItem>) session.getAttribute("cart");

        if (account == null) {
            model.addAttribute("error", "Tài khoản không hợp lệ.");
            return "/user/checkout";
        }

        if (carts == null || carts.isEmpty()) {
            model.addAttribute("error", "Giỏ hàng của bạn trống.");
            return "/user/checkout";
        }

        // Tính tổng tiền
        double totalAmount = 0;
        for (CartItem item : carts) {
            totalAmount += item.getPrice() * item.getQuantity();
        }

        // Điền thông tin cho order
        order.setAccount(account);
        order.setFullname(account.getFullName());
        order.setEmail(account.getEmail());
        order.setPhone(account.getPhone());
        order.setAddress(account.getAddress());
        order.setOrderDate(new Date(System.currentTimeMillis()));
        order.setTotalAmount(totalAmount);

        try {
            // Lưu order
            Order savedOrder = orderRepository.save(order);

            // Tạo và lưu chi tiết đơn hàng
            List<OrderDetail> orderDetails = new ArrayList<>();
            for (CartItem item : carts) {
                OrderDetail detail = new OrderDetail();
                detail.setOrderId(savedOrder.getId());
                detail.setProductName(item.getName());
                detail.setImage(item.getImage());
                detail.setPrice(item.getPrice());
                detail.setQuantity(item.getQuantity());
                detail.setTotal(item.getPrice() * item.getQuantity());
                orderDetails.add(detail);
            }
            orderDetailRepository.saveAll(orderDetails);

            // Liên kết chi tiết đơn hàng với order
            savedOrder.setOrderDetails(orderDetails);
            orderRepository.save(savedOrder);

            // Xóa giỏ hàng
            session.removeAttribute("cart");
            model.addAttribute("success", "Đặt hàng thành công!");
        } catch (Exception e) {
            model.addAttribute("error", "Đặt hàng thất bại. Vui lòng thử lại.");
            return "/user/checkout";
        }

        return "redirect:/";
    }


}
