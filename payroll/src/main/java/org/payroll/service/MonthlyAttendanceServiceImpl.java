package org.payroll.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.payroll.dto.MonthlyAttendanceDto;
import org.payroll.mapper.MonthlyAttendanceMapper;
import org.payroll.model.EmployeeEntity;
import org.payroll.model.MonthlyAttendanceEntity;
import org.payroll.repository.EmployeeRepository;
import org.payroll.repository.MonthlyAttendanceRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class MonthlyAttendanceServiceImpl implements MonthlyAttendanceService {

    private final MonthlyAttendanceRepository repository;
    private final EmployeeRepository employeeRepository;
    private final MonthlyAttendanceMapper mapper;

    @Override
    public List<MonthlyAttendanceDto> findAll() {
        return repository.findAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MonthlyAttendanceDto> findById(Long id) {
        return repository.findById(id).map(mapper::toDto);
    }

    @Override
    public Optional<MonthlyAttendanceDto> findByEmployeeAndPriod(Long employeeId, String period) {
        Optional<EmployeeEntity> employeeOpt = employeeRepository.findById(employeeId);
        if (employeeOpt.isEmpty()) return Optional.empty();
        return repository.findByEmployeeAndPeriod(employeeOpt.get(), period)
                .map(mapper::toDto);
    }

    @Override
    public MonthlyAttendanceDto save(MonthlyAttendanceDto dto) {
        if (dto.getEmployee().getId() == null) throw new RuntimeException("شناسه کارمند الزامی است");
        if (dto.getPeriod() == null) throw new RuntimeException("دوره الزامی هستند");

        EmployeeEntity employee = employeeRepository.findById(dto.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));

        Optional<MonthlyAttendanceEntity> existing = repository.findByEmployeeAndPeriod(employee, dto.getPeriod());
        MonthlyAttendanceEntity entity;

        if (existing.isPresent()) {
            entity = existing.get();
            entity.setDaysPresent(dto.getDaysPresent() != null ? dto.getDaysPresent() : BigDecimal.ZERO);
            entity.setDaysAbsent(dto.getDaysAbsent() != null ? dto.getDaysAbsent() : BigDecimal.ZERO);
            entity.setOvertimeHours(dto.getOvertimeHours() != null ? dto.getOvertimeHours() : BigDecimal.ZERO);
            entity.setMissionDays(dto.getMissionDays() != null ? dto.getMissionDays() : BigDecimal.ZERO);
            entity.setLateHours(dto.getLateHours() != null ? dto.getLateHours() : BigDecimal.ZERO);
            entity.setExtraWorkHoliday(dto.getExtraWorkHoliday() != null ? dto.getExtraWorkHoliday() : BigDecimal.ZERO);
        } else {
            entity = new MonthlyAttendanceEntity();
            entity.setEmployee(employee);
            entity.setPeriod(dto.getPeriod());
            entity.setDaysPresent(dto.getDaysPresent() != null ? dto.getDaysPresent() : BigDecimal.ZERO);
            entity.setDaysAbsent(dto.getDaysAbsent() != null ? dto.getDaysAbsent() : BigDecimal.ZERO);
            entity.setOvertimeHours(dto.getOvertimeHours() != null ? dto.getOvertimeHours() : BigDecimal.ZERO);
            entity.setMissionDays(dto.getMissionDays() != null ? dto.getMissionDays() : BigDecimal.ZERO);
            entity.setLateHours(dto.getLateHours() != null ? dto.getLateHours() : BigDecimal.ZERO);
            entity.setExtraWorkHoliday(dto.getExtraWorkHoliday() != null ? dto.getExtraWorkHoliday() : BigDecimal.ZERO);
        }

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public MonthlyAttendanceDto update(Long id, MonthlyAttendanceDto dto) {
        if (!repository.existsById(id)) throw new RuntimeException("رکورد حضور و غیاب با شناسه " + id + " یافت نشد");
        dto.setId(id);
        return save(dto);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public List<MonthlyAttendanceDto> findByYearAndMonth(String period) {
        return repository.findByPeriod(period).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
    @Override
    public List<MonthlyAttendanceDto> findByEmployeeId(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));
        List<MonthlyAttendanceEntity> monthlyAttendanceEntityList = repository.findByEmployee(employee);
        List<MonthlyAttendanceDto> monthlyAttendanceDtoList = new ArrayList<>();
        monthlyAttendanceDtoList = mapper.toDtoList(monthlyAttendanceEntityList);
        return monthlyAttendanceDtoList;
    }
}