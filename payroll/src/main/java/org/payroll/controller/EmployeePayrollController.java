package org.payroll.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.payroll.dto.PayrollDto;
import org.payroll.model.UserEntity;
import org.payroll.service.UserService;
import org.payroll.service.PayrollService;

@Controller
@RequestMapping("/employee/payroll")
@RequiredArgsConstructor
public class EmployeePayrollController {

    private final PayrollService payrollService;
    private final UserService userService;

    private UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username).orElse(null);
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        PayrollDto payroll = payrollService.findById(id);

        UserEntity currentUser = getCurrentUser();
        if (currentUser != null && currentUser.getEmployee() != null) {
            if (!payroll.getEmployee().getId().equals(currentUser.getEmployee().getId())) {
                return "redirect:/access-denied";
            }
        }

        model.addAttribute("payroll", payroll);
        return "employee/payroll-view";
    }
}