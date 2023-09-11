package com.example.demo.persistence;

import com.example.demo.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {
    List<TodoEntity> findByUserId(String userId);

}

//- 첫 번째 매개변수인 T는 테이블에 매핑할 **엔티티 클래스**이고,
//- 두번째 매개변수 ID는 이 **앤티티의 기본 키 타입**이다.
