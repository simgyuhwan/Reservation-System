package com.reservation.member.application.mapper;

import com.reservation.member.domain.Member;
import com.reservation.member.dto.response.MemberInfoDto;
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
public class MemberInfoDtoMapperImpl implements MemberInfoDtoMapper {

    @Override
    public MemberInfoDto toDto(Member entity) {
        if ( entity == null ) {
            return null;
        }

        MemberInfoDto.MemberInfoDtoBuilder memberInfoDto = MemberInfoDto.builder();

        memberInfoDto.userId( entity.getUserId() );
        memberInfoDto.phoneNum( entity.getPhoneNum() );
        memberInfoDto.username( entity.getUsername() );
        memberInfoDto.address( entity.getAddress() );

        return memberInfoDto.build();
    }

    @Override
    public Member toEntity(MemberInfoDto dto) {
        if ( dto == null ) {
            return null;
        }

        String userId = null;
        String username = null;
        String phoneNum = null;
        String address = null;

        userId = dto.getUserId();
        username = dto.getUsername();
        phoneNum = dto.getPhoneNum();
        address = dto.getAddress();

        Long id = null;
        String password = null;

        Member member = new Member( id, userId, username, password, phoneNum, address );

        return member;
    }

    @Override
    public List<MemberInfoDto> toDto(List<Member> e) {
        if ( e == null ) {
            return null;
        }

        List<MemberInfoDto> list = new ArrayList<MemberInfoDto>( e.size() );
        for ( Member member : e ) {
            list.add( toDto( member ) );
        }

        return list;
    }

    @Override
    public List<Member> toEntity(List<MemberInfoDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Member> list = new ArrayList<Member>( d.size() );
        for ( MemberInfoDto memberInfoDto : d ) {
            list.add( toEntity( memberInfoDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(MemberInfoDto dto, Member entity) {
        if ( dto == null ) {
            return;
        }
    }
}
