package org.payroll.service;

import org.payroll.dto.MonthlyAttendanceDto;
import java.util.List;
import java.util.Optional;

public interface MonthlyAttendanceService {
    List<MonthlyAttendanceDto> findAll();
    Optional<MonthlyAttendanceDto> findById(Long id);
    Optional<MonthlyAttendanceDto> findByEmployeeAndPriod(Long employeeId, String period);
    MonthlyAttendanceDto save(MonthlyAttendanceDto dto);
    MonthlyAttendanceDto update(Long id, MonthlyAttendanceDto dto);
    void delete(Long id);
    List<MonthlyAttendanceDto> findByYearAndMonth(String period);

    Object findByEmployeeId(Long empId);
}