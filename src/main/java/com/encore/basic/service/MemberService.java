package com.encore.basic.service;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.repository.MemoryMemberRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service // @Service를 통해 싱글톤 컴포넌트로 생ㅇ성 -> 스프링 bin으로 등록
public class MemberService {
    private final MemoryMemberRepository memoryMemberRepository;
    static int total_id;
    public MemberService() {
        memoryMemberRepository = new MemoryMemberRepository();
    }
//        List<Member> 리턴
    public List<MemberResponseDto> members() {
        List<Member> members = memoryMemberRepository.members();
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for(Member a : members) {
            MemberResponseDto memberResponseDto = new MemberResponseDto();
            memberResponseDto.setName(a.getName());
            memberResponseDto.setEmail(a.getEmail());
            memberResponseDto.setPassword(a.getPassword());
            memberResponseDtos.add(memberResponseDto);
        }
        return memberResponseDtos;
    }

//    Member 등록

    public  void memberCreate(MemberRequestDto memberRequestDto) {
        LocalDateTime now = LocalDateTime.now();
        total_id += 1;
        Member member = new Member(total_id,
                                memberRequestDto.getName(),
                                memberRequestDto.getEmail(),
                                memberRequestDto.getPassword(),
                                now);
        memoryMemberRepository.memberCreate(member);
    }
}
