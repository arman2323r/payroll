package org.payroll.service;

import org.payroll.dto.EmployeeDto;
import org.payroll.dto.PayrollDetailDto;
import org.payroll.dto.PayrollDto;
import java.util.List;

public interface PayrollService {
    List<PayrollDto> findAll();
    PayrollDto findById(Long id);
    PayrollDto findByEmployeeAndPeriod(EmployeeDto employeeDto, String period);
    PayrollDto save(PayrollDto dto);
    void delete(Long id);
    PayrollDto calculateAndSave(EmployeeDto employee, String period);

    Object findByEmployeeId(Long empId);

    List<PayrollDetailDto> buildDetailsFromCalculation(PayrollDto calculated);
}