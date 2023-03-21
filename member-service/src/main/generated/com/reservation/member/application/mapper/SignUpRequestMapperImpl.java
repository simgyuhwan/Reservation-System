package com.reservation.member.application.mapper;

import com.reservation.member.domain.Member;
import com.reservation.member.dto.SignUpRequest;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-03-21T21:20:01+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (Oracle Corporation)"
)
@Component
public class SignUpRequestMapperImpl implements SignUpRequestMapper {

    @Override
    public SignUpRequest toDto(Member entity) {
        if ( entity == null ) {
            return null;
        }

        SignUpRequest signUpRequest = new SignUpRequest();

        return signUpRequest;
    }

    @Override
    public Member toEntity(SignUpRequest dto) {
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
    public List<SignUpRequest> toDto(List<Member> e) {
        if ( e == null ) {
            return null;
        }

        List<SignUpRequest> list = new ArrayList<SignUpRequest>( e.size() );
        for ( Member member : e ) {
            list.add( toDto( member ) );
        }

        return list;
    }

    @Override
    public List<Member> toEntity(List<SignUpRequest> d) {
        if ( d == null ) {
            return null;
        }

        List<Member> list = new ArrayList<Member>( d.size() );
        for ( SignUpRequest signUpRequest : d ) {
            list.add( toEntity( signUpRequest ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(SignUpRequest dto, Member entity) {
        if ( dto == null ) {
            return;
        }
    }
}
