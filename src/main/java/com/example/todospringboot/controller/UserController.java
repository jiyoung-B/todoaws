package com.example.todospringboot.controller;

import com.example.todospringboot.dto.ResponseDTO;
import com.example.todospringboot.dto.UserDTO;
import com.example.todospringboot.model.UserEntity;
import com.example.todospringboot.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequestMapping("/auth")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUsr(@RequestBody UserDTO userDTO) {
        try {
            if (userDTO == null || userDTO.getPassword() == null) {
                throw new RuntimeException("Invalid Password value");
            }

            UserEntity user = UserEntity.builder()
                    .username(userDTO.getUsername())
                    .password(userDTO.getPassword())
                    .build();
            UserEntity registeredUser = userService.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .id(registeredUser.getId())
                    .username(registeredUser.getUsername())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity.badRequest().body(responseDTO);

        }

    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        UserEntity user = userService.getByCredentials(userDTO.getUsername(), userDTO.getPassword());

        if (user != null) {
            final UserDTO responseUserDTO = userDTO.builder()
                    .username(user.getUsername())
                    .id(user.getId())
                    .build();
            return ResponseEntity.ok().body(responseUserDTO);
        }else{
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed.")
                    .build();

            return ResponseEntity.badRequest().body(responseDTO);
        }
    }
}
