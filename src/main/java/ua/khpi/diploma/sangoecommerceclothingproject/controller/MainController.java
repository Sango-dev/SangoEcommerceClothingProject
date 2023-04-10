package ua.khpi.diploma.sangoecommerceclothingproject.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
    @RequestMapping({"", "/"})
    public String initialPage() {
        return "redirect:/product/list?page=0";
    }

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @RequestMapping("/login-error")
    public String loginError(Model model) {
        model.addAttribute("loginError", true);
        model.addAttribute("errorMessage", "Wrong nickname or password!!!");
        return "login";
    }
}