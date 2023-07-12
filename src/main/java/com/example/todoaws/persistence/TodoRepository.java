package com.example.todoaws.persistence;

import com.example.todoaws.model.TodoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoEntity, String> {

    List<TodoEntity> findByUserId(String userid);

//    @Query("select * from TodoEntity t where t.userId = ?1")
//    List<TodoEntity> findByUserIdQuery(String userid);

}
