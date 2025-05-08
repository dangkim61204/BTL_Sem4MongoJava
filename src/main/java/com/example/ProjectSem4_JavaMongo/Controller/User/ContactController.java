package com.example.ProjectSem4_JavaMongo.Controller.User;

import ch.qos.logback.core.model.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ContactController {
    @RequestMapping("/contact")
    public String contact(Model model) {
        return "/user/contact";
    }
}
