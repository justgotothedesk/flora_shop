package com.example.flora_shop.service;

import com.example.flora_shop.domain.Cart;
import com.example.flora_shop.domain.User;
import com.example.flora_shop.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class CartService {
    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    public Long create(Cart cart) {
        cartRepository.save(cart);
        return cart.getId();
    }

    public Optional<Cart> findByUserId(Long id) {
        Optional<Cart> cartOptional = cartRepository.findByUserId(id);
        return cartOptional;
        //return cartOptional.map(Cart::getId).orElse(null);
//        if (cartOptional.isPresent()) {
//            return cartOptional.get().getId();
//        }
//        return null;
    }
}
