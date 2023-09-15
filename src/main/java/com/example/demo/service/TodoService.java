package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class TodoService{
    @Autowired
    private TodoRepository repository;

    /**
     * @function create
     * @param entity
     * @return 게시글 작성
     */
    public List<TodoEntity> create(final TodoEntity entity){
        //validations
        validate(entity);

        repository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());

        return  repository.findByUserId(entity.getUserId());
    }

    /**
     * @function retrieve
     * @param userId
     * @return 임시사용자 게시글 리스트
     */
    public List<TodoEntity> retrieve(final  String userId){
        return repository.findByUserId(userId);
    }

    /**
     * @function validate
     * @description 게시글 작성시 validate
     * @param entity
     */
    private void validate(final TodoEntity entity){
        if(entity == null){
            log.warn("Entity cannot be null.");

            throw new RuntimeException("Entity cannot be null");
        }

        if(entity.getUserId() == null){
            log.warn("Unknown user");
            throw new RuntimeException("Unknow user.");
        }
    }

    public List<TodoEntity> update(final TodoEntity entity){
        //저장할 entity가 유효한지 확인한다.
        validate(entity);

        //넘겨받은 엔티티 id를 이용해 TodoEntity를 가져온다.
        final Optional<TodoEntity> original = repository.findById(entity.getId());

        original.ifPresent(todo -> {
            //반환된 TodoEntity가 존재하면 값을 새 entity의 값으로 덮어 씌운다.
            todo.setTitle(entity.getTitle());
            todo.setDone(entity.isDone());

            //데이타 베이스에 새 값을 저장한다.
            repository.save(todo);
        });

        return retrieve(entity.getUserId());
    }

    public String testService(){

        //TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        //TodoEneity 저장
        repository.save(entity);
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }
}
