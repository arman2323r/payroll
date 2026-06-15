package org.payroll.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.payroll.dto.SalaryDecreeDto;
import org.payroll.service.EmployeeService;
import org.payroll.service.SalaryDecreeService;

import java.util.List;

@Controller
@RequestMapping("/decrees")
@RequiredArgsConstructor
public class SalaryDecreeController {

    private final SalaryDecreeService decreeService;
    private final EmployeeService employeeService;

    @GetMapping
    public String list(Model model) {
        List<SalaryDecreeDto> decrees = decreeService.findAll();
        model.addAttribute("decrees", decrees);
        return "decree/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("decree", new SalaryDecreeDto());
        model.addAttribute("employees", employeeService.findAll());
        return "decree/form";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        SalaryDecreeDto decree = decreeService.findById(id);
        model.addAttribute("decree", decree);
        model.addAttribute("employees", employeeService.findAll());
        return "decree/form";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute SalaryDecreeDto decree, RedirectAttributes redirectAttributes) {
        try {
            decreeService.save(decree);
            redirectAttributes.addFlashAttribute("success", "حکم با موفقیت ذخیره شد");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            if (decree.getId() == null) {
                return "redirect:/decrees/new";
            } else {
                return "redirect:/decrees/edit/" + decree.getId();
            }
        }
        return "redirect:/decrees";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            decreeService.delete(id);
            redirectAttributes.addFlashAttribute("success", "حکم با موفقیت حذف شد");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "خطا در حذف حکم: " + e.getMessage());
        }
        return "redirect:/decrees";
    }

    @PostMapping("/initial/{employeeId}")
    public String createInitial(@PathVariable Long employeeId, RedirectAttributes redirectAttributes) {
        try {
            decreeService.createInitialDecreeForEmployee(employeeId);
            redirectAttributes.addFlashAttribute("success", "حکم اولیه کارمند صادر شد");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }
        return "redirect:/decrees";
    }
}