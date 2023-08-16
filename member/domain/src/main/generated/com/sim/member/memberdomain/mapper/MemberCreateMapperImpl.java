package com.sim.member.memberdomain.mapper;

import com.sim.member.memberdomain.domain.Member;
import com.sim.member.memberdomain.dto.MemberCreateRequestDto;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-08-16T17:49:55+0900",
    comments = "version: 1.5.2.Final, compiler: javac, environment: Java 17.0.7 (Oracle Corporation)"
)
@Component
public class MemberCreateMapperImpl implements MemberCreateMapper {

    @Override
    public MemberCreateRequestDto toDto(Member arg0) {
        if ( arg0 == null ) {
            return null;
        }

        MemberCreateRequestDto.MemberCreateRequestDtoBuilder memberCreateRequestDto = MemberCreateRequestDto.builder();

        memberCreateRequestDto.userId( arg0.getUserId() );
        memberCreateRequestDto.username( arg0.getUsername() );
        memberCreateRequestDto.password( arg0.getPassword() );
        memberCreateRequestDto.phoneNum( arg0.getPhoneNum() );
        memberCreateRequestDto.address( arg0.getAddress() );

        return memberCreateRequestDto.build();
    }

    @Override
    public Member toEntity(MemberCreateRequestDto arg0) {
        if ( arg0 == null ) {
            return null;
        }

        Member.MemberBuilder member = Member.builder();

        member.userId( arg0.getUserId() );
        member.username( arg0.getUsername() );
        member.password( arg0.getPassword() );
        member.phoneNum( arg0.getPhoneNum() );
        member.address( arg0.getAddress() );

        return member.build();
    }

    @Override
    public List<MemberCreateRequestDto> toDto(List<Member> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<MemberCreateRequestDto> list = new ArrayList<MemberCreateRequestDto>( arg0.size() );
        for ( Member member : arg0 ) {
            list.add( toDto( member ) );
        }

        return list;
    }

    @Override
    public List<Member> toEntity(List<MemberCreateRequestDto> arg0) {
        if ( arg0 == null ) {
            return null;
        }

        List<Member> list = new ArrayList<Member>( arg0.size() );
        for ( MemberCreateRequestDto memberCreateRequestDto : arg0 ) {
            list.add( toEntity( memberCreateRequestDto ) );
        }

        return list;
    }

    @Override
    public void updateFromDto(MemberCreateRequestDto arg0, Member arg1) {
        if ( arg0 == null ) {
            return;
        }
    }
}
