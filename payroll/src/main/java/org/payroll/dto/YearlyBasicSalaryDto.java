package org.payroll.dto;

import jakarta.persistence.Column;
import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class YearlyBasicSalaryDto {
    private Long id;
    private Integer year;
    private Integer shamsiYear;
    private BigDecimal basicSalary;
    private BigDecimal housingAllowance;
    private BigDecimal breadAllowance;
    private BigDecimal insurancePercent;
}