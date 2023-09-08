package com.sim.member.memberdomain.mapper;

import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberCreateRequestDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-03T16:27:05+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class MemberCreateMapperImpl implements MemberCreateMapper {

    @Override
    public MemberCreateRequestDto toDto(Member entity) {
        if ( entity == null ) {
            return null;
        }

        MemberCreateRequestDto.MemberCreateRequestDtoBuilder memberCreateRequestDto = MemberCreateRequestDto.builder();

        memberCreateRequestDto.userId( entity.getUserId() );
        memberCreateRequestDto.username( entity.getUsername() );
        memberCreateRequestDto.password( entity.getPassword() );
        memberCreateRequestDto.phoneNum( entity.getPhoneNum() );
        memberCreateRequestDto.address( entity.getAddress() );

        return memberCreateRequestDto.build();
    }

    @Override
    public Member toEntity(MemberCreateRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.userId( dto.getUserId() );
        member.username( dto.getUsername() );
        member.password( dto.getPassword() );
        member.phoneNum( dto.getPhoneNum() );
        member.address( dto.getAddress() );

        return member.build();
    }

    @Override
    public List<MemberCreateRequestDto> toDto(List<Member> e) {
        if ( e == null ) {
            return null;
        }

        List<MemberCreateRequestDto> list = new ArrayList<MemberCreateRequestDto>( e.size() );
        for ( Member member : e ) {
            list.add( toDto( member ) );
        }

        return list;
    }

    @Override
    public List<Member> toEntity(List<MemberCreateRequestDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Member> list = new ArrayList<Member>( d.size() );
        for ( MemberCreateRequestDto memberCreateRequestDto : d ) {
            list.add( toEntity( memberCreateRequestDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(MemberCreateRequestDto dto, Member entity) {
        if ( dto == null ) {
            return;
        }
    }
}
