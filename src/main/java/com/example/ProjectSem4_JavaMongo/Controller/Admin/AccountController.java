package com.example.ProjectSem4_JavaMongo.Controller.Admin;

import com.example.ProjectSem4_JavaMongo.Model.Account;
import com.example.ProjectSem4_JavaMongo.Service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/admin/account/")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @RequestMapping("list")
    public String index(Model model, @Param("key") String key, @RequestParam(name="pageNo",defaultValue = "1") Integer pageNo) {
        Page<Account> list = this.accountService.getAll(pageNo);
        if (key != null) {
            list = this.accountService.searchAccount(key, pageNo);
            model.addAttribute("key", key);
        }
        model.addAttribute("totalPage", list.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("list", list);
        return "admin/account/index";
    }
    //xoas tai khoan tren admin
    @RequestMapping("delete/{id}")
    public String delete( @PathVariable("id") String id){
        if(this.accountService.delete(id)){
            return "redirect:/admin/account/list";
        }else{
            return "admin/account/list";
        }
    }
}
