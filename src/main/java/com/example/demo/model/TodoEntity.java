package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoEntity {
    //오브젝트 id
    private String id;
    //유저 아이디
    private String userId;
    //제목
    private String title;
    //완료한 경우
    private boolean done;


}

//@Builder는 오브젝트 생성을 위한 디자인 패턴 중 하나이다.
//롬복이 제공하는 @Builder 어노테이션을 사용하면 우리가 @Builder 클래스를 따로 개발하지 않고도
//Builder 패턴을 사용해 오브젝트를 생성할 수 있다.
//Builder 패턴을 사용하는 것은 생성자를 사용해 오브젝트를 생성하는 것과 비슷하다.
//생성자를 이요하는 것과 비교해 장점이 있다면 생성자 매개변수의 순서를 기억할 필요가 없다는 점이다.