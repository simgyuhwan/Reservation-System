package com.reservation.memberservice.application.mapper;

import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.request.SignUpDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-04T21:33:27+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class SignUpRequestMapperImpl implements SignUpRequestMapper {

    @Override
    public SignUpDto toDto(Member arg0) {
        if ( arg0 == null ) {
            return null;
        }

        SignUpDto.SignUpDtoBuilder signUpDto = SignUpDto.builder();

        signUpDto.userId( arg0.getUserId() );
        signUpDto.username( arg0.getUsername() );
        signUpDto.password( arg0.getPassword() );
        signUpDto.phoneNum( arg0.getPhoneNum() );
        signUpDto.address( arg0.getAddress() );

        return signUpDto.build();
    }

    @Override
    public Member toEntity(SignUpDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        String userId = null;
        String username = null;
        String password = null;
        String phoneNum = null;
        String address = null;

        userId = arg0.getUserId();
        username = arg0.getUsername();
        password = arg0.getPassword();
        phoneNum = arg0.getPhoneNum();
        address = arg0.getAddress();

        Long id = null;

        Member member = new Member( id, userId, username, password, phoneNum, address );

        return member;
    }

    @Override
    public List<SignUpDto> toDto(List<Member> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<SignUpDto> list = new ArrayList<SignUpDto>( arg0.size() );
        for ( Member member : arg0 ) {
            list.add( toDto( member ) );
        }

        return list;
    }

    @Override
    public List<Member> toEntity(List<SignUpDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Member> list = new ArrayList<Member>( arg0.size() );
        for ( SignUpDto signUpDto : arg0 ) {
            list.add( toEntity( signUpDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(SignUpDto arg0, Member arg1) {
        if ( arg0 == null ) {
            return;
        }
    }
}
