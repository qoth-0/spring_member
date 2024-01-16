package com.encore.basic.service;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.repository.JdbcMemberRepository;
import com.encore.basic.repository.MemberRepository;
import com.encore.basic.repository.MemoryMemberRepository;
import com.encore.basic.repository.SpringDataJpaMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service // @Service를 통해 싱글톤 컴포넌트로 생성 -> 스프링 빈으로 등록
// 스프링 빈이란 스프링이 생성하고 관리하는 객체를 의미
// 제어의 역전 (Inversion of Control) -> IOC 컨테이너가 스프링 빈을 관리(빈을 생성, 의존성 주입)
public class MemberService {
    private final MemberRepository memberRepository; // 인터페이스로 다형성 구현
    @Autowired
    public MemberService(SpringDataJpaMemberRepository springDataJpaMemberRepository) { // 다른 Repository를 사용하고 싶으면 매개변수만 그 Repository로 바꾸면 됨
        this.memberRepository = springDataJpaMemberRepository;
    }


//        List<Member> 리턴
    public List<MemberResponseDto> members() {
        List<Member> members = memberRepository.findAll();
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        for(Member a : members) {
            MemberResponseDto memberResponseDto = new MemberResponseDto();
            memberResponseDto.setId(a.getId());
            memberResponseDto.setName(a.getName());
            memberResponseDto.setEmail(a.getEmail());
            memberResponseDto.setPassword(a.getPassword());
            memberResponseDto.setCreate_time(a.getCreate_time());
            memberResponseDtos.add(memberResponseDto);
        }
        return memberResponseDtos;
    }

//    Member 등록

    public void memberCreate(MemberRequestDto memberRequestDto) {
        Member member = new Member(
                                memberRequestDto.getName(),
                                memberRequestDto.getEmail(),
                                memberRequestDto.getPassword());
        memberRepository.save(member);
    }

//    id에 해당하는 member의 정보를 Dto에 저장
    public MemberResponseDto findMember(int id) throws NoSuchElementException{
        Member member = memberRepository.findById(id).orElseThrow(NoSuchElementException::new);
        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setId(member.getId());
        memberResponseDto.setName(member.getName());
        memberResponseDto.setEmail(member.getEmail());
        memberResponseDto.setPassword(member.getPassword());
        memberResponseDto.setCreate_time(member.getCreate_time());
        return memberResponseDto;
    }
}
