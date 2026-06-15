package org.payroll.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "yearly_basic_salary")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class YearlyBasicSalaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private Integer year;

    @Column(nullable = false)
    private BigDecimal basicSalary;

    @Column(nullable = false)
    private BigDecimal housingAllowance;

    @Column(nullable = false)
    private BigDecimal breadAllowance;

    @Column(nullable = false)
    private BigDecimal insurancePercent;

}