package org.payroll.repository;

import org.payroll.model.YearlyBasicSalaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface YearlyBasicSalaryRepository extends JpaRepository<YearlyBasicSalaryEntity, Long> {
    Optional<YearlyBasicSalaryEntity> findByYear(Integer year);
}