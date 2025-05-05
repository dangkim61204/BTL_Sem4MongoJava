package com.example.ProjectSem4_JavaMongo.Controller.Admin;

import com.example.ProjectSem4_JavaMongo.Model.Category;
import com.example.ProjectSem4_JavaMongo.Model.Product;
import com.example.ProjectSem4_JavaMongo.Service.CategoryService;
import com.example.ProjectSem4_JavaMongo.Service.ProductService;
import com.example.ProjectSem4_JavaMongo.Service.StorageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/admin/product/")
public class ProductController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductService productService;
    @Autowired
    private StorageService storageService;

    //hien thi list sp, phân trang
    @RequestMapping("list")
    public String index(Model model, @Param("key") String key,
                        @RequestParam(name="pageNo",defaultValue = "1") Integer pageNo){
        Page<Product> list =this.productService.getAll(pageNo);
        if(key != null){
            list = this.productService.searchProductpage(key, pageNo);
            model.addAttribute("key", key);
        }
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("list", list);
        return "admin/product/index";
    }

    //thêm mới sp
    @RequestMapping("add")
    public String add(Model model ){
        Product product = new Product();
        model.addAttribute("product", product);
        List<Category> listCate = this.categoryService.getAll();
        model.addAttribute("listCate", listCate);
        return "admin/product/addproduct";
    }
    // Xử lý thêm sản phẩm
    @PostMapping("add")
    public String save(Model model,
                       @Valid @ModelAttribute("product") Product product,
                       BindingResult result,
                       @RequestParam("file") MultipartFile file) {

        if (result.hasErrors()) {
            model.addAttribute("listCate", categoryService.getAll());
            return "admin/product/addproduct";
        }

        try {
            // Lưu file nếu file không rỗng
            if (!file.isEmpty()) {
                storageService.store(file);
                String fileName = file.getOriginalFilename();
                product.setImage(fileName);
            }
            // Lưu sản phẩm
            productService.add(product);
            // Chuyển hướng về danh sách sản phẩm
            return "redirect:/admin/product/list";
        } catch (Exception e) {
            // Nếu có lỗi runtime, hiển thị thông báo lỗi và quay lại form
            model.addAttribute("listCate", categoryService.getAll());
            model.addAttribute("error", "Lỗi khi lưu sản phẩm: " + e.getMessage());
            return "admin/product/addproduct";
        }
    }

    //sửa sp theo
    @RequestMapping("edit/{id}")
    public String edit(@Valid  Model model , @PathVariable("id") String id){
        Product product = this.productService.getById(id);
        model.addAttribute("product", product);
        model.addAttribute("listCate", categoryService.getAll());
        return "admin/product/editproduct";
    }

    @PostMapping ("edit/{id}")
    public String update(@PathVariable("id") String id,
                         @Valid @ModelAttribute("product") Product product,
                         BindingResult result,
                         @RequestParam("file") MultipartFile file,
                         @RequestParam("oldPicture") String oldPicture,
                         Model model){
        if (result.hasErrors()) {
            model.addAttribute("listCate", categoryService.getAll());
            return "admin/product/editproduct";
        }

        try {
            // Xử lý file upload
            if (!file.isEmpty()) {
                storageService.store(file);
                String fileName = file.getOriginalFilename();
                product.setImage(fileName);
            } else {
                product.setImage(oldPicture); // Giữ ảnh cũ nếu không upload ảnh mới
            }

            // Cập nhật sản phẩm
            product.setProductId(id); // Đảm bảo ID không bị thay đổi
            productService.update(product);
            return "redirect:/admin/product/list";
        } catch (Exception e) {
            // Nếu có lỗi runtime, hiển thị lỗi và quay lại form
            model.addAttribute("listCate", categoryService.getAll());
            model.addAttribute("error", "Lỗi khi cập nhật sản phẩm: " + e.getMessage());
            return "admin/product/editproduct";
        }

    }
    //xoá sp
    @RequestMapping("delete/{id}")
    public String delete(@PathVariable("id") String id,
                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                         Model model){
        productService.delete(id);
        return "redirect:/admin/product/list?pageNo=" + pageNo;
    }
}


