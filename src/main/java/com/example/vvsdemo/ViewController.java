package com.example.vvsdemo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@Controller
public class ViewController {

    @RequestMapping("/")
    public String index(Model model){
        model.addAttribute("datetime",new Date());
        model.addAttribute("username","Tit Radu-Dorin");
        model.addAttribute("mode","production");

        return "index";

    }

}
