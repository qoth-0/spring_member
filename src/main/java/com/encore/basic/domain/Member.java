package com.encore.basic.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Entity // Entity 어노테이션을 통해 mariadb의 테이블 및 컬럼을 자동생성
// 클래스명은 테이블명, 변수명은 컬럼명
@NoArgsConstructor
public class Member {
    @Setter
    @Id // pk설정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment 설정
    private int id;
//    String은 DB의 varchar로 변환(기본길이 255)
    private String name;
    @Column(nullable = false, length = 50) // Not null, 길이지정
    private String email;
    private String password;
    @Setter
    @Column(name = "created_time") // name옵션 : DB의 컬럼명 별도 지정
    private LocalDateTime create_time;
    public Member(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.create_time = LocalDateTime.now();
    }
}



















