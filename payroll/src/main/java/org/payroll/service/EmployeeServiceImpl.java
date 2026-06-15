package org.payroll.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.payroll.dto.EmployeeDto;
import org.payroll.mapper.EmployeeMapper;
import org.payroll.model.EmployeeEntity;
import org.payroll.repository.*;
import org.payroll.DateUtils;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final SalaryDecreeService salaryDecreeService;
    private final SalaryDecreeRepository salaryDecreeRepository;
    private final DeductionRepository deductionRepository;
    private final MonthlyAttendanceRepository attendanceRepository;
    private final PayrollRepository payrollRepository;

    @Override
    public List<EmployeeDto> findAll() {
        return employeeMapper.toDtoList(employeeRepository.findAll());
    }

    @Override
    public EmployeeDto findById(Long id) {
        EmployeeEntity entity = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("کارمند با شناسه " + id + " یافت نشد"));
        return employeeMapper.toDto(entity);
    }

    @Override
    public EmployeeDto save(EmployeeDto dto) {
        if (dto.getId() == null) {
            checkDuplicateNationalCode(dto.getNationalCode());
        } else {
            EmployeeEntity existing = employeeRepository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));
            if (!existing.getNationalCode().equals(dto.getNationalCode())) {
                checkDuplicateNationalCode(dto.getNationalCode());
            }
        }
        EmployeeEntity entity = employeeMapper.toEntity(dto);

        if (dto.getEmploymentDate() != null && !dto.getEmploymentDate().isEmpty()) {
            try {
                if (dto.getEmploymentDate().contains("/")) {
                    LocalDate miladiDate = DateUtils.shamsiToMiladi(dto.getEmploymentDate());
                    entity.setEmploymentDate(miladiDate);
                } else {
                    entity.setEmploymentDate(LocalDate.parse(dto.getEmploymentDate()));
                }
            } catch (Exception e) {
                System.out.println("Error parsing date: " + e.getMessage());
            }
        }

        entity = employeeRepository.save(entity);
        return employeeMapper.toDto(entity);
    }

    @Override
    public EmployeeDto hireEmployee(EmployeeDto dto) {
        checkDuplicateNationalCode(dto.getNationalCode());
        EmployeeEntity entity = employeeMapper.toEntity(dto);
        entity.setEmploymentDate(LocalDate.now());
        entity.setIsActive(true);
        EmployeeEntity saved = employeeRepository.save(entity);
        salaryDecreeService.createInitialDecreeForEmployee(saved.getId());
        return employeeMapper.toDto(saved);
    }

    @Override
    public EmployeeDto updateEmployee(Long id, EmployeeDto dto) {
        EmployeeEntity existing = employeeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("کارمند با شناسه " + id + " یافت نشد"));

        if (!existing.getNationalCode().equals(dto.getNationalCode())) {
            checkDuplicateNationalCode(dto.getNationalCode());
        }

        existing.setFirstName(dto.getFirstName());
        existing.setLastName(dto.getLastName());
        existing.setNationalCode(dto.getNationalCode());

        if (dto.getEmploymentDate() != null && !dto.getEmploymentDate().isEmpty()) {
            try {
                if (dto.getEmploymentDate().contains("/")) {
                    LocalDate miladiDate = DateUtils.shamsiToMiladi(dto.getEmploymentDate());
                    existing.setEmploymentDate(miladiDate);
                } else {
                    existing.setEmploymentDate(LocalDate.parse(dto.getEmploymentDate()));
                }
            } catch (Exception e) {
                System.out.println("Error parsing date in update: " + e.getMessage());
            }
        }

        existing.setIsActive(dto.getIsActive());

        existing = employeeRepository.save(existing);
        return employeeMapper.toDto(existing);
    }

    @Override
    public void delete(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public List<EmployeeDto> getAllActiveEmployees() {
        return employeeMapper.toDtoList(employeeRepository.findByIsActiveTrue());
    }

    @Override
    public boolean hasRelatedRecords(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));

        return !salaryDecreeRepository.findByEmployee(employee).isEmpty() ||
                !deductionRepository.findByEmployee(employee).isEmpty() ||
                !attendanceRepository.findByEmployee(employee).isEmpty() ||
                !payrollRepository.findByEmployee(employee).isEmpty();
    }

    @Override
    public Map<String, Integer> getRelatedRecordsCount(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));

        Map<String, Integer> counts = new HashMap<>();
        counts.put("decrees", salaryDecreeRepository.findByEmployee(employee).size());
        counts.put("deductions", deductionRepository.findByEmployee(employee).size());
        counts.put("attendances", attendanceRepository.findByEmployee(employee).size());
        counts.put("payrolls", payrollRepository.findByEmployee(employee).size());

        return counts;
    }

    private void checkDuplicateNationalCode(String nationalCode) {
        if (employeeRepository.findByNationalCode(nationalCode).isPresent()) {
            throw new RuntimeException("کارمندی با کد ملی " + nationalCode + " قبلاً ثبت شده است");
        }
    }
}