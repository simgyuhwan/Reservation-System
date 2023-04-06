package com.reservation.memberservice.application.mapper;

import com.reservation.memberservice.domain.Member;
import com.reservation.memberservice.dto.response.MemberInfoDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-04-06T13:13:17+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.5 (Oracle Corporation)"
)
@Component
public class MemberInfoDtoMapperImpl implements MemberInfoDtoMapper {

    @Override
    public MemberInfoDto toDto(Member arg0) {
        if ( arg0 == null ) {
            return null;
        }

        MemberInfoDto.MemberInfoDtoBuilder memberInfoDto = MemberInfoDto.builder();

        memberInfoDto.userId( arg0.getUserId() );
        memberInfoDto.phoneNum( arg0.getPhoneNum() );
        memberInfoDto.username( arg0.getUsername() );
        memberInfoDto.address( arg0.getAddress() );

        return memberInfoDto.build();
    }

    @Override
    public Member toEntity(MemberInfoDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        String userId = null;
        String username = null;
        String phoneNum = null;
        String address = null;

        userId = arg0.getUserId();
        username = arg0.getUsername();
        phoneNum = arg0.getPhoneNum();
        address = arg0.getAddress();

        Long id = null;
        String password = null;

        Member member = new Member( id, userId, username, password, phoneNum, address );

        return member;
    }

    @Override
    public List<MemberInfoDto> toDto(List<Member> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<MemberInfoDto> list = new ArrayList<MemberInfoDto>( arg0.size() );
        for ( Member member : arg0 ) {
            list.add( toDto( member ) );
        }

        return list;
    }

    @Override
    public List<Member> toEntity(List<MemberInfoDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Member> list = new ArrayList<Member>( arg0.size() );
        for ( MemberInfoDto memberInfoDto : arg0 ) {
            list.add( toEntity( memberInfoDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(MemberInfoDto arg0, Member arg1) {
        if ( arg0 == null ) {
            return;
        }
    }
}
