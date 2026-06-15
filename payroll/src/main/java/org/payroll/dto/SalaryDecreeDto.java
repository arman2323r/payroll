package org.payroll.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryDecreeDto {
    private Long id;
    private EmployeeDto employee;
    private LocalDate effectiveDate;
    private BigDecimal basicSalary;
    private BigDecimal housingAllowance;
    private BigDecimal breadAllowance;
    private BigDecimal otherBenefits;
    private Boolean isCurrent;
}