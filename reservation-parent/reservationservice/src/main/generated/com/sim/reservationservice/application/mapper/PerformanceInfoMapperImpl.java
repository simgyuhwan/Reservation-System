package com.sim.reservationservice.application.mapper;

import com.reservation.common.dto.PerformanceDto;
import com.reservation.common.type.PerformanceTypes;
import com.sim.reservationservice.domain.PerformanceInfo;
import com.sim.reservationservice.domain.PerformanceSchedule;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-07T22:15:47+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
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

        afterMapping( arg0, arg1 );
    }

    @Override
    public PerformanceInfo toEntity(PerformanceDto dto) {
        if ( dto == null ) {
            return null;
        }

        String name = null;
        String info = null;
        String place = null;
        Integer price = null;
        String contactPhoneNum = null;
        String contactPersonName = null;
        Long performanceId = null;

        name = dto.getPerformanceName();
        info = dto.getPerformanceInfo();
        place = dto.getPerformancePlace();
        price = dto.getPrice();
        contactPhoneNum = dto.getContactPhoneNum();
        contactPersonName = dto.getContactPersonName();
        performanceId = dto.getPerformanceId();

        boolean isAvailable = true;
        PerformanceTypes type = com.reservation.common.type.PerformanceTypes.findByType(dto.getPerformanceType());
        List<PerformanceSchedule> performanceSchedules = null;

        PerformanceInfo performanceInfo = new PerformanceInfo( name, info, place, isAvailable, price, contactPhoneNum, contactPersonName, performanceId, type, performanceSchedules );

        afterMapping( dto, performanceInfo );

        return performanceInfo;
    }
}
