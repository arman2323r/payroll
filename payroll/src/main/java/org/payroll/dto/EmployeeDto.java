package org.payroll.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;

    @NotBlank(message = "کد ملی الزامی است")
    @Pattern(regexp = "^\\d{10}$", message = "کد ملی باید 10 رقم باشد")
    private String nationalCode;

    @NotBlank(message = "نام الزامی است")
    private String firstName;

    @NotBlank(message = "نام خانوادگی الزامی است")
    private String lastName;

    private Boolean isActive;

    @NotNull(message = "تاریخ استخدام الزامی است")
    private String employmentDate;

    private String effectiveDate;
    private String deductionMonth;
}