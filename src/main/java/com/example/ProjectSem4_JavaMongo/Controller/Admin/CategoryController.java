package com.example.ProjectSem4_JavaMongo.Controller.Admin;

import com.example.ProjectSem4_JavaMongo.Model.Category;
import com.example.ProjectSem4_JavaMongo.Repository.ProductRepository;
import com.example.ProjectSem4_JavaMongo.Service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/category/")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductRepository productRepository;

    // hien thi danh sách danh mục
    @GetMapping("list")
    public String index(Model model, @RequestParam(name="pageNo",defaultValue = "1") Integer pageNo){
        Page<Category> list =this.categoryService.getAll(pageNo);
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("list", list);
        return "admin/category/index";
    }

    // thêm mới danh mục
    @GetMapping("add")
    public String add(Model model ){
        Category category = new Category();
        model.addAttribute("category", category);
        return "admin/category/addcategory";
    }

    @PostMapping("add")
    public String save(Model model,
                       @Valid @ModelAttribute("category") Category category,
                       BindingResult result) {
        if (categoryService.findsByName(category.getName())) {
            result.rejectValue("name", null, "Tên đã tồn tại, không được để trùng");
        }

        if (result.hasErrors()) {
            return "admin/category/addcategory";
        }

        categoryService.add(category);
        return "redirect:/admin/category/list";
    }

    // Hiển thị form chỉnh sửa danh mục
    @GetMapping("edit/{id}")
    public String edit(Model model, @PathVariable("id") String id) {
        Category category = categoryService.getById(id);
        if (category == null) {
            return "redirect:/admin/category/list";
        }
        model.addAttribute("category", category);
        return "admin/category/editcategory";
    }

    @PostMapping("edit/{id}")
    public String update(@PathVariable("id") String id,
                         @Valid @ModelAttribute("category") Category category,
                         BindingResult result,
                         Model model){

        Category existing = categoryService.getById(id);

        // Nếu đổi tên, kiểm tra trùng tên khác
        if (!existing.getName().equals(category.getName()) &&
                categoryService.findsByName(category.getName())) {
            result.rejectValue("name", null, "Tên đã tồn tại");
        }

//        // Nếu có lỗi xác thực (bao gồm lỗi trùng tên), quay lại form
//        if (result.hasErrors()) {
//            return "admin/category/editcategory";
//        }

        try {
            // Đặt lại ID để đảm bảo không thay đổi
            category.setId(id);
            categoryService.update(category);
            return "redirect:/admin/category/list";
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi cập nhật danh mục: " + e.getMessage());
            return "admin/category/editcategory";
        }
    }
    // Xoá danh mục
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable("id") String id,
                         @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                         Model model) {

        // Lấy danh mục để lấy tên
        Category category = categoryService.getById(id);
        if (category == null) {
            return "redirect:/admin/category/list?pageNo=" + pageNo;
        }

//        // Kiểm tra nếu có sản phẩm thuộc danh mục này (dựa trên tên danh mục)
//        if (productRepository.fin(category.getName())) {
//            model.addAttribute("mes", "Không thể xóa danh mục khi vẫn còn sản phẩm thuộc danh mục này");
//
//            Page<Category> list = categoryService.getAll(pageNo);
//            model.addAttribute("list", list.getContent());
//            model.addAttribute("totalPage", list.getTotalPages());
//            model.addAttribute("currentPage", pageNo);
//
//            return "admin/category/list";
//        }

        categoryService.delete(id);
        return "redirect:/admin/category/list?pageNo=" + pageNo;
    }


}
