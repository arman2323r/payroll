package org.payroll.service;

import lombok.RequiredArgsConstructor;
import org.payroll.mapper.EmployeeMapper;
import org.payroll.model.EmployeeEntity;
import org.payroll.repository.EmployeeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.payroll.dto.*;
import org.payroll.mapper.PayrollDetailMapper;
import org.payroll.mapper.PayrollMapper;
import org.payroll.model.PayrollDetailEntity;
import org.payroll.model.PayrollEntity;
import org.payroll.repository.PayrollDetailRepository;
import org.payroll.repository.PayrollRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class PayrollServiceImpl implements PayrollService {

    private final PayrollRepository payrollRepository;
    private final PayrollDetailRepository detailRepository;
    private final EmployeeRepository employeeRepository;
    private final PayrollMapper payrollMapper;
    private final PayrollDetailMapper detailMapper;
    private final PayrollCalculationService calculationService;
    private final EmployeeService employeeService;
    private final EmployeeMapper employeeMapper;

    @Override
    public List<PayrollDto> findAll() {
        return payrollRepository.findAll().stream()
                .map(payrollMapper::toDto)
                .map(this::enrichWithEmployeeName)
                .collect(Collectors.toList());
    }

    @Override
    public PayrollDto findById(Long id) {
        PayrollEntity entity = payrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("فیش یافت نشد"));
        return enrichWithEmployeeName(payrollMapper.toDto(entity));
    }

    @Override
    public PayrollDto findByEmployeeAndPeriod(EmployeeDto employeeDto, String period) {
        EmployeeEntity employeeEnt = employeeMapper.toEntity(employeeDto);
        return payrollRepository.findByEmployeeAndPeriod(employeeEnt, period)
                .map(payrollMapper::toDto)
                .map(this::enrichWithEmployeeName)
                .orElse(null);
    }

    @Override
    public PayrollDto save(PayrollDto dto) {
        PayrollEntity entity = payrollMapper.toEntity(dto);
        entity = payrollRepository.save(entity);

        if (dto.getDetails() != null && !dto.getDetails().isEmpty()) {
            detailRepository.deleteByPayrollId(entity.getId());

            PayrollEntity finalEntity = entity;
            List<PayrollDetailEntity> details = dto.getDetails().stream()
                    .map(detailMapper::toEntity)
                    .peek(detail -> detail.setPayroll(finalEntity))
                    .collect(Collectors.toList());

            detailRepository.saveAll(details);
        }

        PayrollEntity savedEntity = payrollRepository.findById(entity.getId())
                .orElseThrow(() -> new RuntimeException("خطا در ذخیره فیش"));
        return enrichWithEmployeeName(payrollMapper.toDto(savedEntity));
    }

    @Override
    public void delete(Long id) {
        detailRepository.deleteByPayrollId(id);
        payrollRepository.deleteById(id);
    }

    @Override
    public PayrollDto calculateAndSave(EmployeeDto employee, String period) {
        PayrollDto calculated = calculationService.calculatePayroll(employee, period);
        List<PayrollDetailDto> details = buildDetailsFromCalculation(calculated);
        calculated.setDetails(details != null ? details : new ArrayList<>());

        BigDecimal totalEarnings = calculated.getDetails().stream()
                .filter(d -> "EARNING".equals(d.getItemType()))
                .map(PayrollDetailDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        BigDecimal totalDeductions = calculated.getDetails().stream()
                .filter(d -> "DEDUCTION".equals(d.getItemType()))
                .map(PayrollDetailDto::getAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        calculated.setTotalEarnings(totalEarnings);
        calculated.setTotalDeductions(totalDeductions);
        calculated.setNetPayable(totalEarnings.subtract(totalDeductions));

        return save(calculated);
    }

    @Override
    public List<PayrollDetailDto> buildDetailsFromCalculation(PayrollDto calculated) {
        List<PayrollDetailDto> list = new ArrayList<>();
        if (calculated == null) return list;
        int order = 1;

        addDetail(list, order++, "BASIC_SALARY", "حقوق پایه", "EARNING", calculated.getBasicSalary());
        addDetail(list, order++, "HOUSING_ALLOWANCE", "حق مسکن", "EARNING", calculated.getHousingAllowance());
        addDetail(list, order++, "BREAD_ALLOWANCE", "بن کارگری", "EARNING", calculated.getBreadAllowance());
        addDetail(list, order++, "OVERTIME_PAY", "اضافه کار", "EARNING", calculated.getOvertimePay());
        addDetail(list, order++, "OTHER_BENEFITS", "سایر مزایا", "EARNING", calculated.getOtherBenefits());
        addDetail(list, order++, "INSURANCE", "بیمه", "DEDUCTION", calculated.getInsuranceAmount());
        addDetail(list, order++, "TAX", "مالیات", "DEDUCTION", calculated.getTaxAmount());
        addDetail(list, order++, "OTHER_DEDUCTIONS", "سایر کسورات", "DEDUCTION", calculated.getTotalDeductions());

        return list;
    }

    private void addDetail(List<PayrollDetailDto> list, int order, String key, String desc, String type, BigDecimal amount) {
        if (amount != null && amount.compareTo(BigDecimal.ZERO) != 0) {
            PayrollDetailDto d = PayrollDetailDto.builder()
                    .itemKey(key)
                    .description(desc)
                    .itemType(type)
                    .amount(amount)
                    .sortOrder(order)
                    .build();
            list.add(d);
        }
    }

    private PayrollDto enrichWithEmployeeName(PayrollDto dto) {
        if (dto == null || dto.getEmployee() == null || dto.getEmployee().getId() == null) return dto;
        try {
            EmployeeDto emp = employeeService.findById(dto.getEmployee().getId());
            if (emp != null) {
                dto.getEmployee().setFirstName(emp.getFirstName());
                dto.getEmployee().setLastName(emp.getLastName());
            }
        } catch (Exception e) {

        }
        return dto;
    }

    @Override
    public List<PayrollDto> findByEmployeeId(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));
        List<PayrollEntity> payrollEntityList = payrollRepository.findByEmployee(employee);
        return payrollMapper.toDtoList(payrollEntityList);
    }
}