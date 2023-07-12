package com.example.todospringboot.persistence;

import com.example.todospringboot.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    List<TodoEntity> findByUserId(String userid);

//    @Query("select * from TodoEntity t where t.userId = ?1")
//    List<TodoEntity> findByUserIdQuery(String userid);

}
