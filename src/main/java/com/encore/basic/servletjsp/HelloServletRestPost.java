package com.encore.basic.servletjsp;

import com.encore.basic.domain.Hello;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello-servlet-rest-post")
public class HelloServletRestPost extends HttpServlet {
//    json으로 데이터 주기 json으로 넘어온 데이터를 역직렬화(ObjectMapper-readValue)로 hello저장 콘솔에 띄우기, respose ok
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        ObjectMapper objectMapper = new ObjectMapper();
//        Json(String)을 객체형태로 변환
        Hello hello = objectMapper.readValue(req.getReader(), Hello.class);
        System.out.println(hello);
        //        응답(header)
        res.setContentType("text/plain");
        res.setCharacterEncoding("UTF-8");
//        응답(body)
        PrintWriter out = res.getWriter();
        out.print("ok");
//        버퍼를 통해 조립이 이루어지므로, 버퍼를 비우는 과정
        out.flush();
    }
}
