package org.payroll.mapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.payroll.dto.EmployeeDto;
import org.payroll.dto.SalaryDecreeDto;
import org.payroll.model.EmployeeEntity;
import org.payroll.model.SalaryDecreeEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-15T14:28:27+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class SalaryDecreeMapperImpl implements SalaryDecreeMapper {

    @Override
    public SalaryDecreeDto toDto(SalaryDecreeEntity entity) {
        if ( entity == null ) {
            return null;
        }

        SalaryDecreeDto.SalaryDecreeDtoBuilder salaryDecreeDto = SalaryDecreeDto.builder();

        salaryDecreeDto.employee( employeeEntityToEmployeeDto( entity.getEmployee() ) );
        salaryDecreeDto.id( entity.getId() );
        salaryDecreeDto.effectiveDate( entity.getEffectiveDate() );
        salaryDecreeDto.basicSalary( entity.getBasicSalary() );
        salaryDecreeDto.housingAllowance( entity.getHousingAllowance() );
        salaryDecreeDto.breadAllowance( entity.getBreadAllowance() );
        salaryDecreeDto.otherBenefits( entity.getOtherBenefits() );
        salaryDecreeDto.isCurrent( entity.getIsCurrent() );

        return salaryDecreeDto.build();
    }

    @Override
    public SalaryDecreeEntity toEntity(SalaryDecreeDto dto) {
        if ( dto == null ) {
            return null;
        }

        SalaryDecreeEntity.SalaryDecreeEntityBuilder salaryDecreeEntity = SalaryDecreeEntity.builder();

        salaryDecreeEntity.id( dto.getId() );
        salaryDecreeEntity.effectiveDate( dto.getEffectiveDate() );
        salaryDecreeEntity.basicSalary( dto.getBasicSalary() );
        salaryDecreeEntity.housingAllowance( dto.getHousingAllowance() );
        salaryDecreeEntity.breadAllowance( dto.getBreadAllowance() );
        salaryDecreeEntity.otherBenefits( dto.getOtherBenefits() );
        salaryDecreeEntity.isCurrent( dto.getIsCurrent() );

        return salaryDecreeEntity.build();
    }

    @Override
    public List<SalaryDecreeDto> toDtoList(List<SalaryDecreeEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<SalaryDecreeDto> list = new ArrayList<SalaryDecreeDto>( entities.size() );
        for ( SalaryDecreeEntity salaryDecreeEntity : entities ) {
            list.add( toDto( salaryDecreeEntity ) );
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
