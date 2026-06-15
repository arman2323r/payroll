package org.payroll.dto;

import lombok.*;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyAttendanceDto {
    private Long id;
    private EmployeeDto employee;
    private String period;
    private BigDecimal daysPresent;
    private BigDecimal daysAbsent;
    private BigDecimal overtimeHours;
    private BigDecimal missionDays;
    private BigDecimal lateHours;
    private BigDecimal extraWorkHoliday;
}