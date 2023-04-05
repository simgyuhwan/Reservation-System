package com.reservation.performances.application.mapper;

import com.reservation.performances.domain.Performance;
import com.reservation.performances.domain.PerformanceType;
import com.reservation.performances.dto.request.PerformanceRegisterDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-04T13:59:34+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class PerformanceRegisterMapperImpl implements PerformanceRegisterMapper {

    @Override
    public PerformanceRegisterDto toDto(Performance entity) {
        if ( entity == null ) {
            return null;
        }

        PerformanceRegisterDto.PerformanceRegisterDtoBuilder performanceRegisterDto = PerformanceRegisterDto.builder();

        performanceRegisterDto.register( entity.getRegister() );
        if ( entity.getPerformanceType() != null ) {
            performanceRegisterDto.performanceType( entity.getPerformanceType().name() );
        }
        performanceRegisterDto.audienceCount( entity.getAudienceCount() );
        performanceRegisterDto.price( entity.getPrice() );
        performanceRegisterDto.contactPhoneNum( entity.getContactPhoneNum() );
        performanceRegisterDto.contactPersonName( entity.getContactPersonName() );
        performanceRegisterDto.performanceInfo( entity.getPerformanceInfo() );
        performanceRegisterDto.performancePlace( entity.getPerformancePlace() );

        return performanceRegisterDto.build();
    }

    @Override
    public List<PerformanceRegisterDto> toDto(List<Performance> e) {
        if ( e == null ) {
            return null;
        }

        List<PerformanceRegisterDto> list = new ArrayList<PerformanceRegisterDto>( e.size() );
        for ( Performance performance : e ) {
            list.add( toDto( performance ) );
        }

        return list;
    }

    @Override
    public List<Performance> toEntity(List<PerformanceRegisterDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Performance> list = new ArrayList<Performance>( d.size() );
        for ( PerformanceRegisterDto performanceRegisterDto : d ) {
            list.add( toEntity( performanceRegisterDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(PerformanceRegisterDto dto, Performance entity) {
        if ( dto == null ) {
            return;
        }
    }

    @Override
    public Performance toEntity(PerformanceRegisterDto dto) {
        if ( dto == null ) {
            return null;
        }

        String register = null;
        Integer audienceCount = null;
        Integer price = null;
        String contactPhoneNum = null;
        String contactPersonName = null;
        String performanceInfo = null;
        String performancePlace = null;

        register = dto.getRegister();
        audienceCount = dto.getAudienceCount();
        price = dto.getPrice();
        contactPhoneNum = dto.getContactPhoneNum();
        contactPersonName = dto.getContactPersonName();
        performanceInfo = dto.getPerformanceInfo();
        performancePlace = dto.getPerformancePlace();

        PerformanceType performanceType = PerformanceType.findByType(dto.getPerformanceType());
        Long id = null;

        Performance performance = new Performance( id, register, performanceType, audienceCount, price, contactPhoneNum, contactPersonName, performanceInfo, performancePlace );

        return performance;
    }
}
