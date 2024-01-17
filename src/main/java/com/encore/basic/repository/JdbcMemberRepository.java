package com.encore.basic.repository;

import com.encore.basic.domain.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Repository
public class JdbcMemberRepository implements MemberRepository{
//    Datasource는 DB와 JDBC에서 사용되는 DB연결 드라이버 객체
    @Autowired
    private DataSource dataSource; // DB정보가 저장됨

//    member 테이블 전체 조회
    @Override
    public List<Member> findAll() {
        List<Member> members = new ArrayList<>();
        try {
            Connection connection = dataSource.getConnection(); // Connection 객체로
            String sql = "select * from member;"; // ?에 변수가 들어옴
            PreparedStatement preparedStatement = connection.prepareStatement(sql); // sql 만들기
            ResultSet resultSet = preparedStatement.executeQuery(); // 결과 저장
            while(resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String email = resultSet.getString("email");
                String password = resultSet.getString("password");
                LocalDateTime now = resultSet.getTimestamp("create_time").toLocalDateTime();
                System.out.println(now.toString());
                Member member = new Member(name, email, password);
                member.setId(id);
                member.setCreate_time(now);
                members.add(member);
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return members;
    }


//   member테이블 데이터 삽입
    @Override
    public Member save(Member member) {
        try {
            Connection connection = dataSource.getConnection(); // Connection 객체로
            String sql = "insert into member(name, email, password) values(?,?,?);"; // ?에 변수가 들어옴
            PreparedStatement preparedStatement = connection.prepareStatement(sql); // sql 만들기
            preparedStatement.setString(1, member.getName());
            preparedStatement.setString(2, member.getEmail());
            preparedStatement.setString(3, member.getPassword());
            preparedStatement.executeUpdate(); // sql 실행
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //    member테이블 id로 조회
    @Override
    public Optional<Member> findById(int id) {
        Member member = null;
        try {
            Connection connection = dataSource.getConnection(); // Connection 객체로
            String sql = "select * from member where id = ?;"; // ?에 변수가 들어옴
            PreparedStatement preparedStatement = connection.prepareStatement(sql); // sql 만들기
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery(); // 결과 저장
            resultSet.next();
            String name = resultSet.getString("name");
            String email = resultSet.getString("email");
            String password = resultSet.getString("password");
            LocalDateTime now = resultSet.getTimestamp("create_time").toLocalDateTime();
            System.out.println(now.toString());
            member = new Member(name, email, password);
            member.setId(id);
            member.setCreate_time(now);
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return Optional.ofNullable(member);
    }

    @Override
    public void delete(Member member) {

    }
}
