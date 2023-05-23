package com.sim.member.memberdomain.mapper;

import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-05-23T17:06:40+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.6 (Amazon.com Inc.)"
)
@Component
public class MemberDtoMapperImpl implements MemberDtoMapper {

    @Override
    public MemberDto toDto(Member entity) {
        if ( entity == null ) {
            return null;
        }

        MemberDto.MemberDtoBuilder memberDto = MemberDto.builder();

        memberDto.id( entity.getId() );
        memberDto.userId( entity.getUserId() );
        memberDto.username( entity.getUsername() );
        memberDto.phoneNum( entity.getPhoneNum() );
        memberDto.address( entity.getAddress() );

        return memberDto.build();
    }

    @Override
    public Member toEntity(MemberDto dto) {
        if ( dto == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.userId( dto.getUserId() );
        member.username( dto.getUsername() );
        member.phoneNum( dto.getPhoneNum() );
        member.address( dto.getAddress() );

        return member.build();
    }

    @Override
    public List<MemberDto> toDto(List<Member> e) {
        if ( e == null ) {
            return null;
        }

        List<MemberDto> list = new ArrayList<MemberDto>( e.size() );
        for ( Member member : e ) {
            list.add( toDto( member ) );
        }

        return list;
    }

    @Override
    public List<Member> toEntity(List<MemberDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Member> list = new ArrayList<Member>( d.size() );
        for ( MemberDto memberDto : d ) {
            list.add( toEntity( memberDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(MemberDto dto, Member entity) {
        if ( dto == null ) {
            return;
        }
    }
}
