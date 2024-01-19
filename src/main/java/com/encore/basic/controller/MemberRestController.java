package com.encore.basic.controller;

import com.encore.basic.domain.MemberRequestDto;
import com.encore.basic.domain.MemberResponseDto;
import com.encore.basic.service.MemberService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@Api(tags = "회원관리서비스") // swagger에 보여질 이름
@RestController // @ResponseBody 포함된 Controller
@RequestMapping("/rest")
public class MemberRestController {

    private final MemberService memberService;
    @Autowired
    public MemberRestController(MemberService memberService) {
        this.memberService = memberService;
    }

//    회원 가입
    @PostMapping("/members/create")
    public String memberCreate(@RequestBody MemberRequestDto memberRequestDto) { // json으로 데이터 받기
        memberService.memberCreate(memberRequestDto);
        return "ok";
    }

//    회원 목록 조회
    @GetMapping("/members")
    public List<MemberResponseDto> members() {
        return memberService.members();
    }

//    회원 상세 조회
    @GetMapping("/member/find/{id}")
    public ResponseEntity<Map<String, Object>> membersDetail(@PathVariable int id) {
        MemberResponseDto memberResponseDto = null;
        try {
            memberResponseDto = memberService.findMember(id);
            return ResponseEntityController.resMessage(HttpStatus.OK, memberResponseDto);
        }catch(EntityNotFoundException e) {
            e.printStackTrace();
            return ResponseEntityController.errResMessage(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

//    회원 정보 삭제
    @DeleteMapping("/member/delete/{id}")
    public String memberDelete(@PathVariable int id) {
        memberService.deleteMember(id);
        return "ok";
    }

//    회원 정보 수정
    @PatchMapping("/member/update")
    public MemberResponseDto memberUpdate(@RequestBody MemberRequestDto memberRequestDto) {
        memberService.updateMember(memberRequestDto);
        return memberService.findMember(memberRequestDto.getId());
    }
}


