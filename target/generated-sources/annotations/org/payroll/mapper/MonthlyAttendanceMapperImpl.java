package org.payroll.mapper;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.payroll.dto.EmployeeDto;
import org.payroll.dto.MonthlyAttendanceDto;
import org.payroll.model.EmployeeEntity;
import org.payroll.model.MonthlyAttendanceEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-15T14:28:27+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class MonthlyAttendanceMapperImpl implements MonthlyAttendanceMapper {

    @Override
    public MonthlyAttendanceDto toDto(MonthlyAttendanceEntity entity) {
        if ( entity == null ) {
            return null;
        }

        MonthlyAttendanceDto.MonthlyAttendanceDtoBuilder monthlyAttendanceDto = MonthlyAttendanceDto.builder();

        monthlyAttendanceDto.employee( employeeEntityToEmployeeDto( entity.getEmployee() ) );
        monthlyAttendanceDto.id( entity.getId() );
        monthlyAttendanceDto.period( entity.getPeriod() );
        monthlyAttendanceDto.daysPresent( entity.getDaysPresent() );
        monthlyAttendanceDto.daysAbsent( entity.getDaysAbsent() );
        monthlyAttendanceDto.overtimeHours( entity.getOvertimeHours() );
        monthlyAttendanceDto.missionDays( entity.getMissionDays() );
        monthlyAttendanceDto.lateHours( entity.getLateHours() );
        monthlyAttendanceDto.extraWorkHoliday( entity.getExtraWorkHoliday() );

        return monthlyAttendanceDto.build();
    }

    @Override
    public MonthlyAttendanceEntity toEntity(MonthlyAttendanceDto dto) {
        if ( dto == null ) {
            return null;
        }

        MonthlyAttendanceEntity monthlyAttendanceEntity = new MonthlyAttendanceEntity();

        monthlyAttendanceEntity.setId( dto.getId() );
        monthlyAttendanceEntity.setPeriod( dto.getPeriod() );
        monthlyAttendanceEntity.setDaysPresent( dto.getDaysPresent() );
        monthlyAttendanceEntity.setDaysAbsent( dto.getDaysAbsent() );
        monthlyAttendanceEntity.setOvertimeHours( dto.getOvertimeHours() );
        monthlyAttendanceEntity.setMissionDays( dto.getMissionDays() );
        monthlyAttendanceEntity.setLateHours( dto.getLateHours() );
        monthlyAttendanceEntity.setExtraWorkHoliday( dto.getExtraWorkHoliday() );

        return monthlyAttendanceEntity;
    }

    @Override
    public List<MonthlyAttendanceDto> toDtoList(List<MonthlyAttendanceEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<MonthlyAttendanceDto> list = new ArrayList<MonthlyAttendanceDto>( entities.size() );
        for ( MonthlyAttendanceEntity monthlyAttendanceEntity : entities ) {
            list.add( toDto( monthlyAttendanceEntity ) );
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
