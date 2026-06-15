package org.payroll.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.payroll.dto.DeductionDto;
import org.payroll.service.DeductionService;
import org.payroll.service.EmployeeService;

@Controller
@RequestMapping("/deductions")
@RequiredArgsConstructor
public class DeductionController {

    private final DeductionService deductionService;
    private final EmployeeService employeeService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("deductions", deductionService.findAll());
        return "deduction/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("deduction", new DeductionDto());
        model.addAttribute("employees", employeeService.findAll());
        return "deduction/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        DeductionDto deduction = deductionService.findById(id);
        model.addAttribute("deduction", deduction);
        model.addAttribute("employees", employeeService.findAll());
        return "deduction/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute DeductionDto dto, RedirectAttributes redirectAttributes) {
        try {
            if (dto.getId() == null) {
                deductionService.save(dto);
                redirectAttributes.addFlashAttribute("success", "کسر با موفقیت ثبت شد");
            } else {
                deductionService.update(dto.getId(), dto);
                redirectAttributes.addFlashAttribute("success", "کسر با موفقیت بروزرسانی شد");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/deductions";
    }

    @GetMapping("/bulk")
    public String showBulkForm(Model model) {
        model.addAttribute("bulkRequest", new org.payroll.dto.BulkDeductionRequestDto());
        return "deduction/bulk";
    }

    @PostMapping("/bulk")
    public String receiveBulk(@ModelAttribute org.payroll.dto.BulkDeductionRequestDto request) {
        deductionService.receiveDeductions(request.getDeductions());
        return "redirect:/deductions";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            deductionService.delete(id);
            redirectAttributes.addFlashAttribute("success", "کسر با موفقیت حذف شد");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "خطا در حذف: " + e.getMessage());
        }
        return "redirect:/deductions";
    }
}