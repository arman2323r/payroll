package org.payroll.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.payroll.dto.EmployeeDto;
import org.payroll.dto.PayrollDto;
import org.payroll.model.EmployeeEntity;
import org.payroll.model.MonthlyAttendanceEntity;
import org.payroll.model.PayrollEntity;
import org.payroll.model.SalaryDecreeEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-15T14:28:27+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class PayrollMapperImpl implements PayrollMapper {

    @Autowired
    private PayrollDetailMapper payrollDetailMapper;

    @Override
    public PayrollDto toDto(PayrollEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PayrollDto.PayrollDtoBuilder payrollDto = PayrollDto.builder();

        payrollDto.employee( employeeEntityToEmployeeDto( entity.getEmployee() ) );
        payrollDto.salaryDecreeId( entitySalaryDecreeId( entity ) );
        payrollDto.attendanceId( entityAttendanceId( entity ) );
        payrollDto.id( entity.getId() );
        payrollDto.period( entity.getPeriod() );
        payrollDto.totalGross( entity.getTotalGross() );
        payrollDto.totalDeductions( entity.getTotalDeductions() );
        payrollDto.netPayable( entity.getNetPayable() );
        payrollDto.status( entity.getStatus() );
        payrollDto.calculationDate( entity.getCalculationDate() );
        payrollDto.details( payrollDetailMapper.toDtoList( entity.getDetails() ) );

        return payrollDto.build();
    }

    @Override
    public PayrollEntity toEntity(PayrollDto dto) {
        if ( dto == null ) {
            return null;
        }

        PayrollEntity.PayrollEntityBuilder payrollEntity = PayrollEntity.builder();

        payrollEntity.employee( employeeDtoToEmployeeEntity( dto.getEmployee() ) );
        payrollEntity.id( dto.getId() );
        payrollEntity.period( dto.getPeriod() );
        payrollEntity.totalGross( dto.getTotalGross() );
        payrollEntity.totalDeductions( dto.getTotalDeductions() );
        payrollEntity.netPayable( dto.getNetPayable() );
        payrollEntity.status( dto.getStatus() );
        payrollEntity.calculationDate( dto.getCalculationDate() );
        payrollEntity.details( payrollDetailMapper.toEntityList( dto.getDetails() ) );

        return payrollEntity.build();
    }

    @Override
    public List<PayrollDto> toDtoList(List<PayrollEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PayrollDto> list = new ArrayList<PayrollDto>( entities.size() );
        for ( PayrollEntity payrollEntity : entities ) {
            list.add( toDto( payrollEntity ) );
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

    private Long entitySalaryDecreeId(PayrollEntity payrollEntity) {
        if ( payrollEntity == null ) {
            return null;
        }
        SalaryDecreeEntity salaryDecree = payrollEntity.getSalaryDecree();
        if ( salaryDecree == null ) {
            return null;
        }
        Long id = salaryDecree.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    private Long entityAttendanceId(PayrollEntity payrollEntity) {
        if ( payrollEntity == null ) {
            return null;
        }
        MonthlyAttendanceEntity attendance = payrollEntity.getAttendance();
        if ( attendance == null ) {
            return null;
        }
        Long id = attendance.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected EmployeeEntity employeeDtoToEmployeeEntity(EmployeeDto employeeDto) {
        if ( employeeDto == null ) {
            return null;
        }

        EmployeeEntity.EmployeeEntityBuilder employeeEntity = EmployeeEntity.builder();

        employeeEntity.id( employeeDto.getId() );
        employeeEntity.nationalCode( employeeDto.getNationalCode() );
        employeeEntity.firstName( employeeDto.getFirstName() );
        employeeEntity.lastName( employeeDto.getLastName() );
        if ( employeeDto.getEmploymentDate() != null ) {
            employeeEntity.employmentDate( LocalDate.parse( employeeDto.getEmploymentDate() ) );
        }
        employeeEntity.isActive( employeeDto.getIsActive() );

        return employeeEntity.build();
    }
}
