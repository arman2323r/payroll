package org.payroll.service;

import org.payroll.dto.YearlyBasicSalaryDto;
import org.payroll.model.YearlyBasicSalaryEntity;
import java.util.List;

public interface YearlyBasicSalaryService {
    List<YearlyBasicSalaryDto> findAll();
    YearlyBasicSalaryDto findById(Long id);
    YearlyBasicSalaryDto save(YearlyBasicSalaryDto dto);

    YearlyBasicSalaryDto update(YearlyBasicSalaryDto dto);

    void delete(Long id);
    YearlyBasicSalaryDto getByYear(Integer year);
    YearlyBasicSalaryEntity findEntityByYear(Integer year);
}