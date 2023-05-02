package com.reservation.performanceservice.application.mapper;

import com.reservation.common.type.PerformanceType;
import com.reservation.performanceservice.domain.Performance;
import com.reservation.performanceservice.domain.PerformanceDay;
import com.reservation.performanceservice.event.PerformanceCreatedEvent;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-02T16:55:34+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class CreatedEventMapperImpl implements CreatedEventMapper {

    @Override
    public Performance toEntity(PerformanceCreatedEvent arg0) {
        if ( arg0 == null ) {
            return null;
        }

        String userId = null;
        String performanceName = null;
        PerformanceType performanceType = null;
        Integer audienceCount = null;
        Integer price = null;
        String contactPhoneNum = null;
        String contactPersonName = null;
        String performanceInfo = null;
        String performancePlace = null;

        userId = arg0.getUserId();
        performanceName = arg0.getPerformanceName();
        if ( arg0.getPerformanceType() != null ) {
            performanceType = Enum.valueOf( PerformanceType.class, arg0.getPerformanceType() );
        }
        audienceCount = arg0.getAudienceCount();
        price = arg0.getPrice();
        contactPhoneNum = arg0.getContactPhoneNum();
        contactPersonName = arg0.getContactPersonName();
        performanceInfo = arg0.getPerformanceInfo();
        performancePlace = arg0.getPerformancePlace();

        Long id = null;
        List<PerformanceDay> performanceDays = null;

        Performance performance = new Performance( id, userId, performanceName, performanceType, audienceCount, price, contactPhoneNum, contactPersonName, performanceInfo, performancePlace, performanceDays );

        return performance;
    }

    @Override
    public List<PerformanceCreatedEvent> toDto(List<Performance> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<PerformanceCreatedEvent> list = new ArrayList<PerformanceCreatedEvent>( arg0.size() );
        for ( Performance performance : arg0 ) {
            list.add( toDto( performance ) );
        }

        return list;
    }

    @Override
    public List<Performance> toEntity(List<PerformanceCreatedEvent> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Performance> list = new ArrayList<Performance>( arg0.size() );
        for ( PerformanceCreatedEvent performanceCreatedEvent : arg0 ) {
            list.add( toEntity( performanceCreatedEvent ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(PerformanceCreatedEvent arg0, Performance arg1) {
        if ( arg0 == null ) {
            return;
        }
    }

    @Override
    public PerformanceCreatedEvent toDto(Performance entity) {
        if ( entity == null ) {
            return null;
        }

        PerformanceCreatedEvent.PerformanceCreatedEventBuilder performanceCreatedEvent = PerformanceCreatedEvent.builder();

        performanceCreatedEvent.performanceId( entity.getId() );
        performanceCreatedEvent.performanceTimes( mapTimesSetString( entity.getPerformanceDays() ) );
        performanceCreatedEvent.performanceStartDate( mapStartDateString( entity.getPerformanceDays() ) );
        performanceCreatedEvent.performanceEndDate( mapEndDateString( entity.getPerformanceDays() ) );
        performanceCreatedEvent.userId( entity.getUserId() );
        performanceCreatedEvent.performanceName( entity.getPerformanceName() );
        performanceCreatedEvent.audienceCount( entity.getAudienceCount() );
        performanceCreatedEvent.price( entity.getPrice() );
        performanceCreatedEvent.contactPhoneNum( entity.getContactPhoneNum() );
        performanceCreatedEvent.contactPersonName( entity.getContactPersonName() );
        performanceCreatedEvent.performanceInfo( entity.getPerformanceInfo() );
        performanceCreatedEvent.performancePlace( entity.getPerformancePlace() );

        performanceCreatedEvent.timestamp( java.time.LocalDateTime.now() );
        performanceCreatedEvent.performanceType( entity.getPerformanceType().getType() );

        return performanceCreatedEvent.build();
    }
}
