package com.encore.basic.controller;

import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.service.MemberService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.EntityNotFoundException;
import java.util.NoSuchElementException;
import java.util.Optional;

@Controller // @Controller를 통해 싱글톤 컴포넌트로 생성 -> 스프링 빈으로 등록
//@RequiredArgsConstructor
// 스프링 빈이란 스프링이 생성하고 관리하는 객체를 의미
// 제어의 역전 (Inversion of Control) -> IOC 컨테이너가 스프링 빈을 관리(빈을 생성, 의존성 주입)
public class MemberController {
//    @Autowired // 의존성 주입(DI) 방법1 => 필드 주입방식
//    @RequiredArgsConstructor

//    의존성 주입 방법2 => 생성자 주입방식 (가장 많이 사용하는 방법)
//    장점 : final을 통해 상수로 사용가능, 다형성 구현 가능, 순환참조방지
//    생성자가 1개 밖에 없을 경우 Autowired 생략가능
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

////    의존성 주입 방법3 : @RequiredArgsConstructor를 이용한 방식
////    @RequeiredArgsConstructor : @NonNull 어노테이션이 붙어있는 필드 또는 초기화 되지 않은 final 필드를 대상으로 생성자 생성
//    private final MemberService memberService;



//    홈화면
    @GetMapping("/")
    public String home() {
        return "member/header";
    }

//    회원 가입
    @GetMapping("/members/create")
    public String memberCreateScreen() {
        return "member/member-create";
    }
    @PostMapping("/members/create")
    public String memberCreate(MemberRequestDto memberRequestDto) {
        memberService.memberCreate(memberRequestDto);
//        url 리다이렉트
        return "redirect:/members";
////        트랜잭션 및 예외 처리 테스트
//        try {
//            memberService.memberCreate(memberRequestDto);
//            return "redirect:/members";
//        } catch (IllegalArgumentException e) {
//            return "member/404-error-page";
//        }
    }

//    회원 목록 조회
    @GetMapping("/members")
    public String members(Model model) {
        model.addAttribute("memberList", memberService.members());
        return "member/member-list";
    }

//    회원 상세 조회
    @GetMapping("/member/find")
    public String membersDetail(@RequestParam(value = "id") int id, Model model) {
        try {
            MemberResponseDto memberResponseDto = memberService.findMember(id);
            model.addAttribute("member", memberResponseDto);
            return "member/member-detail";
        }catch (EntityNotFoundException e) { // 예외 발생 시
            return "member/404-error-page";
        }
    }

//    회원 정보 삭제
    @GetMapping("/member/delete")
    public String memberDelete(@RequestParam(value = "id") int id) {
        memberService.deleteMember(id);
        return "redirect:/members";
    }

//    회원 정보 수정
    @PostMapping("/member/update")
    public String memberUpdate(MemberRequestDto memberRequestDto) {  // 데이터바인딩
        memberService.updateMember(memberRequestDto);
        return "redirect:/member/find?id="+memberRequestDto.getId();
    }

}


