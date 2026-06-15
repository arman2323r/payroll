package org.payroll.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.payroll.dto.YearlyBasicSalaryDto;
import org.payroll.model.YearlyBasicSalaryEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-15T14:28:27+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class YearlyBasicSalaryMapperImpl implements YearlyBasicSalaryMapper {

    @Override
    public YearlyBasicSalaryDto toDto(YearlyBasicSalaryEntity entity) {
        if ( entity == null ) {
            return null;
        }

        YearlyBasicSalaryDto.YearlyBasicSalaryDtoBuilder yearlyBasicSalaryDto = YearlyBasicSalaryDto.builder();

        yearlyBasicSalaryDto.id( entity.getId() );
        yearlyBasicSalaryDto.year( entity.getYear() );
        yearlyBasicSalaryDto.basicSalary( entity.getBasicSalary() );
        yearlyBasicSalaryDto.housingAllowance( entity.getHousingAllowance() );
        yearlyBasicSalaryDto.breadAllowance( entity.getBreadAllowance() );
        yearlyBasicSalaryDto.insurancePercent( entity.getInsurancePercent() );

        return yearlyBasicSalaryDto.build();
    }

    @Override
    public YearlyBasicSalaryEntity toEntity(YearlyBasicSalaryDto dto) {
        if ( dto == null ) {
            return null;
        }

        YearlyBasicSalaryEntity.YearlyBasicSalaryEntityBuilder yearlyBasicSalaryEntity = YearlyBasicSalaryEntity.builder();

        yearlyBasicSalaryEntity.id( dto.getId() );
        yearlyBasicSalaryEntity.year( dto.getYear() );
        yearlyBasicSalaryEntity.basicSalary( dto.getBasicSalary() );
        yearlyBasicSalaryEntity.housingAllowance( dto.getHousingAllowance() );
        yearlyBasicSalaryEntity.breadAllowance( dto.getBreadAllowance() );
        yearlyBasicSalaryEntity.insurancePercent( dto.getInsurancePercent() );

        return yearlyBasicSalaryEntity.build();
    }

    @Override
    public List<YearlyBasicSalaryDto> toDtoList(List<YearlyBasicSalaryEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<YearlyBasicSalaryDto> list = new ArrayList<YearlyBasicSalaryDto>( entities.size() );
        for ( YearlyBasicSalaryEntity yearlyBasicSalaryEntity : entities ) {
            list.add( toDto( yearlyBasicSalaryEntity ) );
        }

        return list;
    }
}
