package com.sim.reservation.data.reservation.mapper;

import com.sim.reservation.data.reservation.domain.Reservation;
import com.sim.reservation.data.reservation.dto.ReservationDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-01T10:19:55+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class ReservationInfoMapperImpl implements ReservationInfoMapper {

    @Override
    public ReservationDto toDto(Reservation entity) {
        if ( entity == null ) {
            return null;
        }

        ReservationDto.ReservationDtoBuilder reservationDto = ReservationDto.builder();

        reservationDto.userId( entity.getUserId() );
        reservationDto.name( entity.getName() );
        reservationDto.phoneNum( entity.getPhoneNum() );
        reservationDto.email( entity.getEmail() );

        return reservationDto.build();
    }

    @Override
    public Reservation toEntity(ReservationDto dto) {
        if ( dto == null ) {
            return null;
        }

        Reservation.ReservationBuilder reservation = Reservation.builder();

        reservation.userId( dto.getUserId() );
        reservation.name( dto.getName() );
        reservation.phoneNum( dto.getPhoneNum() );
        reservation.email( dto.getEmail() );

        return reservation.build();
    }

    @Override
    public List<ReservationDto> toDto(List<Reservation> e) {
        if ( e == null ) {
            return null;
        }

        List<ReservationDto> list = new ArrayList<ReservationDto>( e.size() );
        for ( Reservation reservation : e ) {
            list.add( toDto( reservation ) );
        }

        return list;
    }

    @Override
    public List<Reservation> toEntity(List<ReservationDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Reservation> list = new ArrayList<Reservation>( d.size() );
        for ( ReservationDto reservationDto : d ) {
            list.add( toEntity( reservationDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(ReservationDto dto, Reservation entity) {
        if ( dto == null ) {
            return;
        }
    }
}
