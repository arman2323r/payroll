package org.payroll.mapper;

import org.payroll.dto.YearlyBasicSalaryDto;
import org.payroll.model.YearlyBasicSalaryEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface YearlyBasicSalaryMapper {
    YearlyBasicSalaryDto toDto(YearlyBasicSalaryEntity entity);
    YearlyBasicSalaryEntity toEntity(YearlyBasicSalaryDto dto);
    List<YearlyBasicSalaryDto> toDtoList(List<YearlyBasicSalaryEntity> entities);
}