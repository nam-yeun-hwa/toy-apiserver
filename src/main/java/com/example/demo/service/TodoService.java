package com.example.demo.service;

import com.example.demo.model.TodoEntity;
import com.example.demo.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoService{

    @Autowired
    private TodoRepository repository;

    public List<TodoEntity> create(final TodoEntity entity){
        //validations
        validate(entity);

        repository.save(entity);
        log.info("Entity Id : {} is saved.", entity.getId());

        return  repository.findByUserId(entity.getUserId());
    }

    public List<TodoEntity> retrieve(final  String userId){
        return repository.findByUserId(userId);
    }

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

    public String testService(){

        //TodoEntity 생성
        TodoEntity entity = TodoEntity.builder().title("My first todo item").build();
        //TodoEneity 저장
        repository.save(entity);
        TodoEntity savedEntity = repository.findById(entity.getId()).get();
        return savedEntity.getTitle();
    }
}
