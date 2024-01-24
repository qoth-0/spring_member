package com.encore.basic.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 롬복으로 메소드 어노테이션으로 대체
// @Getter
// @Setter
@Data // Getter, Setter, toString, equals 등 사전 구현
@NoArgsConstructor
public class Hello {
    private String name;
    private String email;
    private String password;

    // Builder 패턴 직접 구현
    public Hello(MyBuilder myBuilder) {
        this.name = myBuilder.name;
        this.email = myBuilder.email;
        this.password = myBuilder.password;
    }

//    MyBuilder 객체 생성
    public static MyBuilder builder() {
        return new MyBuilder();
    }

    public static class MyBuilder{
        private String name;
        private String email;
        private String password;

        // MyBuilder 객체의 name, email, password를 세팅하는 메소드
        public MyBuilder name(String name) {
            this.name = name;
            return this;
        }
        public MyBuilder email(String email) {
            this.email = email;
            return this;
        }
        public MyBuilder password(String password) {
            this.password = password;
            return this;
        }
//        build : 엔티티에 각 메소드로 세팅된 MyBuilder를 적용
        public Hello build() {
            return new Hello(this);
        }
    }
}





