package org.payroll.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeductionDto {
    private Long id;
    private EmployeeDto employee;
    private String externalRefId;
    private String deductionType;
    private BigDecimal amount;
    private String period;
    private String description;
    private Boolean isApplied;
    private String appliedDate;
}