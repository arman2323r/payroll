package org.payroll.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.payroll.dto.YearlyBasicSalaryDto;
import org.payroll.mapper.YearlyBasicSalaryMapper;
import org.payroll.model.YearlyBasicSalaryEntity;
import org.payroll.repository.YearlyBasicSalaryRepository;
import org.payroll.DateUtils;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class YearlyBasicSalaryServiceImpl implements YearlyBasicSalaryService {

    private final YearlyBasicSalaryRepository repository;
    private final YearlyBasicSalaryMapper mapper;

    @Override
    public List<YearlyBasicSalaryDto> findAll() {
        return mapper.toDtoList(repository.findAll());
    }

    @Override
    public YearlyBasicSalaryDto findById(Long id) {
        YearlyBasicSalaryEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("رکورد حقوق پایه سالانه با شناسه " + id + " یافت نشد"));
        return mapper.toDto(entity);
    }

    @Override
    public YearlyBasicSalaryDto save(YearlyBasicSalaryDto dto) {
        if (dto.getShamsiYear() != null && dto.getYear() == null) {
            int miladiYear = DateUtils.shamsiYearToMiladi(dto.getShamsiYear());
            dto.setYear(miladiYear);
        } else if (dto.getShamsiYear() != null && dto.getYear() != null) {
            int calculatedMiladi = DateUtils.shamsiYearToMiladi(dto.getShamsiYear());
            if (calculatedMiladi != dto.getYear()) {
                throw new IllegalArgumentException("سال شمسی و میلادی با هم تطابق ندارند");
            }
        }

        if (repository.findByYear(dto.getYear()).isPresent()) {
            YearlyBasicSalaryEntity existing = repository.findByYear(dto.getYear()).get();
            if (dto.getId() == null || !existing.getId().equals(dto.getId())) {
                throw new RuntimeException("حقوق پایه سال " + dto.getYear() + " (میلادی) قبلاً ثبت شده است");
            }
        }

        YearlyBasicSalaryEntity entity;
        if (dto.getId() != null) {

            entity = repository.findById(dto.getId())
                    .orElseThrow(() -> new RuntimeException("رکورد حقوق پایه سالانه یافت نشد"));
            entity.setYear(dto.getYear());
            entity.setBasicSalary(dto.getBasicSalary());
            entity.setHousingAllowance(dto.getHousingAllowance());
            entity.setBreadAllowance(dto.getBreadAllowance());
            entity.setInsurancePercent(dto.getInsurancePercent());
        } else {
            entity = mapper.toEntity(dto);
        }

        entity = repository.save(entity);
        return mapper.toDto(entity);
    }

    @Override
    public YearlyBasicSalaryDto update(YearlyBasicSalaryDto dto) {
        if (dto.getId() == null) {
            throw new RuntimeException("شناسه برای بروزرسانی الزامی است");
        }

        YearlyBasicSalaryEntity existing = repository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("رکورد حقوق پایه سالانه یافت نشد"));

        if (dto.getShamsiYear() != null) {
            int miladiYear = DateUtils.shamsiYearToMiladi(dto.getShamsiYear());
            dto.setYear(miladiYear);
        } else if (dto.getYear() == null) {
            throw new RuntimeException("سال (میلادی) باید مشخص شود");
        }

        if (!existing.getYear().equals(dto.getYear()) && repository.findByYear(dto.getYear()).isPresent()) {
            throw new RuntimeException("سال " + dto.getYear() + " (میلادی) قبلاً در رکورد دیگری ثبت شده است");
        }

        existing.setYear(dto.getYear());
        existing.setBasicSalary(dto.getBasicSalary());
        existing.setHousingAllowance(dto.getHousingAllowance());
        existing.setBreadAllowance(dto.getBreadAllowance());
        existing.setInsurancePercent(dto.getInsurancePercent());

        existing = repository.save(existing);
        return mapper.toDto(existing);
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("رکورد حقوق پایه سالانه با شناسه " + id + " یافت نشد");
        }
        repository.deleteById(id);
    }

    @Override
    public YearlyBasicSalaryDto getByYear(Integer miladiYear) {
        YearlyBasicSalaryEntity entity = repository.findByYear(miladiYear)
                .orElseThrow(() -> new RuntimeException("حقوق پایه سال میلادی " + miladiYear + " یافت نشد"));
        return mapper.toDto(entity);
    }

    @Override
    public YearlyBasicSalaryEntity findEntityByYear(Integer miladiYear) {
        return repository.findByYear(miladiYear)
                .orElseThrow(() -> new RuntimeException("حقوق پایه سال میلادی " + miladiYear + " یافت نشد"));
    }
}