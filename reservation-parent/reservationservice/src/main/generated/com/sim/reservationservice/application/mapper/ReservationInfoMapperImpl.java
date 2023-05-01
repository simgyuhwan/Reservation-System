package com.sim.reservationservice.application.mapper;

import com.sim.reservationservice.domain.Reservation;
import com.sim.reservationservice.domain.ReservationStatus;
import com.sim.reservationservice.dto.response.ReservationInfoDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-01T18:52:30+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class ReservationInfoMapperImpl implements ReservationInfoMapper {

    @Override
    public ReservationInfoDto toDto(Reservation arg0) {
        if ( arg0 == null ) {
            return null;
        }

        ReservationInfoDto.ReservationInfoDtoBuilder reservationInfoDto = ReservationInfoDto.builder();

        reservationInfoDto.id( arg0.getId() );
        reservationInfoDto.name( arg0.getName() );
        reservationInfoDto.phoneNum( arg0.getPhoneNum() );
        reservationInfoDto.email( arg0.getEmail() );
        if ( arg0.getStatus() != null ) {
            reservationInfoDto.status( arg0.getStatus().name() );
        }

        return reservationInfoDto.build();
    }

    @Override
    public Reservation toEntity(ReservationInfoDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Reservation.ReservationBuilder reservation = Reservation.builder();

        reservation.name( arg0.getName() );
        reservation.phoneNum( arg0.getPhoneNum() );
        reservation.email( arg0.getEmail() );
        if ( arg0.getStatus() != null ) {
            reservation.status( Enum.valueOf( ReservationStatus.class, arg0.getStatus() ) );
        }

        return reservation.build();
    }

    @Override
    public List<ReservationInfoDto> toDto(List<Reservation> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<ReservationInfoDto> list = new ArrayList<ReservationInfoDto>( arg0.size() );
        for ( Reservation reservation : arg0 ) {
            list.add( toDto( reservation ) );
        }

        return list;
    }

    @Override
    public List<Reservation> toEntity(List<ReservationInfoDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Reservation> list = new ArrayList<Reservation>( arg0.size() );
        for ( ReservationInfoDto reservationInfoDto : arg0 ) {
            list.add( toEntity( reservationInfoDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(ReservationInfoDto arg0, Reservation arg1) {
        if ( arg0 == null ) {
            return;
        }
    }
}
