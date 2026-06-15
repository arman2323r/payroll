package org.payroll.repository;

import org.payroll.model.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByNationalCode(String nationalCode);
    List<EmployeeEntity> findByIsActiveTrue();
}