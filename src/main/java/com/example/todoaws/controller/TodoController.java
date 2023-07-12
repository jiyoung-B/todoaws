package com.example.todoaws.controller;

import com.example.todoaws.dto.ResponseDTO;
import com.example.todoaws.dto.TodoDTO;
import com.example.todoaws.model.TodoEntity;
import com.example.todoaws.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TodoController {
    @Autowired
    private TodoService service;

    @PostMapping("/todo")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO dto) {

        try {
            String temporaryUserId = "temporary-user";
            TodoEntity entity = TodoDTO.toEntity(dto);
            entity.setId(null);

            entity.setUserId(temporaryUserId);

            List<TodoEntity> entities = service.create(entity);
            List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());

            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().error(error).build();
            return ResponseEntity.badRequest().body(response);

        }

    }

    @GetMapping("/todo")
    public ResponseEntity<?> retrieveTodoList(){
        String temporaryUserId = "temporary-user";
        List<TodoEntity> entities = service.retrieve(temporaryUserId);
        List<TodoDTO> dtos = entities.stream().map(TodoDTO::new).collect(Collectors.toList());
        ResponseDTO<TodoDTO> response = ResponseDTO.<TodoDTO>builder().data(dtos).build();
        return ResponseEntity.ok().body(response);

    }
}
