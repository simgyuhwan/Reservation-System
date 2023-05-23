package com.sim.member.memberdomain.mapper;

import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberCreateDto;
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
public class MemberCreateMapperImpl implements MemberCreateMapper {

    @Override
    public MemberCreateDto toDto(Member entity) {
        if ( entity == null ) {
            return null;
        }

        MemberCreateDto.MemberCreateDtoBuilder memberCreateDto = MemberCreateDto.builder();

        memberCreateDto.userId( entity.getUserId() );
        memberCreateDto.username( entity.getUsername() );
        memberCreateDto.password( entity.getPassword() );
        memberCreateDto.phoneNum( entity.getPhoneNum() );
        memberCreateDto.address( entity.getAddress() );

        return memberCreateDto.build();
    }

    @Override
    public Member toEntity(MemberCreateDto dto) {
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
    public List<MemberCreateDto> toDto(List<Member> e) {
        if ( e == null ) {
            return null;
        }

        List<MemberCreateDto> list = new ArrayList<MemberCreateDto>( e.size() );
        for ( Member member : e ) {
            list.add( toDto( member ) );
        }

        return list;
    }

    @Override
    public List<Member> toEntity(List<MemberCreateDto> d) {
        if ( d == null ) {
            return null;
        }

        List<Member> list = new ArrayList<Member>( d.size() );
        for ( MemberCreateDto memberCreateDto : d ) {
            list.add( toEntity( memberCreateDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(MemberCreateDto dto, Member entity) {
        if ( dto == null ) {
            return;
        }
    }
}
