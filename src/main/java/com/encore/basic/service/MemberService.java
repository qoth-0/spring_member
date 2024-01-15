package com.encore.basic.service;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.repository.MemoryMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service // @Service를 통해 싱글톤 컴포넌트로 생성 -> 스프링 빈으로 등록
// 스프링 빈이란 스프링이 생성하고 관리하는 객체를 의미
// 제어의 역전 (Inversion of Control) -> IOC 컨테이너가 스프링 빈을 관리(빈을 생성, 의존성 주입)
public class MemberService {
    @Autowired // 의존성 주입(DI) 방법1 => 필드 주입방식
    private MemoryMemberRepository memoryMemberRepository;
    static int total_id;

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
