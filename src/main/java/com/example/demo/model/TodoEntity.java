package com.example.demo.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name="Todo")
public class TodoEntity {
    //오브젝트 id
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;
    //유저 아이디
    private String userId;
    //제목
    private String title;
    //완료한 경우
    private boolean done;


}

//엔티티 이름을 부여하고 싶다면
//@Entity(name = "투두") 어노테이션을 사용 한다.

//보통 데이터베이스 테이블 마다 그에 상응하는 엔티티 클래스가 존재한다고 언급했다.
//하나의 엔티티 인스턴서는 데이터베이스 테이블의 한 행에 해당한다.
//엔티티 클래스는 클래스 그 자체가 테이블을 정의 해야 한다.
//이 말은 즉 ORM이 엔티티를 보고 어떤 테이블의 어던 필드에 매핑해야 하는지 알 수 있어야 한다는 뜻이다.
//또 어떤 필드가 기본키 인지 왜래 키 인지 구분할 수 있어야 한다.

//자바 클래스를 엔티티로 정의할 때 주의해야 하는 점이 몇 가지 있다.
//첫번째로 클래스에는 매개변수가 없는 생성자. NoArgsConstructor가 필요하다.
//두번째로 Getter/Setter가 필요하다.
//세번째는 기본키를 지정해줘야 한다.class

//@Builder는 오브젝트 생성을 위한 디자인 패턴 중 하나이다.
//롬복이 제공하는 @Builder 어노테이션을 사용하면 우리가 @Builder 클래스를 따로 개발하지 않고도
//Builder 패턴을 사용해 오브젝트를 생성할 수 있다.
//Builder 패턴을 사용하는 것은 생성자를 사용해 오브젝트를 생성하는 것과 비슷하다.
//생성자를 이요하는 것과 비교해 장점이 있다면 생성자 매개변수의 순서를 기억할 필요가 없다는 점이다.