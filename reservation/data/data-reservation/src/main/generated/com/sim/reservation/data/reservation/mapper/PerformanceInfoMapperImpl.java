package com.sim.reservation.data.reservation.mapper;

import com.sim.reservation.data.reservation.domain.PerformanceInfo;
import com.sim.reservation.data.reservation.dto.PerformanceDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-29T02:40:59+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class PerformanceInfoMapperImpl implements PerformanceInfoMapper {

    @Override
    public PerformanceDto toDto(PerformanceInfo entity) {
        if ( entity == null ) {
            return null;
        }

        PerformanceDto.PerformanceDtoBuilder performanceDto = PerformanceDto.builder();

        performanceDto.performanceId( entity.getPerformanceId() );
        performanceDto.price( entity.getPrice() );
        performanceDto.contactPhoneNum( entity.getContactPhoneNum() );
        performanceDto.contactPersonName( entity.getContactPersonName() );

        return performanceDto.build();
    }

    @Override
    public List<PerformanceDto> toDto(List<PerformanceInfo> e) {
        if ( e == null ) {
            return null;
        }

        List<PerformanceDto> list = new ArrayList<PerformanceDto>( e.size() );
        for ( PerformanceInfo performanceInfo : e ) {
            list.add( toDto( performanceInfo ) );
        }

        return list;
    }

    @Override
    public List<PerformanceInfo> toEntity(List<PerformanceDto> d) {
        if ( d == null ) {
            return null;
        }

        List<PerformanceInfo> list = new ArrayList<PerformanceInfo>( d.size() );
        for ( PerformanceDto performanceDto : d ) {
            list.add( toEntity( performanceDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(PerformanceDto dto, PerformanceInfo entity) {
        if ( dto == null ) {
            return;
        }

        afterMapping( dto, entity );
    }

    @Override
    public PerformanceInfo toEntity(PerformanceDto dto) {
        if ( dto == null ) {
            return null;
        }

        PerformanceInfo.PerformanceInfoBuilder performanceInfo = PerformanceInfo.builder();

        performanceInfo.name( dto.getPerformanceName() );
        performanceInfo.info( dto.getPerformanceInfo() );
        performanceInfo.place( dto.getPerformancePlace() );
        performanceInfo.price( dto.getPrice() );
        performanceInfo.contactPhoneNum( dto.getContactPhoneNum() );
        performanceInfo.contactPersonName( dto.getContactPersonName() );
        performanceInfo.performanceId( dto.getPerformanceId() );

        performanceInfo.isAvailable( true );
        performanceInfo.type( com.sim.reservation.data.reservation.type.PerformanceType.findByType(dto.getPerformanceType()) );

        return performanceInfo.build();
    }
}
