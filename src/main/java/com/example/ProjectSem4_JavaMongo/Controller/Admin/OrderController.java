package com.example.ProjectSem4_JavaMongo.Controller.Admin;
import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Model.CartItem;
import com.example.ProjectSem4_JavaMongo.Model.Order;
import com.example.ProjectSem4_JavaMongo.Model.OrderDetail;
import com.example.ProjectSem4_JavaMongo.Repository.OrderRepository;
import com.example.ProjectSem4_JavaMongo.Service.OrderDetailService;
import com.example.ProjectSem4_JavaMongo.Service.OrderService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/order/")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderDetailService orderDetailService;
    @Autowired
    private OrderRepository orderRepository;



    // Xem danh sách đơn hàng có phân trang
    @GetMapping("list")
    public String listOrders(Model model,
                             @RequestParam(defaultValue = "0") int page,
                             @RequestParam(defaultValue = "5") int size) {
        Page<Order> orderPage = orderService.getAllOrders(PageRequest.of(page, size));

        model.addAttribute("orderPage", orderPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", orderPage.getTotalPages());

        return "admin/order/index";
    }

    @GetMapping("/detail/{id}")
    public String orderDetail(@PathVariable("id") String id, Model model) {
        // Lấy Order theo ID
        Optional<Order> orderOpt = orderService.getOrderById(id);
        if (!orderOpt.isPresent()) {
            model.addAttribute("error", "Đơn hàng không tồn tại.");
            return "admin/order/error";
        }
        Order order = orderOpt.get();

        // Lấy danh sách OrderDetail
        List<OrderDetail> orderDetails = orderDetailService.findByOrderId(id);
        if (orderDetails == null || orderDetails.isEmpty()) {
            model.addAttribute("error", "Không có chi tiết đơn hàng.");
            return "admin/order/error";
        }

        // Lấy thông tin Account từ Order
        Account account = order.getAccount();

        // Tính tổng tiền
        double total = orderDetails.stream()
                .mapToDouble(od -> od.getPrice() * od.getQuantity())
                .sum();

        // Thêm vào model
        model.addAttribute("order", order); // Để lấy orderDate
        model.addAttribute("orderDetails", orderDetails); // Sửa tên thành orderDetails
        model.addAttribute("account", account);
        model.addAttribute("total", total);

        return "admin/order/orderDetail";
    }
//    xoa don hang
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id, Model model) {
        if (orderService.delete(id)) {
            model.addAttribute("success", "Xóa đơn hàng và chi tiết đơn hàng thành công.");
            return "redirect:/admin/order/list";
        } else {
            model.addAttribute("error", "Xóa đơn hàng thất bại. Đơn hàng không tồn tại hoặc có lỗi.");
            return "redirect:/admin/order/list";
        }
    }
}
