package org.payroll.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "payroll")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "salary_decree_id")
    private SalaryDecreeEntity salaryDecree;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "attendance_id")
    private MonthlyAttendanceEntity attendance;

    @Column(nullable = false)
    private String period;

    @Column(nullable = false)
    private BigDecimal totalGross;

    @Column(nullable = false)
    private BigDecimal totalDeductions;

    @Column(nullable = false)
    private BigDecimal netPayable;

    @Builder.Default
    private String status = "DRAFT";

    @Column(nullable = false)
    private LocalDate calculationDate;

    @OneToMany(mappedBy = "payroll", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<PayrollDetailEntity> details = new ArrayList<>();
}