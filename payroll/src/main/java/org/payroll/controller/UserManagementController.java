package org.payroll.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.payroll.model.UserEntity;
import org.payroll.service.UserService;
import org.payroll.service.EmployeeService;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin/user")
@RequiredArgsConstructor
public class UserManagementController {

    private final UserService userService;
    private final EmployeeService employeeService;

    @GetMapping("/list")
    public String listUsers(Model model) {
        List<UserEntity> allUsers = userService.findAll();
        List<UserEntity> employees = allUsers.stream()
                .filter(u -> "EMPLOYEE".equals(u.getRole()))
                .collect(Collectors.toList());
        model.addAttribute("users", employees);
        return "admin/user/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserEntity());
        model.addAttribute("employees", employeeService.findAll());
        return "admin/user/form";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute UserEntity user, RedirectAttributes ra) {
        try {
            user.setRole("EMPLOYEE");
            userService.save(user);
            ra.addFlashAttribute("success", "کاربر با موفقیت ایجاد شد");
        } catch (Exception e) {
            ra.addFlashAttribute("error", "خطا: " + e.getMessage());
        }
        return "redirect:/admin/user/list";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id, RedirectAttributes ra) {
        try {
            UserEntity user = userService.findById(id).orElse(null);
            if (user != null && "ADMIN".equals(user.getRole())) {
                ra.addFlashAttribute("error", "امکان حذف کاربر ادمین وجود ندارد");
            } else {
                userService.delete(id);
                ra.addFlashAttribute("success", "کاربر با موفقیت حذف شد");
            }
        } catch (Exception e) {
            ra.addFlashAttribute("error", "خطا در حذف: " + e.getMessage());
        }
        return "redirect:/admin/user/list";
    }
}