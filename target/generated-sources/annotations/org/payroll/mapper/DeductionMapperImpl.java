package org.payroll.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.payroll.dto.DeductionDto;
import org.payroll.dto.EmployeeDto;
import org.payroll.model.DeductionEntity;
import org.payroll.model.EmployeeEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-15T14:28:27+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class DeductionMapperImpl implements DeductionMapper {

    @Override
    public DeductionDto toDto(DeductionEntity entity) {
        if ( entity == null ) {
            return null;
        }

        DeductionDto.DeductionDtoBuilder deductionDto = DeductionDto.builder();

        deductionDto.employee( employeeEntityToEmployeeDto( entity.getEmployee() ) );
        deductionDto.id( entity.getId() );
        deductionDto.externalRefId( entity.getExternalRefId() );
        deductionDto.deductionType( entity.getDeductionType() );
        deductionDto.amount( entity.getAmount() );
        deductionDto.period( entity.getPeriod() );
        deductionDto.description( entity.getDescription() );
        deductionDto.isApplied( entity.getIsApplied() );
        if ( entity.getAppliedDate() != null ) {
            deductionDto.appliedDate( DateTimeFormatter.ISO_LOCAL_DATE.format( entity.getAppliedDate() ) );
        }

        return deductionDto.build();
    }

    @Override
    public DeductionEntity toEntity(DeductionDto dto) {
        if ( dto == null ) {
            return null;
        }

        DeductionEntity.DeductionEntityBuilder deductionEntity = DeductionEntity.builder();

        deductionEntity.id( dto.getId() );
        deductionEntity.externalRefId( dto.getExternalRefId() );
        deductionEntity.deductionType( dto.getDeductionType() );
        deductionEntity.amount( dto.getAmount() );
        deductionEntity.period( dto.getPeriod() );
        deductionEntity.isApplied( dto.getIsApplied() );
        if ( dto.getAppliedDate() != null ) {
            deductionEntity.appliedDate( LocalDate.parse( dto.getAppliedDate() ) );
        }
        deductionEntity.description( dto.getDescription() );

        return deductionEntity.build();
    }

    @Override
    public List<DeductionDto> toDtoList(List<DeductionEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<DeductionDto> list = new ArrayList<DeductionDto>( entities.size() );
        for ( DeductionEntity deductionEntity : entities ) {
            list.add( toDto( deductionEntity ) );
        }

        return list;
    }

    protected EmployeeDto employeeEntityToEmployeeDto(EmployeeEntity employeeEntity) {
        if ( employeeEntity == null ) {
            return null;
        }

        EmployeeDto.EmployeeDtoBuilder employeeDto = EmployeeDto.builder();

        employeeDto.id( employeeEntity.getId() );
        employeeDto.nationalCode( employeeEntity.getNationalCode() );
        employeeDto.firstName( employeeEntity.getFirstName() );
        employeeDto.lastName( employeeEntity.getLastName() );
        employeeDto.isActive( employeeEntity.getIsActive() );
        if ( employeeEntity.getEmploymentDate() != null ) {
            employeeDto.employmentDate( DateTimeFormatter.ISO_LOCAL_DATE.format( employeeEntity.getEmploymentDate() ) );
        }

        return employeeDto.build();
    }
}
