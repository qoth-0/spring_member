package com.encore.basic.service;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service // @Service를 통해 싱글톤 컴포넌트로 생성 -> 스프링 빈으로 등록
// 스프링 빈이란 스프링이 생성하고 관리하는 객체를 의미
// 제어의 역전 (Inversion of Control) -> IOC 컨테이너가 스프링 빈을 관리(빈을 생성, 의존성 주입)
//@Transactional
// Transactional 어노테이션 클래스 단위로 붙이면 모든 메서드에 각각 Transaction적용
// Transactional을 메서드에 적용하면 한 메서드 단위로 트랜잭션 지정
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

    @Transactional
    public void memberCreate(MemberRequestDto memberRequestDto) throws IllegalArgumentException{
        Member member = new Member(
                                memberRequestDto.getName(),
                                memberRequestDto.getEmail(),
                                memberRequestDto.getPassword());
        memberRepository.save(member);
////        트랜잭션 테스트
//        if(member.getName().equals("kim")) { // 강제 예외 처리
//            throw new IllegalArgumentException();
//        }
    }

//    id에 해당하는 member의 정보를 Dto에 저장
    public MemberResponseDto findMember(int id) throws EntityNotFoundException{
        Member member = memberRepository.findById(id).orElseThrow(()->new EntityNotFoundException("검색하신 ID의 Member가 없습니다."));
        MemberResponseDto memberResponseDto = new MemberResponseDto();
        memberResponseDto.setId(member.getId());
        memberResponseDto.setName(member.getName());
        memberResponseDto.setEmail(member.getEmail());
        memberResponseDto.setPassword(member.getPassword());
        memberResponseDto.setCreate_time(member.getCreate_time());
        return memberResponseDto;
    }

//    회원 정보 삭제
    public void deleteMember(int id) {
        Member member = memberRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        memberRepository.delete(member);
    }

//    회원 정보 수정
    public void updateMember(MemberRequestDto memberRequestDto) {
        Member member = memberRepository.findById(memberRequestDto.getId()).orElseThrow(EntityNotFoundException::new);
        member.updateMember(memberRequestDto.getName(), memberRequestDto.getPassword());
        memberRepository.save(member);
    }
}
