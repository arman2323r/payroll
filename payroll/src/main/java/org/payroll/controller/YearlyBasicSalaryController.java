package org.payroll.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.payroll.dto.YearlyBasicSalaryDto;
import org.payroll.service.YearlyBasicSalaryService;
import org.payroll.DateUtils;

@Controller
@RequestMapping("/salary")
@RequiredArgsConstructor
public class YearlyBasicSalaryController {

    private final YearlyBasicSalaryService service;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("salaries", service.findAll());
        return "salary/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        YearlyBasicSalaryDto dto = new YearlyBasicSalaryDto();
        dto.setShamsiYear(DateUtils.miladiYearToShamsi(java.time.LocalDate.now().getYear()));
        model.addAttribute("salary", dto);
        return "salary/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute YearlyBasicSalaryDto salary, RedirectAttributes redirectAttributes) {
        try {
            if (salary.getShamsiYear() != null) {
                int miladiYear = DateUtils.shamsiYearToMiladi(salary.getShamsiYear());
                salary.setYear(miladiYear);
            }

            if (salary.getId() != null) {
                service.update(salary);
                redirectAttributes.addFlashAttribute("success", "حقوق پایه سالانه با موفقیت بروزرسانی شد");
            } else {
                service.save(salary);
                redirectAttributes.addFlashAttribute("success", "حقوق پایه سالانه با موفقیت ثبت شد");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            if (salary.getId() != null) {
                return "redirect:/salary/edit/" + salary.getId();
            }
            return "redirect:/salary/new";
        }
        return "redirect:/salary";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        YearlyBasicSalaryDto salary = service.findById(id);
        if (salary.getYear() != null) {
            salary.setShamsiYear(DateUtils.miladiYearToShamsi(salary.getYear()));
        }
        model.addAttribute("salary", salary);
        return "salary/form";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            service.delete(id);
            redirectAttributes.addFlashAttribute("success", "حقوق پایه سالانه حذف شد");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "خطا در حذف: " + e.getMessage());
        }
        return "redirect:/salary";
    }
}