package org.payroll.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.payroll.dto.EmployeeDto;
import org.payroll.service.EmployeeService;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping
    public String list(Model model) {
        List<EmployeeDto> employees = employeeService.getAllActiveEmployees();
        model.addAttribute("employees", employees);
        return "employee/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("employee", new EmployeeDto());
        return "employee/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        EmployeeDto employee = employeeService.findById(id);
        model.addAttribute("employee", employee);
        return "employee/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute EmployeeDto employee,
                       BindingResult result,
                       RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            String errorMessage = result.getFieldError().getDefaultMessage();
            redirectAttributes.addFlashAttribute("error", errorMessage);
            if (employee.getId() == null) {
                return "redirect:/employees/new";
            } else {
                return "redirect:/employees/edit/" + employee.getId();
            }
        }

        try {
            if (employee.getId() == null) {
                employeeService.hireEmployee(employee);
                redirectAttributes.addFlashAttribute("success", "کارمند با موفقیت ثبت شد");
            } else {
                employeeService.updateEmployee(employee.getId(), employee);
                redirectAttributes.addFlashAttribute("success", "کارمند با موفقیت بروزرسانی شد");
            }
            return "redirect:/employees";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            if (employee.getId() == null) {
                return "redirect:/employees/new";
            } else {
                return "redirect:/employees/edit/" + employee.getId();
            }
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            if (employeeService.hasRelatedRecords(id)) {
                Map<String, Integer> counts = employeeService.getRelatedRecordsCount(id);
                StringBuilder message = new StringBuilder("❌ امکان حذف کارمند وجود ندارد. ابتدا موارد زیر را حذف کنید: ");

                if (counts.get("decrees") > 0) {
                    message.append(counts.get("decrees")).append(" حکم، ");
                }
                if (counts.get("deductions") > 0) {
                    message.append(counts.get("deductions")).append(" کسر، ");
                }
                if (counts.get("attendances") > 0) {
                    message.append(counts.get("attendances")).append(" حضور و غیاب، ");
                }
                if (counts.get("payrolls") > 0) {
                    message.append(counts.get("payrolls")).append(" فیش حقوقی، ");
                }

                redirectAttributes.addFlashAttribute("error", message.toString());
                return "redirect:/employees";
            }

            employeeService.delete(id);
            redirectAttributes.addFlashAttribute("success", "کارمند با موفقیت حذف شد");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "خطا در حذف کارمند: " + e.getMessage());
        }
        return "redirect:/employees";
    }
}