package org.payroll.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "monthly_attendance")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyAttendanceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @Column(nullable = false)
    private String period;

    private BigDecimal daysPresent;
    private BigDecimal daysAbsent;
    private BigDecimal overtimeHours;
    private BigDecimal missionDays;
    private BigDecimal lateHours;
    private BigDecimal extraWorkHoliday;
}