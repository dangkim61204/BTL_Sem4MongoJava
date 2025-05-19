package com.example.ProjectSem4_JavaMongo.Controller.User;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Model.Product;
import com.example.ProjectSem4_JavaMongo.Repository.AccountRepository;
import com.example.ProjectSem4_JavaMongo.Service.AccountService;
import com.example.ProjectSem4_JavaMongo.Service.CategoryService;
import com.example.ProjectSem4_JavaMongo.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private AccountService accountService;

    @GetMapping({"/","/home"})
    public String home(Model model, HttpServletRequest req, @Param("key") String key,
                       @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) {

        Page<Product> listview = this.productService.getAll(pageNo);

        if (key != null && !key.isEmpty()) {
            listview = this.productService.searchProductpage(key, pageNo);
            model.addAttribute("key", key);
        }

        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("totalPage", listview.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("listview", listview);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        Account acc = accountService.findByUserName(username);
        String email = acc.getEmail();
        String phone = acc.getPhone();
        String address = acc.getAddress();
        HttpSession session = req.getSession();
        session.setAttribute("email", email);
        session.setAttribute("phone", phone);
        session.setAttribute("address", address);
        session.setAttribute("username", username);
        model.addAttribute("username", username);
        System.out.println(email);


        return "/user/home";
    }



}


