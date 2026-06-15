package org.payroll.mapper;

import org.payroll.dto.PayrollDetailDto;
import org.payroll.model.PayrollDetailEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface PayrollDetailMapper {

    @Mapping(source = "payroll.id", target = "payrollId")
    @Mapping(source = "componentName", target = "description")
    @Mapping(source = "type", target = "itemType")
    PayrollDetailDto toDto(PayrollDetailEntity entity);

    @Mapping(target = "payroll", ignore = true)
    @Mapping(source = "description", target = "componentName")
    @Mapping(source = "itemType", target = "type")
    PayrollDetailEntity toEntity(PayrollDetailDto dto);

    List<PayrollDetailDto> toDtoList(List<PayrollDetailEntity> entities);
    List<PayrollDetailEntity> toEntityList(List<PayrollDetailDto> dtos);
}