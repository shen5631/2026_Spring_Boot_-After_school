package com.mirim.board1;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class HelloController {
    // 브라우저 -> 내장 톰캣 -> (교통정리 담당) -> HelloController .hello()
    // 교통정리 담당 = DispatcherServlet
    // DispatcherServlet 하는 일
    // - 주소를 보고 어느 메소드로 보낼지고른다.
    // - 목적지가 없다면 404를 응답한다.

    // CRUD : Create / Read(Get) / Update / Delete
    // 브라우저에서 주소창으로 직접 요청할때는 GET이외에 메서드는 보낼수없다.
    // 1. 게시글 작성하는 어떻게 테스트할까?
    // 2. RestController, GetMapping 뭐하는 애들일까?

    //@Value 를 사용하여 변수처럼 불러올수있음 변수명은 반드시 포매팅 해줘야함 ${변수}
    @Value("${my.message}")
    private String message;

    @GetMapping("/")
    public String hello(){
        return "hello";
    }

    @GetMapping("/hello")
    public String hello2(){
        return message;
//        throw new RuntimeException("에러");
    }

    @GetMapping("/hello-map")
    public Map<String, Object> helloMap() {
        return Map.of("name","김미림", "grade" ,2);
    }
}
