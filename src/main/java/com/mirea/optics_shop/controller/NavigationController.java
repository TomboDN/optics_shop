package com.mirea.optics_shop.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class NavigationController {
    @Resource
    private SessionRegistry sessionRegistry;

    @GetMapping("/")
    public String home(HttpServletResponse response, Authentication authentication, Model model){
        if (authentication == null){
            return "index";
        }
        setDummyCookie(response);
        List<SessionInformation> sessions = sessionRegistry.getAllSessions
                (authentication.getPrincipal(), false);
        model.addAttribute("currentSession", sessions);
        return "index";
    }

    private void setDummyCookie(HttpServletResponse response){
        Cookie cookie = new Cookie("dummyCookie", "dummy_cookie");
        cookie.setMaxAge(2592000);
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}