package org.payroll.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Entity
@Table(name = "deduction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DeductionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeEntity employee;

    @Column(name = "external_ref_id", nullable = false)
    private String externalRefId;

    @Column(nullable = false, length = 20)
    private String deductionType;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(name = "period", nullable = false)
    private String period;

    @Builder.Default
    private Boolean isApplied = false;

    private LocalDate appliedDate;

    private String description;
}