package org.payroll.mapper;

import org.payroll.dto.SalaryDecreeDto;
import org.payroll.model.SalaryDecreeEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface SalaryDecreeMapper {

    @Mapping(source = "employee", target = "employee")
    SalaryDecreeDto toDto(SalaryDecreeEntity entity);

    @Mapping(target= "employee", ignore = true)
    SalaryDecreeEntity toEntity(SalaryDecreeDto dto);

    List<SalaryDecreeDto> toDtoList(List<SalaryDecreeEntity> entities);

}