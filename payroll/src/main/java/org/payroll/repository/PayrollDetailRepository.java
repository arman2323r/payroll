package org.payroll.repository;

import org.payroll.model.PayrollDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayrollDetailRepository extends JpaRepository<PayrollDetailEntity, Long> {
    void deleteByPayrollId(Long payrollId);
}