package org.payroll.dto;

import lombok.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BulkDeductionRequestDto {
    private List<DeductionDto> deductions;
}