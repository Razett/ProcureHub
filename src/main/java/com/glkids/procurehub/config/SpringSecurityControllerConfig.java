package com.glkids.procurehub.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Configuration
public class SpringSecurityControllerConfig {

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        if (error != null) {
            model.addAttribute("errorMsg", "Invalid username or password.");
        }

        if (logout != null) {
            model.addAttribute("logoutMsg", "You have been logged out successfully.");
        }

        return "/login"; // login.html (Thymeleaf 템플릿)
    }

    @GetMapping("/")
    public String homePage() {
        return "/index"; // home.html (로그인 후의 홈 페이지)
    }
}
