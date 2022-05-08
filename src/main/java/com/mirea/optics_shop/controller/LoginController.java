package com.mirea.optics_shop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @GetMapping("/login")
    public String login(@RequestParam(value = "invalid-session", defaultValue = "false") boolean invalidSession,
                        final Model model){
        if(invalidSession){
            model.addAttribute("invalidSession", "У вас уже есть активная сессия. Несколько активных сессий запрещены");
        }
        return "login";
    }
}
