package org.payroll.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.payroll.model.UserEntity;
import org.payroll.service.UserService;

@Configuration
@RequiredArgsConstructor
public class DataInitializer {

    private final UserService userService;

    @Bean
    public CommandLineRunner initUsers() {
        return args -> {
            if (!userService.existsByUsername("admin")) {
                UserEntity admin = UserEntity.builder()
                        .username("admin")
                        .password("admin123")
                        .role("ADMIN")
                        .isActive(true)
                        .passwordChanged(true)
                        .build();
                userService.save(admin);
                System.out.println("✅ کاربر ادمین ایجاد شد - username: admin, password: admin123");
            }
        };
    }
}