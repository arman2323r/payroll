package org.payroll.controller;

import lombok.RequiredArgsConstructor;
import org.payroll.dto.EmployeeDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.payroll.dto.PayrollCalculateRequestDto;
import org.payroll.dto.PayrollDto;
import org.payroll.service.EmployeeService;
import org.payroll.service.PayrollService;

@Controller
@RequestMapping("/payrolls")
@RequiredArgsConstructor
public class PayrollController {

    private final PayrollService payrollService;
    private final EmployeeService employeeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("payrolls", payrollService.findAll());
        return "payroll/list";
    }

    @GetMapping("/calculate")
    public String showCalculateForm(Model model) {
        model.addAttribute("employees", employeeService.findAll());
        model.addAttribute("request", new PayrollCalculateRequestDto());
        return "payroll/calculate";
    }

    @PostMapping("/calculate")
    public String calculate(@ModelAttribute PayrollCalculateRequestDto request,
                            RedirectAttributes redirectAttributes,
                            Model model) {
        try {
            if (request.getEmployeeId() == null || request.getPeriod() == null) {
                redirectAttributes.addFlashAttribute("error", "لطفاً کارمند و دوره را انتخاب کنید");
                return "redirect:/payrolls/calculate";
            }
            EmployeeDto employee = employeeService.findById(request.getEmployeeId());
            PayrollDto result = payrollService.calculateAndSave(employee, request.getPeriod());
            model.addAttribute("payroll", result);
            return "payroll/result";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/payrolls/calculate";
        }
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable Long id, Model model) {
        PayrollDto payroll = payrollService.findById(id);
        model.addAttribute("payroll", payroll);
        return "payroll/view";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            payrollService.delete(id);
            redirectAttributes.addFlashAttribute("success", "فیش حقوقی با موفقیت حذف شد");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "خطا در حذف: " + e.getMessage());
        }
        return "redirect:/payrolls";
    }
}