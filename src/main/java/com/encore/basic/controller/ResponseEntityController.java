package com.encore.basic.controller;

import com.encore.basic.domain.Member;
import com.encore.basic.domain.MemberResponseDto;
import org.apache.tomcat.util.http.parser.HttpParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("res/entity")
public class ResponseEntityController {

//    @ResponseStatus 방식 - 객체가 아닌 어노테이션
    @GetMapping("response-status")
    @ResponseStatus(HttpStatus.CREATED)
    public String responseStatus() {
        return "OK";
    }

    @GetMapping("response-status2")
    @ResponseStatus(HttpStatus.CREATED)
    public Member responseStatus2() {
        Member member = new Member("qoth-0","bbb@naver.com","0000");
        return member;
    }

//    ResponseEntity객체를 직접 생성한 방식
    @GetMapping("custom1")
    public ResponseEntity<Member> custom1() {
        Member member = new Member("qoth-0","bbb@naver.com","0000");
        return new ResponseEntity<>(member, HttpStatus.CREATED); // HttpStatus만 Entity에서 넣으면 상태코드만 전달
    }

    //    ResponseEntity<String>일 경우 text/html로 설정
    @GetMapping("custom2")
    public ResponseEntity<String> custom2() {
        String html = "<h1>없는 ID입니다.</h1>";
        return new ResponseEntity<>(html, HttpStatus.NOT_FOUND);
    }

////    map형태의 메시지 커스텀
////    실패 시
//    @GetMapping("map-custom1")
//    public ResponseEntity<Map<String, String>> customMap1() {
//        Map<String, String> body = new HashMap<>();
//        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
//        body.put("status", Integer.toString(status.value())); // 500
//        body.put("error message", status.getReasonPhrase()); // Internal Server Error
//        return new ResponseEntity<>(body, status);
//    }
//
////    정상 시 status 201, message: 객체
//    @GetMapping("map-custom2")
//    public ResponseEntity<Map<String, Object>> customMap2() {
//        Map<String, Object> body = new HashMap<>();
//        HttpStatus status = HttpStatus.CREATED;
//        Member member = new Member("qoth-0","bbb@naver.com","0000");
//        body.put("status", Integer.toString(status.value())); // 201
//        body.put("message", member);
//        return new ResponseEntity<>(body, status);
//    }

//  에러 공통화 동적으로 하기 - MemberRestController(그외 다른파일에서도)에서 함수를 불러서 사용할 수 있도록
    public static ResponseEntity<Map<String, Object>> errResMessage(HttpStatus status, String message) { // 매개변수로 상태코드만
        Map<String, Object> body = new HashMap<>();
        body.put("status", Integer.toString(status.value()));
        body.put("error message", message);
        body.put("status message", status.getReasonPhrase());
        return new ResponseEntity<>(body, status);
    }

    public static ResponseEntity<Map<String, Object>> resMessage(HttpStatus status, Object object) { // 매개변수로 상태코드와 객체가 넘어오도록
        Map<String, Object> body = new HashMap<>();
        body.put("status", Integer.toString(status.value()));
        body.put("message", object);
        return new ResponseEntity<>(body, status);
    }

//    메서드 체이닝 : ResponseEntity의 클래스메서드 사용
    @GetMapping("chaining1")
    public ResponseEntity<Member> chaining1() {
        Member member = new Member("qoth-0","bbb@naver.com","0000");
        return ResponseEntity.ok(member);
    }

    @GetMapping("chaining2")
    public ResponseEntity<String> chaining2() {
        return ResponseEntity.notFound().build();
    }

    @GetMapping("chaining3")
    public ResponseEntity<Member> chaining3() {
        Member member = new Member("qoth-0","bbb@naver.com","0000");
        return ResponseEntity.status(HttpStatus.CREATED).body(member);
    }
}
