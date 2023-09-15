package com.example.demo.controller;

import com.example.demo.dto.ResponseDTO;
import com.example.demo.dto.TodoDTO;
import com.example.demo.model.TodoEntity;
import com.example.demo.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("todo")
public class TodoController {
    @Autowired
    private TodoService service;

    /**
     * @function createTodo
     * @param dto
     * @return
     * @description dto를 받아 엔티티로 변환후 서비스로 엔티티를 전달해 서비스에서 엔티티를 저장 한 후 엔티티 리스트를 리턴 하고 엔티티 리스트를 dto리스트로 변환하여 사용자에게 반환해준다.
     */
    @PostMapping
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto){
        try {
            String temporaryUserId = "temporary-user";
            //dto를 엔티티로 변환
            TodoEntity entity = TodoDTO.todoEntity(dto);

            //id를 null로 초기화한다.
            //생성 당시에는 id가 없어야 하기 때문이다.
            entity.setId(null);
            //임시 유저 아이디를 설정해 준다. 이 부분은 4장 인증과 인가에서 수정할 예정이다.
            //인증과 인가 기능이 없으므로 한 유저(temporary-User)만 로그인 없이 사용 가능한 애플리케이션인 셈이다.
            entity.setUserId(temporaryUserId);

            //서비스를 이용한 Todo엔티티를 생성한다.
            List<TodoEntity> entities = service.create(entity);
//            log.info("entity : {} 전달", entities);

            //자바스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO리스트로 변환한다.
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            //변환된 dto 리스트를 이용해 ResponseDTO를 초기화한다.
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            //ResponseDTO를 리턴한다.
            return ResponseEntity.ok().body(response);
        }catch (Exception e){
            String error = e.getMessage();

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return  ResponseEntity.badRequest().body(response);
        }
    }

    /**
     * @function retrieveTodoList
     * @return temporary-user의 게시글 리스트 리턴
     */
    @GetMapping
    public ResponseEntity<?> retrieveTodoList(){
        String temporaryUserId = "temporary-user";

        List<TodoEntity> entities = service.retrieve(temporaryUserId);

        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return  ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateTodo(@RequestBody TodoDTO dto){
        String temporaryUserId = "temporary-user";

        //dto를 entity로 변환
        TodoEntity entity = TodoDTO.todoEntity(dto);

        //temporary-user로 유저 아이디를 초기화 해준다.
        entity.setUserId(temporaryUserId);

        //service를 이용해 enitty를 업데이트한다.
        List<TodoEntity> entities = service.update(entity);

        //자바스트림을 이용해 리턴된 엔티티 리스트를 TodoDTO 리스트로 변환 한다.
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

        //변환된 TodoDTO 리스트를 이용해 ResponseDTO를 초기화 한다.
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/test")
    public ResponseEntity<?> testTodo(){
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);

        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }
}
