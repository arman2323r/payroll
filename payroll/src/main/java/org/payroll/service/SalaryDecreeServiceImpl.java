package org.payroll.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.payroll.dto.SalaryDecreeDto;
import org.payroll.mapper.SalaryDecreeMapper;
import org.payroll.model.EmployeeEntity;
import org.payroll.model.SalaryDecreeEntity;
import org.payroll.model.YearlyBasicSalaryEntity;
import org.payroll.repository.EmployeeRepository;
import org.payroll.repository.SalaryDecreeRepository;
import org.payroll.repository.YearlyBasicSalaryRepository;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SalaryDecreeServiceImpl implements SalaryDecreeService {

    private final SalaryDecreeRepository decreeRepository;
    private final EmployeeRepository employeeRepository;
    private final YearlyBasicSalaryRepository yearlyBasicSalaryRepository;
    private final SalaryDecreeMapper mapper;

    @Override
    public List<SalaryDecreeDto> findAll() {
        List<SalaryDecreeEntity> entities = decreeRepository.findAll();
        List<SalaryDecreeDto> dtos = new ArrayList<>();
        for (SalaryDecreeEntity entity : entities) {
            SalaryDecreeDto dto = mapper.toDto(entity);

            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public SalaryDecreeDto findById(Long id) {
        SalaryDecreeEntity entity = decreeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("حکم یافت نشد"));
        SalaryDecreeDto dto = mapper.toDto(entity);

        return dto;
    }

    @Override
    public void save(SalaryDecreeDto dto) {
        if (dto.getId() == null) {
            EmployeeEntity employee = employeeRepository.findById(dto.getEmployee().getId())
                    .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));

            LocalDate effectiveDate = dto.getEffectiveDate();
            if (effectiveDate == null) {
                effectiveDate = LocalDate.now();
            }

            int year = effectiveDate.getYear();
            YearlyBasicSalaryEntity yearly = yearlyBasicSalaryRepository.findByYear(year)
                    .orElseThrow(() -> new RuntimeException("حقوق پایه سال " + year + " (میلادی) ثبت نشده است"));

            SalaryDecreeEntity entity = SalaryDecreeEntity.builder()
                    .employee(employee)
                    .effectiveDate(effectiveDate)
                    .basicSalary(dto.getBasicSalary())
                    .housingAllowance(yearly.getHousingAllowance())
                    .breadAllowance(yearly.getBreadAllowance())
                    .otherBenefits(dto.getOtherBenefits() != null ? dto.getOtherBenefits() : BigDecimal.ZERO)
                    .isCurrent(true)
                    .build();

            decreeRepository.setPreviousDecreesNotCurrent(employee, -1L);
            decreeRepository.save(entity);
        } else {
            SalaryDecreeEntity existing = decreeRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("حکم یافت نشد"));
            existing.setBasicSalary(dto.getBasicSalary());
            existing.setOtherBenefits(dto.getOtherBenefits());
            if (dto.getEffectiveDate() != null) {
                existing.setEffectiveDate(dto.getEffectiveDate());
            }
            decreeRepository.save(existing);
        }
    }

    @Override
    public void delete(Long id) {
        decreeRepository.deleteById(id);
    }

    @Override
    public void createInitialDecreeForEmployee(Long employeeId) {
        EmployeeEntity employees = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));
        decreeRepository.setPreviousDecreesNotCurrent(employees, -1L);
        int currentYear = LocalDate.now().getYear();
        YearlyBasicSalaryEntity yearly = yearlyBasicSalaryRepository.findByYear(currentYear)
                .orElseThrow(() -> new RuntimeException("حقوق پایه سال " + currentYear + " (میلادی) ثبت نشده است"));
        SalaryDecreeEntity decree = SalaryDecreeEntity.builder()
                .employee(employees)
                .effectiveDate(LocalDate.now())
                .basicSalary(yearly.getBasicSalary())
                .housingAllowance(yearly.getHousingAllowance())
                .breadAllowance(yearly.getBreadAllowance())
                .otherBenefits(BigDecimal.ZERO)
                .isCurrent(true)
                .build();
        decreeRepository.save(decree);
    }

    @Override
    public SalaryDecreeEntity getCurrentDecree(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));
        return decreeRepository.findByEmployeeAndIsCurrentTrue(employee)
                .orElseThrow(() -> new RuntimeException("حکم جاری یافت نشد"));
    }
    @Override
    public List<SalaryDecreeDto> findByEmployeeId(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));
        List<SalaryDecreeEntity> salaryDecreeEntityList = decreeRepository.findByEmployee(employee);
        List<SalaryDecreeDto> salaryDecreeDtoListDtoList = new ArrayList<>();
        salaryDecreeDtoListDtoList = mapper.toDtoList(salaryDecreeEntityList);
        return salaryDecreeDtoListDtoList;
    }
}