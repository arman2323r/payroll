package org.payroll.mapper;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.payroll.dto.PayrollDetailDto;
import org.payroll.model.PayrollDetailEntity;
import org.payroll.model.PayrollEntity;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2026-06-15T14:28:26+0330",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.12 (Oracle Corporation)"
)
@Component
public class PayrollDetailMapperImpl implements PayrollDetailMapper {

    @Override
    public PayrollDetailDto toDto(PayrollDetailEntity entity) {
        if ( entity == null ) {
            return null;
        }

        PayrollDetailDto.PayrollDetailDtoBuilder payrollDetailDto = PayrollDetailDto.builder();

        payrollDetailDto.payrollId( entityPayrollId( entity ) );
        payrollDetailDto.description( entity.getComponentName() );
        payrollDetailDto.itemType( entity.getType() );
        payrollDetailDto.id( entity.getId() );
        payrollDetailDto.amount( entity.getAmount() );

        return payrollDetailDto.build();
    }

    @Override
    public PayrollDetailEntity toEntity(PayrollDetailDto dto) {
        if ( dto == null ) {
            return null;
        }

        PayrollDetailEntity.PayrollDetailEntityBuilder payrollDetailEntity = PayrollDetailEntity.builder();

        payrollDetailEntity.componentName( dto.getDescription() );
        payrollDetailEntity.type( dto.getItemType() );
        payrollDetailEntity.id( dto.getId() );
        payrollDetailEntity.amount( dto.getAmount() );

        return payrollDetailEntity.build();
    }

    @Override
    public List<PayrollDetailDto> toDtoList(List<PayrollDetailEntity> entities) {
        if ( entities == null ) {
            return null;
        }

        List<PayrollDetailDto> list = new ArrayList<PayrollDetailDto>( entities.size() );
        for ( PayrollDetailEntity payrollDetailEntity : entities ) {
            list.add( toDto( payrollDetailEntity ) );
        }

        return list;
    }

    @Override
    public List<PayrollDetailEntity> toEntityList(List<PayrollDetailDto> dtos) {
        if ( dtos == null ) {
            return null;
        }

        List<PayrollDetailEntity> list = new ArrayList<PayrollDetailEntity>( dtos.size() );
        for ( PayrollDetailDto payrollDetailDto : dtos ) {
            list.add( toEntity( payrollDetailDto ) );
        }

        return list;
    }

    private Long entityPayrollId(PayrollDetailEntity payrollDetailEntity) {
        if ( payrollDetailEntity == null ) {
            return null;
        }
        PayrollEntity payroll = payrollDetailEntity.getPayroll();
        if ( payroll == null ) {
            return null;
        }
        Long id = payroll.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
