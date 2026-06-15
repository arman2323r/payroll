package org.payroll.repository;

import org.payroll.model.EmployeeEntity;
import org.payroll.model.SalaryDecreeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface SalaryDecreeRepository extends JpaRepository<SalaryDecreeEntity, Long> {
    Optional<SalaryDecreeEntity> findByEmployeeAndIsCurrentTrue(EmployeeEntity employee);
    @Query("SELECT d FROM SalaryDecreeEntity d WHERE d.employee = :employee AND d.effectiveDate <= :date ORDER BY d.effectiveDate DESC")
    Optional<SalaryDecreeEntity> findLatestValidDecreeByDate(@Param("employee") EmployeeEntity employee, @Param("date") LocalDate date);
    @Modifying
    @Query("UPDATE SalaryDecreeEntity d SET d.isCurrent = false WHERE d.employee = :employee AND d.id != :decreeId")
    void setPreviousDecreesNotCurrent(@Param("employee") EmployeeEntity employee, @Param("decreeId") Long decreeId);
    List<SalaryDecreeEntity> findByEmployee(EmployeeEntity employee);
}