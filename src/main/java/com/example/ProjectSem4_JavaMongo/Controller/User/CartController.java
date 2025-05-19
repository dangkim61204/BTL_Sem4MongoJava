package com.example.ProjectSem4_JavaMongo.Controller.User;

//import com.Project.Project_springboot.model.CartItem;
//import com.Project.Project_springboot.model.Product;
//import com.Project.Project_springboot.service.ProductService;
import com.example.ProjectSem4_JavaMongo.Model.CartItem;
import com.example.ProjectSem4_JavaMongo.Model.Order;
import com.example.ProjectSem4_JavaMongo.Model.Product;
import com.example.ProjectSem4_JavaMongo.Security.AccountDetail;
//import com.example.ProjectSem4_JavaMongo.Service.Impl.OrderServiceImpl;
import com.example.ProjectSem4_JavaMongo.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.*;

@Controller
public class CartController {
    @Autowired
    private ProductService productService;
//
//    @Autowired
//    private OrderServiceImpl orderService;

    // Thêm số lượng sản phẩm và tên người dùng vào model
//    @ModelAttribute
//    public void addCommonAttributes(Model model, HttpServletRequest req) {
//        // Số lượng sản phẩm trong giỏ hàng
//        List<CartItem> carts = new ArrayList<>();
//        HttpSession session = req.getSession();
//        if (session.getAttribute("cart") != null) {
//            carts = (List<CartItem>) session.getAttribute("cart");
//        }
//        model.addAttribute("itemCount", carts.size());
//
//        // Tên người dùng
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
//            AccountDetail accountDetail = (AccountDetail) authentication.getPrincipal();
//            model.addAttribute("fullname", accountDetail.getAccount().getFullName());
//            model.addAttribute("isLoggedIn", true);
//        } else {
//            model.addAttribute("isLoggedIn", false);
//        }
//    }
//them sp vào gio hang
    @PostMapping("/cart/add")
    @ResponseBody
    public ResponseEntity<?> addToCart(@RequestParam("id") String id,
                                       @RequestParam("name") String name,
                                       @RequestParam("price") String priceStr,
                                       @RequestParam("image") String image,
                                       @RequestParam("quantity") int quantity,
                                       HttpServletRequest req) {
        double price = 0;
        try {
            price = Double.parseDouble(priceStr);
        } catch (NumberFormatException e) {
            return ResponseEntity.badRequest().body("Giá sản phẩm không hợp lệ");
        }

        HttpSession session = req.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new ArrayList<>();
        }

        boolean found = false;
        for (CartItem item : cart) {
            if (item.getId().equals(id)) {
                item.setQuantity(item.getQuantity() + quantity);
                found = true;
                break;
            }
        }

        if (!found) {
            cart.add(new CartItem(id, name, image, price, quantity));
        }

        session.setAttribute("cart", cart);
        return ResponseEntity.ok("Thêm vào giỏ hàng thành công");
    }

    // Xem giỏ hàng
    @RequestMapping("/cart")
    public String viewCart(Model model, HttpServletRequest req) {
        model.addAttribute("page", "cart");
        List<CartItem> carts = new ArrayList<>();
        HttpSession session = req.getSession();
        if (session.getAttribute("cart") != null) {
            carts = (List<CartItem>) session.getAttribute("cart");
            System.out.println("Số lượng sản phẩm trong giỏ: " + carts.size()); // <-- kiểm tra trong console
        }

        double total = carts.stream()
                .mapToDouble(item -> item.getPrice() * item.getQuantity())
                .sum();

        model.addAttribute("total", total);
        model.addAttribute("carts", carts);

        return "user/cart"; // Không có dấu / ở đầu
    }
//update giỏ hàng
    @PostMapping("/cart/update")
    @ResponseBody
    public ResponseEntity<Map<String, Object>> updateQuantity(@RequestParam String id,
                                                              @RequestParam int quantity,
                                                              HttpServletRequest req) {
        HttpSession session = req.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");

        double subtotal = 0;
        double total = 0;

        if (cart != null) {
            for (CartItem item : cart) {
                if (item.getId().equals(id)) {
                    item.setQuantity(quantity);
                    subtotal = item.getPrice() * quantity;
                }
            }
            total = cart.stream().mapToDouble(i -> i.getPrice() * i.getQuantity()).sum();
            session.setAttribute("cart", cart);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("subtotal", subtotal);
        result.put("total", total);
        result.put("subtotalFormatted", NumberFormat.getCurrencyInstance().format(subtotal));
        result.put("totalFormatted", NumberFormat.getCurrencyInstance().format(total));

        return ResponseEntity.ok(result);
    }
// xoa sp trong gio hang
    @GetMapping("/cart/remove")
    public String removeFromCart(@RequestParam("id") String id, HttpServletRequest req) {
        HttpSession session = req.getSession();
        List<CartItem> cart = (List<CartItem>) session.getAttribute("cart");
        if (cart != null) {
            cart.removeIf(item -> item.getId().equals(id));
            session.setAttribute("cart", cart);
        }
        return "redirect:/cart";
    }

    // Trang xác nhận đặt hàng thành công
    @GetMapping("/order-success")
    public String orderSuccess(Model model) {
        model.addAttribute("page", "order-success");
        return "/user/order-success";
    }



}
