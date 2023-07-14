package com.example.todospringboot.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(uniqueConstraints ={@UniqueConstraint(columnNames = "username")})
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    private String username;
    private String password; // OAuth를 이용한 SSO이용할 경우 password 필요없으므로 nullable = true(기본값)
    private String role;
    private String authProvider;

}
