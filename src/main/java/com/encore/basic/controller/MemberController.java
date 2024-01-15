package com.encore.basic.controller;

import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller // @Controller를 통해 싱글톤 컴포넌트로 생성 -> 스프링 빈으로 등록
// 스프링 빈이란 스프링이 생성하고 관리하는 객체를 의미
// 제어의 역전 (Inversion of Control) -> IOC 컨테이너가 스프링 빈을 관리(빈을 생성, 의존성 주입)
public class MemberController {
//    @Autowired // 의존성 주입(DI) 방법1 => 필드 주입방식
//    private MemberService memberService;

//    의존성 주입 방법2 => 생성자 주입방식 (가장 많이 사용하는 방법)
//    장점 : final을 통해 상수로 사용가능, 다형성 구현 가능, 순환참조방지
//    생성자가 1개 밖에 없을 경우 Autowired 생략가능
    private final MemberService memberService;
    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }


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
    @ResponseBody
    public String memberCreate(MemberRequestDto memberRequestDto) {
        memberService.memberCreate(memberRequestDto);
        return "가입 완료";
    }

//    회원 목록 조회
    @GetMapping("/members")
    public String members(Model model) {
        model.addAttribute("memberList", memberService.members());
        return "member/member-list";
    }

}
