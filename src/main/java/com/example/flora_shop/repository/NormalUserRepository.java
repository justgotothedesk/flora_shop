package com.example.flora_shop.repository;

import com.example.flora_shop.domain.Normal_User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NormalUserRepository extends JpaRepository<Normal_User, String> {
    Optional<Normal_User> findByUsernameAndPassword(String username, String password);
}
