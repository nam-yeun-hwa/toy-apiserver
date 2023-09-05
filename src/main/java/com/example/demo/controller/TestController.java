package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("test")
public class TestController {
    @GetMapping
    public String testController(){
        return "hello World!";
    }
}

//@RestController를 이용하면 hppt 관련된 코드 및 요청/응답 매핑을 스프링이 알아서 해준다.
//localhost:8080/test의 GET 호출에는 testController()가 호출 된다.
