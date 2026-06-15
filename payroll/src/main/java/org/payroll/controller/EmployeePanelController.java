package org.payroll.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.payroll.model.UserEntity;
import org.payroll.service.UserService;
import org.payroll.service.PayrollService;
import org.payroll.service.SalaryDecreeService;
import org.payroll.service.MonthlyAttendanceService;

import java.util.ArrayList;

@Controller
@RequestMapping("/employee")
@RequiredArgsConstructor
public class EmployeePanelController {

    private final UserService userService;
    private final SalaryDecreeService decreeService;
    private final PayrollService payrollService;
    private final MonthlyAttendanceService attendanceService;

    private UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username).orElse(null);
    }

    private Long getCurrentEmployeeId() {
        UserEntity user = getCurrentUser();
        if (user != null && user.getEmployee() != null) {
            return user.getEmployee().getId();
        }
        return null;
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "employee/dashboard";
    }

    @GetMapping("/profile")
    public String profile(Model model) {
        UserEntity user = getCurrentUser();
        if (user != null && user.getEmployee() != null) {
            model.addAttribute("employee", user.getEmployee());
        } else {
            model.addAttribute("employee", null);
            model.addAttribute("error", "اطلاعات کارمند یافت نشد");
        }
        return "employee/profile";
    }

    @GetMapping("/decrees")
    public String decrees(Model model) {
        Long employeeId = getCurrentEmployeeId();
        if (employeeId != null) {
            model.addAttribute("decrees", decreeService.findByEmployeeId(employeeId));
        } else {
            model.addAttribute("decrees", new ArrayList<>());
            model.addAttribute("error", "هیچ حکمی برای شما ثبت نشده است");
        }
        return "employee/decrees";
    }

    @GetMapping("/payrolls")
    public String payrolls(Model model) {
        Long employeeId = getCurrentEmployeeId();
        if (employeeId != null) {
            model.addAttribute("payrolls", payrollService.findByEmployeeId(employeeId));
        } else {
            model.addAttribute("payrolls", new ArrayList<>());
            model.addAttribute("error", "هیچ فیش حقوقی برای شما ثبت نشده است");
        }
        return "employee/payrolls";
    }

    @GetMapping("/attendances")
    public String attendances(Model model) {
        Long employeeId = getCurrentEmployeeId();
        if (employeeId != null) {
            model.addAttribute("attendances", attendanceService.findByEmployeeId(employeeId));
        } else {
            model.addAttribute("attendances", new ArrayList<>());
            model.addAttribute("error", "هیچ رکوردی برای شما ثبت نشده است");
        }
        return "employee/attendances";
    }
}