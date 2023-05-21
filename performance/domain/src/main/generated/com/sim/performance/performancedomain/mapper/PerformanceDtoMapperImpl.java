package com.sim.performance.performancedomain.mapper;

import com.sim.performance.performancedomain.domain.Performance;
import com.sim.performance.performancedomain.dto.PerformanceDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-21T16:52:51+0900",
    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class PerformanceDtoMapperImpl implements PerformanceDtoMapper {

    @Override
    public List<PerformanceDto> toDto(List<Performance> e) {
        if ( e == null ) {
            return null;
        }

        List<PerformanceDto> list = new ArrayList<PerformanceDto>( e.size() );
        for ( Performance performance : e ) {
            list.add( toDto( performance ) );
        }

        return list;
    }

    @Override
    public List<Performance> toEntity(List<PerformanceDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Performance> list = new ArrayList<Performance>( d.size() );
        for ( PerformanceDto performanceDto : d ) {
            list.add( toEntity( performanceDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(PerformanceDto dto, Performance entity) {
        if ( dto == null ) {
            return;
        }

        mapPerformanceDays( dto, entity );
    }

    @Override
    public Performance toEntity(PerformanceDto dto) {
        if ( dto == null ) {
            return null;
        }

        Performance.PerformanceBuilder performance = Performance.builder();

        performance.memberId( dto.getMemberId() );
        performance.performanceName( dto.getPerformanceName() );
        performance.audienceCount( dto.getAudienceCount() );
        performance.price( dto.getPrice() );
        performance.contactPhoneNum( dto.getContactPhoneNum() );
        performance.contactPersonName( dto.getContactPersonName() );
        performance.performanceInfo( dto.getPerformanceInfo() );
        performance.performancePlace( dto.getPerformancePlace() );

        performance.performanceType( com.sim.performance.performancedomain.type.PerformanceType.findByType(dto.getPerformanceType()) );

        return performance.build();
    }

    @Override
    public PerformanceDto toDto(Performance entity) {
        if ( entity == null ) {
            return null;
        }

        PerformanceDto.PerformanceDtoBuilder performanceDto = PerformanceDto.builder();

        performanceDto.performanceId( entity.getId() );
        performanceDto.performanceTimes( mapTimesSetString( entity.getPerformanceDays() ) );
        performanceDto.performanceStartDate( mapStartDateString( entity.getPerformanceDays() ) );
        performanceDto.performanceEndDate( mapEndDateString( entity.getPerformanceDays() ) );
        performanceDto.memberId( entity.getMemberId() );
        performanceDto.performanceName( entity.getPerformanceName() );
        performanceDto.audienceCount( entity.getAudienceCount() );
        performanceDto.price( entity.getPrice() );
        performanceDto.contactPhoneNum( entity.getContactPhoneNum() );
        performanceDto.contactPersonName( entity.getContactPersonName() );
        performanceDto.performanceInfo( entity.getPerformanceInfo() );
        performanceDto.performancePlace( entity.getPerformancePlace() );

        performanceDto.performanceType( entity.getPerformanceType().getType() );

        return performanceDto.build();
    }
}
