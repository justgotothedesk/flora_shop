package com.example.flora_shop.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor(access = AccessLevel.PROTECTED) // 외부에서 해당 Entity 생성 불가
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nickname;
    private String username; // 아이디
    private String password;
    private String address;
    private String phoneNumber;

    @Builder
    public User(String name, String nickname, String username, String password, String address, String phoneNumber) {
        this.name = name;
        this.nickname = nickname;
        this.username = username;
        this.password = password;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }
}