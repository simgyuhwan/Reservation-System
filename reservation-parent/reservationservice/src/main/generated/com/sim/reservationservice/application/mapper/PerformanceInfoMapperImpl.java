package com.sim.reservationservice.application.mapper;

import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.dto.request.PerformanceDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-24T17:33:24+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class PerformanceInfoMapperImpl implements PerformanceInfoMapper {

    @Override
    public PerformanceDto toDto(PerformanceInfo arg0) {
        if ( arg0 == null ) {
            return null;
        }

        PerformanceDto.PerformanceDtoBuilder performanceDto = PerformanceDto.builder();

        performanceDto.performanceId( arg0.getPerformanceId() );
        performanceDto.price( arg0.getPrice() );
        performanceDto.contactPhoneNum( arg0.getContactPhoneNum() );
        performanceDto.contactPersonName( arg0.getContactPersonName() );

        return performanceDto.build();
    }

    @Override
    public List<PerformanceDto> toDto(List<PerformanceInfo> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<PerformanceDto> list = new ArrayList<PerformanceDto>( arg0.size() );
        for ( PerformanceInfo performanceInfo : arg0 ) {
            list.add( toDto( performanceInfo ) );
        }

        return list;
    }

    @Override
    public List<PerformanceInfo> toEntity(List<PerformanceDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<PerformanceInfo> list = new ArrayList<PerformanceInfo>( arg0.size() );
        for ( PerformanceDto performanceDto : arg0 ) {
            list.add( toEntity( performanceDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(PerformanceDto arg0, PerformanceInfo arg1) {
        if ( arg0 == null ) {
            return;
        }

        mapPerformanceDates( arg0, arg1 );
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
        performanceInfo.type( com.reservation.common.type.PerformanceType.findByType(dto.getPerformanceType()) );

        return performanceInfo.build();
    }
}
