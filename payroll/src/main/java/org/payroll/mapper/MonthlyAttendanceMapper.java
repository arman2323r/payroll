package org.payroll.mapper;

import org.payroll.dto.MonthlyAttendanceDto;
import org.payroll.model.MonthlyAttendanceEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MonthlyAttendanceMapper {

    @Mapping(source = "employee", target = "employee")
    MonthlyAttendanceDto toDto(MonthlyAttendanceEntity entity);

    @Mapping(target = "employee", ignore = true)
    MonthlyAttendanceEntity toEntity(MonthlyAttendanceDto dto);

    List<MonthlyAttendanceDto> toDtoList(List<MonthlyAttendanceEntity> entities);


}