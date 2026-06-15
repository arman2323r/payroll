package org.payroll.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PayrollDetailDto {
    private Long id;
    private Long payrollId;
    private String itemKey;
    private String description;
    private String itemType;
    private BigDecimal amount;
    private Integer sortOrder;
}