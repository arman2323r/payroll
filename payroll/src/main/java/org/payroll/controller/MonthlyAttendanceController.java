package org.payroll.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.payroll.dto.MonthlyAttendanceDto;
import org.payroll.service.EmployeeService;
import org.payroll.service.MonthlyAttendanceService;

@Controller
@RequestMapping("/attendances")
@RequiredArgsConstructor
public class MonthlyAttendanceController {

    private final MonthlyAttendanceService attendanceService;
    private final EmployeeService employeeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("attendances", attendanceService.findAll());
        return "attendance/list";
    }

    @GetMapping("/new")
    public String showForm(Model model) {
        model.addAttribute("attendance", new MonthlyAttendanceDto());
        model.addAttribute("employees", employeeService.findAll());
        return "attendance/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        MonthlyAttendanceDto attendance = attendanceService.findById(id)
                .orElseThrow(() -> new RuntimeException("رکورد حضور و غیاب یافت نشد"));
        model.addAttribute("attendance", attendance);
        model.addAttribute("employees", employeeService.findAll());
        return "attendance/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MonthlyAttendanceDto dto, RedirectAttributes ra) {
        try {
            if (dto.getId() == null) {
                attendanceService.save(dto);
                ra.addFlashAttribute("success", "کارکرد ماهانه ذخیره شد");
            } else {
                attendanceService.update(dto.getId(), dto);
                ra.addFlashAttribute("success", "کارکرد ماهانه بروزرسانی شد");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/attendances";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes ra) {
        try {
            attendanceService.delete(id);
            ra.addFlashAttribute("success", "رکورد حذف شد");
        } catch (Exception e) {
            ra.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/attendances";
    }
}