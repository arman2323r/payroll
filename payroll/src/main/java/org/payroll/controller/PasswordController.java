package org.payroll.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.payroll.model.UserEntity;
import org.payroll.service.UserService;

@Controller
@RequiredArgsConstructor
public class PasswordController {

    private final UserService userService;

    private UserEntity getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userService.findByUsername(username).orElse(null);
    }

    @GetMapping("/change-password")
    public String showChangePasswordForm(Model model) {
        UserEntity user = getCurrentUser();
        if (user != null && Boolean.TRUE.equals(user.getPasswordChanged())) {
            return "redirect:/";
        }
        model.addAttribute("mustChange", true);
        return "change-password";
    }

    @PostMapping("/change-password")
    public String changePassword(@RequestParam String newPassword,
                                 @RequestParam String confirmPassword,
                                 RedirectAttributes ra) {
        UserEntity user = getCurrentUser();

        if (user == null) {
            return "redirect:/login";
        }

        if (!newPassword.equals(confirmPassword)) {
            ra.addFlashAttribute("error", "رمز عبور جدید و تکرار آن مطابقت ندارند");
            return "redirect:/change-password";
        }

        if (newPassword.length() < 4) {
            ra.addFlashAttribute("error", "رمز عبور جدید باید حداقل 4 کاراکتر باشد");
            return "redirect:/change-password";
        }

        userService.updatePassword(user.getId(), newPassword);

        ra.addFlashAttribute("success", "رمز عبور با موفقیت تغییر کرد");
        return "redirect:/";
    }
}