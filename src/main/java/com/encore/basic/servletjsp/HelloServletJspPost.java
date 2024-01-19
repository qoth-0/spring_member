package com.encore.basic.servletjsp;

import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/hello-servlet-jsp-post")
public class HelloServletJspPost extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
//        request 꺼내보기
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
//        출력
        System.out.println(name);
        System.out.println(email);
        System.out.println(password);
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
