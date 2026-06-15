package org.payroll.service;

import org.payroll.dto.EmployeeDto;
import org.payroll.dto.PayrollDto;

public interface PayrollCalculationService {
    PayrollDto calculatePayroll(EmployeeDto employee, String period);
}