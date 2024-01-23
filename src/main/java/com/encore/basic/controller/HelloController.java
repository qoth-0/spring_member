package com.encore.basic.controller;

import com.encore.basic.domain.Hello;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

// @ : 어노테이션
@Controller // http 통신을 위함
// 모든 요청에 ResponseBody를 붙이고 싶다면, @RestController 사용
// 클래스 차원에서 url 경로를 지정하고 싶다면 @RequestMapping을 클래스 위에 선언하면서 경로지정
@RequestMapping("/hello") // class로 url을 공통화 시킴
public class HelloController {
    @GetMapping("/string") // hello/string
//    @RequestMapping(value = "string", method = RequestMethod.GET) // @GetMapping 대신 RequestMapping의 method=get도 쓸수있음
    @ResponseBody // response 시 data만을 return
    public String helloString() {
        return "hello_string";
    }

    @GetMapping("/json")
    @ResponseBody
//    객체를 return하면 json형태로 반환
    public Hello helloJson() {
        Hello hello = new Hello();
        hello.setName("qoth");
        hello.setEmail("aaa@naver.com");
        hello.setPw("1234");
        System.out.println(hello); // toString 구현 확인
        return hello;
    }

    @GetMapping("/screen")
//    @ResponseBody가 없고 return타입이 String이면 templates 밑에 html파일 리턴
    public String helloScreen() {
        return "screen";
    }


    @GetMapping("/screen-model")
    public String helloScreenModel(Model model) {
//        화면에 data를 넘기고 싶을 때는 model 객체 사용
//        model에 key:value형식으로 전달
        model.addAttribute("myData", "hongildong"); // myData:hongildong -> screen.html로 전달
        return "screen";
    }

    @GetMapping("/screen-model-param")
//    @RequestParam : parameter 호출 방식으로 데이터 받음
//    ?name=hongildong
    public String helloScreenModelParam(@RequestParam(value = "name") String inputName, Model model) { // name이 key
        model.addAttribute("myData", inputName);
        return "screen";
    }

    @GetMapping("/screen-model-path/{id}")
//    pathvariable방식은 url을 통해 자원의 구조를 명확하게 표현할 수 있음
//    좀 더 RestFul API 디자인에 적합
    public String helloScreenModelPath(@PathVariable int id, Model model) { // url로 id값을 받아서 사용
        model.addAttribute("myData", id);
        return "screen";
    }

    //    form태그로 x-www 데이터 처리
    @GetMapping("/form-screen") // 첫 화면
    public String formScreen() {
        return "form-screen";
    }

    @PostMapping("/form-post-handle") // form태그로 post요청
    @ResponseBody
//    form태그를 통해 들어오는 데이터는 body에 들어오며, 파라미터방식(key1=value1&key2=value2)과 같은 형식
    public String formPostHandle(@RequestParam(value = "name") String name,
                                 @RequestParam(value = "email") String email,
                                 @RequestParam(value = "password") String password) {
        System.out.println("이름 : " + name);
        System.out.println("email : " + email);
        System.out.println("password : " + password);
        return "정상 처리";
    }

    @PostMapping("/form-post-handle2")
    @ResponseBody
//    Spring에서 Hello클래스의 인스턴스를 자동 매핑하여 생성
//    form-data형식 즉, x-www-url인코딩 형식의 경우 사용 - RequestParam 대신
//    이를 데이터 바인딩이라 부른다.(Hello클래스에 setter 필수)
    public String formPostHandle(Hello hello) {
        System.out.println(hello);
        return "정상 처리";
    }


    //    json데이터 처리
    @GetMapping("json-screen") // 첫 화면
    public String jsonScreen() {
        return "json-screen";
    }

    @PostMapping("/json-post-handle1") // json으로 post요청
    @ResponseBody
//    @RequestBody는 json으로 post요청이 들어왔을 때 body에서 data를 꺼내기 위해 사용
    public String jsonPostHandle1(@RequestBody Map<String, String> body) {     // body로 들어온 json데이터를 Map객체로 변환
        System.out.println(body.get("name"));
        System.out.println(body.get("email"));
        System.out.println(body.get("password"));
        Hello hello = new Hello(); // 객체를 만들어서 객체에 데이터 저장
        hello.setName(body.get("name"));
        hello.setEmail(body.get("email"));
        hello.setPassword(body.get("password"));
        return "ok";
    }

    @PostMapping("/json-post-handle2")
    @ResponseBody
    public String jsonPostHandle2(@RequestBody JsonNode body) { // JsonNode로도 받을 수 있음
        Hello hello = new Hello();
        hello.setName(body.get("name").asText());
        hello.setEmail(body.get("email").asText());
        hello.setPassword(body.get("password").asText());
        return "ok";
    }

    @PostMapping("/json-post-handle3")
    @ResponseBody
    public String jsonPostHandle3(@RequestBody Hello hello) { // 클래스에 받기
        System.out.println(hello);
        return "ok";
    }

//    DispatcherServlet : 요청 정보를 담은 HttpServletRequest 객체 생성
//    HttpServletRequest 객체를 주입해서 요청 내용을 꺼내서 사용 가능
    @PostMapping("/http-servlet")
    @ResponseBody
    public String httpServletTest(HttpServletRequest req) {
//        HttpServletRequest객체에서 header정보 추출
        System.out.println(req.getContentType());
        System.out.println(req.getMethod());
//        session : 로그인(auth) 정보에서 필요한 정보값을 추출할 때 많이 사용
        System.out.println(req.getSession());
        System.out.println(req.getHeader("Accept"));

//        HttpServletRequest객체에서 body정보 추출
        System.out.println(req.getParameter("test1")); // form 태그로 보낸 데이터를 추출
        System.out.println(req.getParameter("test2"));
//        req.getReader()를 통해 BufferedReader로 받아 직접 파싱
        return "ok";
    }

//    controller와 jsp의 조합
    @GetMapping("/hello-servlet-jsp-get")
    public String helloServletJspGet(Model model) {
        model.addAttribute("myData", "jsp test data");
        return "hello-jsp";
    }

//
    public void helloBuilderTest() {
        Hello hello = Hello.builder()
                .name("hong")
                .email("naver.com")
                .password("1234")
                .build();
    }

}
