package org.payroll.service;

import org.payroll.dto.DeductionDto;
import java.math.BigDecimal;
import java.util.List;

public interface DeductionService {
    List<DeductionDto> findAll();
    DeductionDto findById(Long id);
    List<DeductionDto> findByEmployeeId(Long employeeId);
    List<DeductionDto> findByEmployeeAndPeriod(Long employeeId, String period);
    BigDecimal sumDeductionsByEmployeeAndPeriod(Long employeeId, String period);
    DeductionDto save(DeductionDto dto);
    DeductionDto update(Long id, DeductionDto dto);
    void delete(Long id);
    void receiveDeductions(List<DeductionDto> dtos);
}