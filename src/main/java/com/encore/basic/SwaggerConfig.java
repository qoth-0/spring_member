package com.encore.basic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;

// @Configuration + @Bean 은 서드파티를 스프링 빈으로 등록할 때 사용
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
//    Docket : Swagger 구성의 핵심 기능 클래스
//    Swagger : postman 같이 요청을 대신해주는 서비스? ui 제공
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select() // 어떤 컨트롤러 또는 어떤 api를 Swagger 문서에 포함시킬지 선택
//                 모든  RequestHandler들을 문서화 대상으로 선택한다는 설정
                .apis(RequestHandlerSelectors.any())
                // url 패턴 정의 (*: 직계, **: 손자까지)
                .paths(PathSelectors.ant("/rest/**")) // Controller에서 설정한 /rest 포함 url
                .build();
    }
////    swagger의 authorize자물쇠 버튼 활성화를 위해서는  jwt, session 등의 별도 설정 필요

}

    // 종승's
//@Configuration
//@EnableSwagger2
//// url: localhost:8080/swagger-ui/
//public class SwaggerConfig {
//    @Bean
//    // Docket: Swagger 구성의 핵심 기능 클래스
//    public Docket api(){
//        return new Docket(DocumentationType.SWAGGER_2)
//                .securitySchemes(Arrays.asList(apiKey()))
//                .select() // 어떤 컨트롤러 또는 api를 Swagger 문서에 포함시킬 지 선택
//
//                .apis(RequestHandlerSelectors.any()) // 모든 RequestHandler들을 문서화 대상으로 선택한다는 설정
//                // url 패턴 정의, * 1개는 직계, * 2개는 자손 폴더 디렉터리까지
//                .paths(PathSelectors.ant("/rest/**"))
//                .build();
//    }
//
//    // swagger의 authorize 자물쇠 버튼 활성화를 위해서는 jwt, session 등의 별도의 설정 필요
//    private ApiKey apiKey() {
//        return new ApiKey("Authorization", "Authorization", "header");
//    }
//}