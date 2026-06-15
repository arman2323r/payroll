package org.payroll.model;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "payroll_detail")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PayrollDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "payroll_id", nullable = false)
    private PayrollEntity payroll;

    @Column(nullable = false)
    private String componentName;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = true, length = 10)
    private String type;

}