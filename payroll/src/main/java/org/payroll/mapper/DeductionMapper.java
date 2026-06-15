package org.payroll.mapper;

import org.payroll.dto.DeductionDto;
import org.payroll.model.DeductionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface DeductionMapper {

    @Mapping(source = "employee", target = "employee")
    DeductionDto toDto(DeductionEntity entity);

    @Mapping(target = "employee", ignore = true)
    DeductionEntity toEntity(DeductionDto dto);

    List<DeductionDto> toDtoList(List<DeductionEntity> entities);

}