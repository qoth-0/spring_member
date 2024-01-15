package com.encore.basic.controller;

import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.service.MemberService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MemberController {
    private final MemberService memberService;
    MemberController() {
        memberService = new MemberService();
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
