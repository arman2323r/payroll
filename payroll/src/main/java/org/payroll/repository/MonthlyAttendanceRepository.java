package org.payroll.repository;

import org.payroll.model.EmployeeEntity;
import org.payroll.model.MonthlyAttendanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface MonthlyAttendanceRepository extends JpaRepository<MonthlyAttendanceEntity, Long> {
    Optional<MonthlyAttendanceEntity> findByEmployeeAndPeriod(EmployeeEntity employee, String period);
    List<MonthlyAttendanceEntity> findByPeriod(String period);
    List<MonthlyAttendanceEntity> findByEmployee(EmployeeEntity employee);
}