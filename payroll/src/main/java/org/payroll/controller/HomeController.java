package org.payroll.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.payroll.model.UserEntity;
import org.payroll.service.UserService;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;

    @GetMapping("/")
    public String home() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth != null && auth.isAuthenticated() && !(auth.getPrincipal() instanceof String)) {
            String username = auth.getName();
            UserEntity user = userService.findByUsername(username).orElse(null);

            if (user != null && Boolean.FALSE.equals(user.getPasswordChanged())) {
                return "redirect:/change-password";
            }

            if (auth.getAuthorities() != null) {
                for (GrantedAuthority authority : auth.getAuthorities()) {
                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                        return "admin/dashboard";
                    } else if (authority.getAuthority().equals("ROLE_EMPLOYEE")) {
                        return "employee/dashboard";
                    }
                }
            }
        }

        return "redirect:/login";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard() {
        return "admin/dashboard";
    }
}