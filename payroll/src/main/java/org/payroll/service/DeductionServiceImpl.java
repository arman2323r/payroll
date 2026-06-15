package org.payroll.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.payroll.dto.DeductionDto;
import org.payroll.mapper.DeductionMapper;
import org.payroll.model.DeductionEntity;
import org.payroll.model.EmployeeEntity;
import org.payroll.repository.DeductionRepository;
import org.payroll.repository.EmployeeRepository;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class DeductionServiceImpl implements DeductionService {

    private final DeductionRepository deductionRepository;
    private final EmployeeRepository employeeRepository;
    private final DeductionMapper mapper;

    @Override
    public List<DeductionDto> findAll() {
        List<DeductionEntity> entities = deductionRepository.findAll();
        List<DeductionDto> dtos = new ArrayList<>();
        for (DeductionEntity entity : entities) {
            DeductionDto dto = mapper.toDto(entity);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public DeductionDto findById(Long id) {
        DeductionEntity entity = deductionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("کسری با شناسه " + id + " یافت نشد"));
        return mapper.toDto(entity);
    }

    @Override
    public List<DeductionDto> findByEmployeeId(Long employeeId) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));
        List<DeductionEntity> entities = deductionRepository.findByEmployee(employee);
        List<DeductionDto> dtos = new ArrayList<>();
        for (DeductionEntity entity : entities) {
            DeductionDto dto = mapper.toDto(entity);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public List<DeductionDto> findByEmployeeAndPeriod(Long employeeId, String period) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));
        List<DeductionEntity> entities = deductionRepository.findByEmployeeAndPeriod(employee, period);
        List<DeductionDto> dtos = new ArrayList<>();
        for (DeductionEntity entity : entities) {
            DeductionDto dto = mapper.toDto(entity);
            dtos.add(dto);
        }
        return dtos;
    }

    @Override
    public BigDecimal sumDeductionsByEmployeeAndPeriod(Long employeeId, String period) {
        EmployeeEntity employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("کارمند یافت نشد"));
        return deductionRepository.sumUnappliedByEmployeeAndPeriod(employee, period);
    }

    @Override
    public DeductionDto save(DeductionDto dto) {
        EmployeeEntity employee = employeeRepository.findById(dto.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("کارمند با این مشخصات یافت نشد"));

        DeductionEntity entity = DeductionEntity.builder()
                .employee(employee)
                .externalRefId(dto.getExternalRefId())
                .deductionType(dto.getDeductionType())
                .amount(dto.getAmount())
                .period(dto.getPeriod())
                .description(dto.getDescription())
                .isApplied(false)
                .build();

        entity = deductionRepository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public DeductionDto update(Long id, DeductionDto dto) {
        DeductionEntity existing = deductionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("کسری با شناسه " + id + " یافت نشد"));

        EmployeeEntity employee = employeeRepository.findById(dto.getEmployee().getId())
                .orElseThrow(() -> new RuntimeException("کارمند با این مشخصات یافت نشد"));

        existing.setEmployee(employee);
        existing.setExternalRefId(dto.getExternalRefId());
        existing.setDeductionType(dto.getDeductionType());
        existing.setAmount(dto.getAmount());
        existing.setPeriod(dto.getPeriod());
        existing.setDescription(dto.getDescription());

        existing = deductionRepository.save(existing);
        return mapper.toDto(existing);
    }

    @Override
    public void delete(Long id) {
        if (!deductionRepository.existsById(id)) {
            throw new RuntimeException("کسری با شناسه " + id + " یافت نشد");
        }
        deductionRepository.deleteById(id);
    }

    @Override
    public void receiveDeductions(List<DeductionDto> dtos) {
        for (DeductionDto dto : dtos) {
            EmployeeEntity employee = employeeRepository.findByNationalCode(dto.getEmployee().getNationalCode())
                    .orElseThrow(() -> new IllegalArgumentException("کارمند با کد ملی " + dto.getEmployee().getNationalCode() + " یافت نشد"));

            DeductionEntity deduction = DeductionEntity.builder()
                    .employee(employee)
                    .externalRefId(dto.getExternalRefId())
                    .deductionType(dto.getDeductionType())
                    .amount(dto.getAmount())
                    .period(dto.getPeriod())
                    .description(dto.getDescription())
                    .isApplied(false)
                    .build();
            deductionRepository.save(deduction);
        }
    }
}