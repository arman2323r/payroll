package org.payroll.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.payroll.dto.EmployeeDto;
import org.payroll.model.EmployeeEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-15T14:28:27+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class EmployeeMapperImpl implements EmployeeMapper {

    @Override
    public EmployeeDto toDto(EmployeeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        EmployeeDto.EmployeeDtoBuilder employeeDto = EmployeeDto.builder();

        employeeDto.id( entity.getId() );
        employeeDto.nationalCode( entity.getNationalCode() );
        employeeDto.firstName( entity.getFirstName() );
        employeeDto.lastName( entity.getLastName() );
        employeeDto.isActive( entity.getIsActive() );
        if ( entity.getEmploymentDate() != null ) {
            employeeDto.employmentDate( DateTimeFormatter.ISO_LOCAL_DATE.format( entity.getEmploymentDate() ) );
        }

        return employeeDto.build();
    }

    @Override
    public EmployeeEntity toEntity(EmployeeDto dto) {
        if ( dto == null ) {
            return null;
        }

        EmployeeEntity.EmployeeEntityBuilder employeeEntity = EmployeeEntity.builder();

        employeeEntity.id( dto.getId() );
        employeeEntity.nationalCode( dto.getNationalCode() );
        employeeEntity.firstName( dto.getFirstName() );
        employeeEntity.lastName( dto.getLastName() );
        if ( dto.getEmploymentDate() != null ) {
            employeeEntity.employmentDate( LocalDate.parse( dto.getEmploymentDate() ) );
        }
        employeeEntity.isActive( dto.getIsActive() );

        return employeeEntity.build();
    }

    @Override
    public List<EmployeeDto> toDtoList(List<EmployeeEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<EmployeeDto> list = new ArrayList<EmployeeDto>( entities.size() );
        for ( EmployeeEntity employeeEntity : entities ) {
            list.add( toDto( employeeEntity ) );
        }

        return list;
    }
}
