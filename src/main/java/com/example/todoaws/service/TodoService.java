package com.example.todoaws.service;

import com.example.todoaws.model.TodoEntity;
import com.example.todoaws.persistence.TodoRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TodoService {

    @Autowired
    TodoRepository repository;

    public List<TodoEntity> create(final TodoEntity entity) {
        //Validations
        validate(entity);

        repository.save(entity);
        log.info("Entity Id : () is saved.", entity.getId());

        return repository.findByUserId(entity.getUserId());

    }

    public List<TodoEntity> retrieve(final String userId) {

        return repository.findByUserId(userId);
    }

    public void validate(final TodoEntity entity) {

        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (entity.getUserId() == null) {
            log.warn("Unknown user");
            throw new RuntimeException("Unknown user");
        }
    }
}
