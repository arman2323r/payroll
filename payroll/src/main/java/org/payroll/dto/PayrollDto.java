package org.payroll.dto;

import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class    PayrollDto {
    private Long id;
    private EmployeeDto employee;
    private Long salaryDecreeId;
    private Long attendanceId;
    private String period;
    private BigDecimal totalGross;
    private BigDecimal totalDeductions;
    private BigDecimal netPayable;
    private String status;
    private LocalDate calculationDate;

    @Builder.Default
    private List<PayrollDetailDto> details = new ArrayList<>();

    private BigDecimal basicSalary;
    private BigDecimal housingAllowance;
    private BigDecimal breadAllowance;
    private BigDecimal overtimePay;
    private BigDecimal otherBenefits;
    private BigDecimal insuranceAmount;
    private BigDecimal taxAmount;
    private BigDecimal totalEarnings;
}