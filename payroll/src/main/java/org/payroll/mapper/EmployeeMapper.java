package org.payroll.mapper;

import org.payroll.dto.EmployeeDto;
import org.payroll.model.EmployeeEntity;
import org.mapstruct.Mapper;
import java.util.List;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto toDto(EmployeeEntity entity);
    EmployeeEntity toEntity(EmployeeDto dto);
    List<EmployeeDto> toDtoList(List<EmployeeEntity> entities);
}