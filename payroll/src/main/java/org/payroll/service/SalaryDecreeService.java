package org.payroll.service;

import org.payroll.dto.SalaryDecreeDto;
import org.payroll.model.SalaryDecreeEntity;
import java.util.List;

public interface SalaryDecreeService {
    List<SalaryDecreeDto> findAll();
    SalaryDecreeDto findById(Long id);
    void save(SalaryDecreeDto dto);
    void delete(Long id);
    void createInitialDecreeForEmployee(Long employeeId);
    SalaryDecreeEntity getCurrentDecree(Long employeeId);

    Object findByEmployeeId(Long empId);
}