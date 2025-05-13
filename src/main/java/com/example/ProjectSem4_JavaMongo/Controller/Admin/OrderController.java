//package com.example.ProjectSem4_JavaMongo.Controller.Admin;
//
//import com.example.ProjectSem4_JavaMongo.Repository.OrderRepository;
//import com.example.ProjectSem4_JavaMongo.Service.AccountService;
//import com.example.ProjectSem4_JavaMongo.Service.CategoryService;
//import com.example.ProjectSem4_JavaMongo.Service.OrderService;
//import com.example.ProjectSem4_JavaMongo.Service.ProductService;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.repository.query.Param;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.RequestParam;
//
//import java.sql.Date;
//import java.text.SimpleDateFormat;
//import java.time.LocalDate;
//import java.util.ArrayList;
//import java.util.Calendar;
//import java.util.List;
//
//@Controller
//public class OrderController {
//    @Autowired
//    private ProductService productService;
//
//    @Autowired
//    private OrderService orderSevice;
//    @Autowired
//    private CategoryService categoryService;
//    @Autowired
//    private AccountService accountService;
//
//    @Autowired
//    private OrderRepository orderRepository;
//    //admin
//    //list
//    @RequestMapping("list")
//    public String index(Model model, @Param("key") String key, @RequestParam(name="pageNo",defaultValue = "1") Integer pageNo) {
//        Page<Order> list = this.orderSevice.getAll(pageNo);
//        if (key != null) {
//            list = this.orderSevice.searchOrder(key, pageNo);
//            model.addAttribute("key", key);
//        }
//
//        model.addAttribute("totalPage", list.getTotalPages());
//        model.addAttribute("currentPage", pageNo);
//        model.addAttribute("list", list);
//        model.addAttribute("list1", list.getContent());
//        return "admin/order/index";
//    }
//    //xem chi tiest đơn hàng  view
//    @RequestMapping(value= {"orderDetail/{id}"}, method = RequestMethod.GET)
//    public String order_detail(Model model, @PathVariable("id") String id) {
//        List<OrderDetail> orderDetail = orderSevice.getId(id);
//        double total = orderDetail.stream()
//                .mapToDouble(item -> item.getPrice() * item.getQuantity())
//                .sum();
//
//        Order  list = this.orderSevice.getById(Integer.valueOf(id));
//        // Đưa danh sách sản phẩm và tổng giá trị vào model
//        model.addAttribute("total", total);
//        model.addAttribute("account", list.getAccount());
//        model.addAttribute("orderDetail", orderDetail);
//        return "admin/order/orderDetail";
//    }
//    //    //xoas đơn hàng tren admin
//    @RequestMapping("delete/{id}")
//    public String delete( @PathVariable("id") Integer id, Model model){
//
//        if(this.orderSevice.delete(id)){
//            return "redirect:/admin/order/list";
//        }else{
//            return "admin/order/list";
//        }
//    }
//
//    //views
////POST: dat-hang "xử lý đặt hang"
//    @RequestMapping(value = {"dat-hang"}, method = RequestMethod.POST)
//    public String addOrder(String id,String email,String name,String fullname, String address, String phone, Model model, HttpServletRequest req) {
//
//        List<CartItem> carts=null;
//        HttpSession session = req.getSession();
//        if (session.getAttribute("account_id") == null) {
//            return "redirect:/dang-nhap";
//        }
//        if (session.getAttribute("cart") != null) {
//            System.out.println("helllo");
//            carts = (List<CartItem>) session.getAttribute("cart");
//            String timeStamp = new SimpleDateFormat("yyMMdd-HHmmss").format(Calendar.getInstance().getTime());
//
//            Order order=new Order();
//
//            order.setAccount(accountService.findByEmail((String) session.getAttribute("email")));
//            order.setOrder_date(Date.valueOf(LocalDate.now()));
//            order.setAddress(address);
//            order.setPhone(phone);
//            order.setEmail(email);
//            order.setName(name);
//            order.setName(fullname);
//
//            List<OrderDetail> orderdetails=new ArrayList<>();
//            for (CartItem cart : carts) {
//                OrderDetail orderdetail = new OrderDetail();
//                Product pr = productService.getById(cart.getId());
//                orderdetail.setPrice((float) cart.getPrice());
//                orderdetail.setQuantity(cart.getQuantity());
//                orderdetail.setOrder(order);
//                orderdetail.setProduct(pr);
//                orderdetails.add(orderdetail);
//            }
//            orderSevice.insertOrderDetail(order, orderdetails);
//            model.addAttribute("msg","Đặt hàng thành công");
//
//        } else {
//            model.addAttribute("msg","Giỏ hàng trống");
//        }
//        model.addAttribute("Categorys", categoryService.getAll());
//        model.addAttribute("Products", productService.getAll());
//        carts=new ArrayList<>();
//        session.setAttribute("cart", null);
//        model.addAttribute("carts", carts);
//        return "redirect:/";
//    }
//
//}
