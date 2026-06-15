package org.payroll.service;

import lombok.RequiredArgsConstructor;
import org.payroll.mapper.SalaryDecreeMapper;
import org.springframework.stereotype.Service;
import org.payroll.dto.*;
import org.payroll.model.SalaryDecreeEntity;
import org.payroll.model.YearlyBasicSalaryEntity;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PayrollCalculationServiceImpl implements PayrollCalculationService {
    private final SalaryDecreeService salaryDecreeService;
    private final YearlyBasicSalaryService yearlyBasicSalaryService;
    private final MonthlyAttendanceService attendanceService;
    private final DeductionService deductionService;
    private final SalaryDecreeMapper salaryDecreeMapper;

    @Override
    public PayrollDto calculatePayroll(EmployeeDto employee, String period) {
        SalaryDecreeEntity decreeEntity = salaryDecreeService.getCurrentDecree(employee.getId());
        SalaryDecreeDto decree = salaryDecreeMapper.toDto(decreeEntity);

        MonthlyAttendanceDto attendance = attendanceService.findByEmployeeAndPriod(employee.getId(), period)
                .orElseThrow(() -> new RuntimeException("اطلاعات حضور و غیاب ثبت نشده"));

        BigDecimal totalDeductions = deductionService.sumDeductionsByEmployeeAndPeriod(employee.getId(), period);
        BigDecimal overtimePay = calculateOvertimePay(decree, attendance.getOvertimeHours());

        BigDecimal insuranceBase = decree.getBasicSalary()
                .add(decree.getHousingAllowance())
                .add(decree.getBreadAllowance())
                .add(decree.getOtherBenefits() != null ? decree.getOtherBenefits() : BigDecimal.ZERO)
                .add(overtimePay);

        int currentYear = LocalDate.now().getYear();
        YearlyBasicSalaryEntity yearly = yearlyBasicSalaryService.findEntityByYear(currentYear);
        BigDecimal insurancePercent = yearly.getInsurancePercent().divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);
        BigDecimal insuranceAmount = insuranceBase.multiply(insurancePercent).setScale(0, RoundingMode.HALF_UP);
        BigDecimal taxAmount = calculateTax(insuranceBase.subtract(insuranceAmount));
        BigDecimal netPay = insuranceBase.subtract(insuranceAmount).subtract(taxAmount).subtract(totalDeductions);

        return PayrollDto.builder()
                .employee(employee)
                .period(period)
                .basicSalary(decree.getBasicSalary())
                .housingAllowance(decree.getHousingAllowance())
                .breadAllowance(decree.getBreadAllowance())
                .overtimePay(overtimePay)
                .otherBenefits(decree.getOtherBenefits())
                .insuranceAmount(insuranceAmount)
                .taxAmount(taxAmount)
                .totalGross(insuranceBase)
                .totalDeductions(totalDeductions.add(insuranceAmount).add(taxAmount))
                .netPayable(netPay)
                .status("CALCULATED")
                .calculationDate(LocalDate.now())
                .build();
    }

    private BigDecimal calculateOvertimePay(SalaryDecreeDto decree, BigDecimal overtimeHours) {
        if (overtimeHours == null || overtimeHours.compareTo(BigDecimal.ZERO) <= 0) return BigDecimal.ZERO;
        BigDecimal dailyRate = decree.getBasicSalary().divide(BigDecimal.valueOf(30), 2, RoundingMode.HALF_UP);
        BigDecimal hourlyRate = dailyRate.divide(BigDecimal.valueOf(8), 2, RoundingMode.HALF_UP);
        return hourlyRate.multiply(BigDecimal.valueOf(1.4)).multiply(overtimeHours).setScale(0, RoundingMode.HALF_UP);
    }

    private BigDecimal calculateTax(BigDecimal taxableIncome) {
        if (taxableIncome.compareTo(new BigDecimal("10000000")) <= 0) return BigDecimal.ZERO;
        return taxableIncome.subtract(new BigDecimal("10000000")).multiply(BigDecimal.valueOf(0.10)).setScale(0, RoundingMode.HALF_UP);
    }
}