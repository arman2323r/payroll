package org.payroll.service;

import org.payroll.dto.EmployeeDto;
import java.util.List;
import java.util.Map;

public interface EmployeeService {
    List<EmployeeDto> findAll();
    EmployeeDto findById(Long id);
    EmployeeDto save(EmployeeDto dto);
    EmployeeDto hireEmployee(EmployeeDto dto);
    EmployeeDto updateEmployee(Long id, EmployeeDto dto);
    void delete(Long id);
    List<EmployeeDto> getAllActiveEmployees();
    boolean hasRelatedRecords(Long employeeId);
    Map<String, Integer> getRelatedRecordsCount(Long employeeId);
}