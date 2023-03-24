package com.reservation.member.application.mapper;

import com.reservation.member.domain.Member;
import com.reservation.member.dto.request.SignUpDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-24T17:33:40+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class SignUpRequestMapperImpl implements SignUpRequestMapper {

    @Override
    public SignUpDto toDto(Member entity) {
        if ( entity == null ) {
            return null;
        }

        SignUpDto.SignUpDtoBuilder signUpDto = SignUpDto.builder();

        signUpDto.userId( entity.getUserId() );
        signUpDto.username( entity.getUsername() );
        signUpDto.password( entity.getPassword() );
        signUpDto.phoneNum( entity.getPhoneNum() );
        signUpDto.address( entity.getAddress() );

        return signUpDto.build();
    }

    @Override
    public Member toEntity(SignUpDto dto) {
        if ( dto == null ) {
            return null;
        }

        String userId = null;
        String username = null;
        String password = null;
        String phoneNum = null;
        String address = null;

        userId = dto.getUserId();
        username = dto.getUsername();
        password = dto.getPassword();
        phoneNum = dto.getPhoneNum();
        address = dto.getAddress();

        Long id = null;

        Member member = new Member( id, userId, username, password, phoneNum, address );

        return member;
    }

    @Override
    public List<SignUpDto> toDto(List<Member> e) {
        if ( e == null ) {
            return null;
        }

        List<SignUpDto> list = new ArrayList<SignUpDto>( e.size() );
        for ( Member member : e ) {
            list.add( toDto( member ) );
        }

        return list;
    }

    @Override
    public List<Member> toEntity(List<SignUpDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Member> list = new ArrayList<Member>( d.size() );
        for ( SignUpDto signUpDto : d ) {
            list.add( toEntity( signUpDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(SignUpDto dto, Member entity) {
        if ( dto == null ) {
            return;
        }
    }
}
