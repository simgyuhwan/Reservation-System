package com.reservation.performanceservice.application.mapper;

import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.PerformanceType;
import com.reservation.performanceservice.dto.request.PerformanceDto;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-05T11:01:03+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class PerformanceRegisterMapperImpl implements PerformanceRegisterMapper {

    @Override
    public PerformanceDto toDto(Performance arg0) {
        if ( arg0 == null ) {
            return null;
        }

        PerformanceDto.PerformanceRegisterDtoBuilder performanceRegisterDto = PerformanceDto.builder();

        performanceRegisterDto.userId( arg0.getUserId() );
        if ( arg0.getPerformanceType() != null ) {
            performanceRegisterDto.performanceType( arg0.getPerformanceType().name() );
        }
        performanceRegisterDto.audienceCount( arg0.getAudienceCount() );
        performanceRegisterDto.price( arg0.getPrice() );
        performanceRegisterDto.contactPhoneNum( arg0.getContactPhoneNum() );
        performanceRegisterDto.contactPersonName( arg0.getContactPersonName() );
        performanceRegisterDto.performanceInfo( arg0.getPerformanceInfo() );
        performanceRegisterDto.performancePlace( arg0.getPerformancePlace() );

        return performanceRegisterDto.build();
    }

    @Override
    public List<PerformanceDto> toDto(List<Performance> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<PerformanceDto> list = new ArrayList<PerformanceDto>( arg0.size() );
        for ( Performance performance : arg0 ) {
            list.add( toDto( performance ) );
        }

        return list;
    }

    @Override
    public List<Performance> toEntity(List<PerformanceDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Performance> list = new ArrayList<Performance>( arg0.size() );
        for ( PerformanceDto performanceDto : arg0 ) {
            list.add( toEntity(performanceDto) );
        }

        return list;
    }

    @Override
    public void updateFromDto(PerformanceDto arg0, Performance arg1) {
        if ( arg0 == null ) {
            return;
        }
    }

    @Override
    public Performance toEntity(PerformanceDto dto) {
        if ( dto == null ) {
            return null;
        }

        String userId = null;
        Integer audienceCount = null;
        Integer price = null;
        String contactPhoneNum = null;
        String contactPersonName = null;
        String performanceInfo = null;
        String performancePlace = null;

        userId = dto.getUserId();
        audienceCount = dto.getAudienceCount();
        price = dto.getPrice();
        contactPhoneNum = dto.getContactPhoneNum();
        contactPersonName = dto.getContactPersonName();
        performanceInfo = dto.getPerformanceInfo();
        performancePlace = dto.getPerformancePlace();

        PerformanceType performanceType = PerformanceType.findByType(dto.getPerformanceType());
        Long id = null;

        Performance performance = new Performance( id, userId, performanceType, audienceCount, price, contactPhoneNum, contactPersonName, performanceInfo, performancePlace );

        return performance;
    }
}
