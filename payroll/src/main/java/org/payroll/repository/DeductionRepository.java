package org.payroll.repository;

import org.payroll.model.DeductionEntity;
import org.payroll.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.List;

public interface DeductionRepository extends JpaRepository<DeductionEntity, Long> {
    List<DeductionEntity> findByEmployeeAndPeriodAndIsAppliedFalse(EmployeeEntity employee, String Period);
    List<DeductionEntity> findByEmployeeAndPeriod(EmployeeEntity employee, String period);
    @Query("SELECT COALESCE(SUM(d.amount), 0) FROM DeductionEntity d WHERE d.employee = :employee AND d.period = :period AND d.isApplied = false")
    BigDecimal sumUnappliedByEmployeeAndPeriod(@Param("employee") EmployeeEntity employee, @Param("period") String period);
    List<DeductionEntity> findByEmployee(EmployeeEntity employee);
}