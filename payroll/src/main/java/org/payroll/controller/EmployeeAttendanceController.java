package org.payroll.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.payroll.dto.MonthlyAttendanceDto;
import org.payroll.model.UserEntity;
import org.payroll.service.UserService;
import org.payroll.service.MonthlyAttendanceService;

@Controller
@RequestMapping("/employee/attendance")
@RequiredArgsConstructor
public class EmployeeAttendanceController {

    private final MonthlyAttendanceService attendanceService;
    private final UserService userService;

    private UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username).orElse(null);
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        MonthlyAttendanceDto attendance = attendanceService.findById(id)
                .orElseThrow(() -> new RuntimeException("رکورد حضور و غیاب یافت نشد"));

        UserEntity currentUser = getCurrentUser();

        if (currentUser != null && currentUser.getEmployee() != null && attendance.getEmployee() != null) {
            if (!attendance.getEmployee().getId().equals(currentUser.getEmployee().getId())) {
                return "redirect:/access-denied";
            }
        }

        model.addAttribute("attendance", attendance);
        return "employee/attendance-view";
    }
}