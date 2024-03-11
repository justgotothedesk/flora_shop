package com.example.flora_shop.service;

import com.example.flora_shop.domain.CartItem;
import com.example.flora_shop.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
@Transactional
public class CartItemService {
    @Autowired
    private CartItemRepository cartItemRepository;

    public Long save(CartItem cartItem) {
        cartItemRepository.save(cartItem);
        return cartItem.getId();
    }

    public List<CartItem> findByCartID(Long id) {
        return cartItemRepository.findByCartId(id);
    }

    public void removeCartItem(Long id) {
        CartItem temp = cartItemRepository.findByCartItemId(id);
        cartItemRepository.delete(temp);
    }
}
