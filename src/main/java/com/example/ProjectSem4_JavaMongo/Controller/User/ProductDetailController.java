package com.example.ProjectSem4_JavaMongo.Controller.User;

import com.example.ProjectSem4_JavaMongo.Model.Product;
import com.example.ProjectSem4_JavaMongo.Service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ProductDetailController {
    @Autowired
    private ProductService productService;
    //chi tiet sp
    @RequestMapping("/product_detail/{id}")
    public String product_detail(@PathVariable String id, Model model){
        Product product = this.productService.getById(id);
        model.addAttribute("product", product);
        return "/user/product_detail";
    }
}



