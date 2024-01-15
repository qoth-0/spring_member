package com.encore.basic.domain;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

// 롬복으로 메소드 어노테이션으로 대체
// @Getter
// @Setter
@Data // Getter, Setter, toString, equals 등 사전 구현
public class Hello {
    private String name;
    private String email;
    private String pw;
}


