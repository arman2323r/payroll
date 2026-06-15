package org.payroll.mapper;

import org.payroll.dto.PayrollDto;
import org.payroll.model.PayrollEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import java.util.List;

@Mapper(componentModel = "spring", uses = PayrollDetailMapper.class)
public interface PayrollMapper {
    @Mapping(source = "employee", target = "employee")
    @Mapping(source = "salaryDecree.id", target = "salaryDecreeId")
    @Mapping(source = "attendance.id", target = "attendanceId")
    PayrollDto toDto(PayrollEntity entity);

    @Mapping(source = "employee", target = "employee")
    @Mapping(target = "salaryDecree", ignore = true)
    @Mapping(target = "attendance", ignore = true)
    PayrollEntity toEntity(PayrollDto dto);

    List<PayrollDto> toDtoList(List<PayrollEntity> entities);
}