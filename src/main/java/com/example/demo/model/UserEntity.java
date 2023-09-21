package com.example.demo.model;

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
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = "username")})
public class UserEntity {
    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid")
    private String id;

    @Column(nullable = false)
    //아이디로 사용할 유저 네임. 이메일
    private String username;

    //패스워드
    private String password;

    // 사용자의 롤 (예: 어드민 일반 사용자)
    private String role;

    //이후 OAuth에서 사용할 유저 정보 제공자 : github
    private String authProvider;


}
