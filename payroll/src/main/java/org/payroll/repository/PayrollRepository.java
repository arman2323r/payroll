package org.payroll.repository;

import org.payroll.model.EmployeeEntity;
import org.payroll.model.PayrollEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface PayrollRepository extends JpaRepository<PayrollEntity, Long> {
    Optional<PayrollEntity> findByEmployeeAndPeriod(EmployeeEntity employee, String period);
    List<PayrollEntity> findByEmployee(EmployeeEntity employee);

}