package com.encore.basic.servletjsp;

import com.encore.basic.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello-servlet-rest-get")
public class HelloServletRestGet extends HttpServlet {
//    Hello객체 조립 -> res - Contente-type: json, out.print(hello)
//    out.print()는 String타입을 받으므로 hello객체를 json(stirng)으로 직렬화해서 넣어줘야 함 - ObjectMapper의 writeValueAsString() 사용
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        Hello hello = new Hello();
        hello.setName("qoth");
        hello.setEmail("qoth@a.com");
        hello.setPw("1234");
        ObjectMapper objectMapper = new ObjectMapper();
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");
        PrintWriter out = res.getWriter();
        out.print(objectMapper.writeValueAsString(hello));
        out.flush();
    }
}
