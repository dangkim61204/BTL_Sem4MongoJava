package com.example.ProjectSem4_JavaMongo.Controller.User;

import com.example.ProjectSem4_JavaMongo.Model.Product;
import com.example.ProjectSem4_JavaMongo.Repository.CategoryRepository;
import com.example.ProjectSem4_JavaMongo.Service.CategoryService;
import com.example.ProjectSem4_JavaMongo.Service.ProductService;
import com.example.ProjectSem4_JavaMongo.Service.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class ShopController {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private StorageService storageService;
    public static List<Product> products=new ArrayList<>();
    @Autowired
    private CategoryRepository categoryRepository;

    //phan trang, tim kiem ten
    @RequestMapping("/shop")
    public String shop(Model model, HttpServletRequest req, @Param("key") String key, @RequestParam(name="pageNo",defaultValue = "1") Integer pageNo){
        Page<Product> products =this.productService.getAll(pageNo);
        if(key != null){
            products = this.productService.searchProductpage(key, pageNo);
            model.addAttribute("key", key);
        }
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", products);
//      model.addAttribute("products", productService.getAll());
        return "/user/shop";
    }
    //tìm kiếm khoảng giá from - to
    @RequestMapping("/shop/searchprice")
    public String searchprice(Model model, HttpServletRequest req, @RequestParam(value = "priceFrom", defaultValue = "0.0") double priceFrom,@RequestParam(value = "priceTo", defaultValue = "0.0") double priceTo, @RequestParam(name="pageNo",defaultValue = "1") Integer pageNo){
        Page<Product> products =this.productService.getAll(pageNo);
        if(priceFrom == 0.0 && priceTo > 0.0 || priceFrom > 0.0 && priceTo  > priceFrom){
            products = this.productService.searchPriceShop(priceFrom, priceTo, pageNo);
            model.addAttribute("priceFrom", priceFrom);
            model.addAttribute("priceTo", priceTo);
        }else if(priceTo < 0.0 || priceTo < 0.0  || priceTo < priceFrom){
            model.addAttribute("mes", "không tìm thấy sản phẩm");
            products = this.productService.searchPriceShop(priceFrom, priceTo, pageNo);
            model.addAttribute("priceFrom", priceFrom);
            model.addAttribute("priceTo", priceTo);
            return "/user/shop";
        }
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("totalPage", products.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("products", products);
        return "/user/shop";
    }
    //lọc sp theo danh mục
    @GetMapping("/shop/categories")
    public String listProducts(@RequestParam(value = "cate", required = false) List<String> cateIds, Model model) {
        List<Product> products;

        if (cateIds != null && !cateIds.isEmpty()) {
            products = this.productService.findProductById(cateIds);
        } else {
            products = this.productService.getAll();
        }

        model.addAttribute("categories", categoryService.getAll()); // nếu muốn hiển thị danh sách tất cả danh mục
        model.addAttribute("products", products);
        return "/user/shop";
    }


    // sort name A-Z, price a-z

    @GetMapping("/shop-sort")
    public String sort(Model model,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "10") int size,
                       @RequestParam(defaultValue = "default",required = false) String sort) {
        Sort sort1 = Sort.unsorted();
        switch (sort) {
            case "name_asc":
                sort1 = Sort.by("productName").ascending();//a-z
                break;
            case "name_desc":
                sort1 = Sort.by("productName").descending();//z-A
                break;
            case "price_asc":
                sort1 = Sort.by("price").ascending();//a-z
                break;
            case "price_desc":
                sort1 = Sort.by("price").descending();//a-z
                break;
        }
        Page<Product> productPage = productService.findAll(page, size, sort1);
        model.addAttribute("categories", categoryService.getAll());
        model.addAttribute("products", productPage);
        model.addAttribute("currentPage", page);
        model.addAttribute("pageSize", size);
        model.addAttribute("sort", sort);
        return "/user/shop";

    }
}
