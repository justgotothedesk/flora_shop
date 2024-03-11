package com.example.flora_shop.repository;

import com.example.flora_shop.domain.Cart;
import com.example.flora_shop.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUserId(Long id);
}
