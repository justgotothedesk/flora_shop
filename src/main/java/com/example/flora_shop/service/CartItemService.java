package com.example.flora_shop.service;

import com.example.flora_shop.domain.CartItem;
import com.example.flora_shop.repository.CartItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CartItemService {
    private final CartItemRepository cartItemRepository;

    public CartItemService(CartItemRepository cartItemRepository) {
        this.cartItemRepository = cartItemRepository;
    }

    public Long create(CartItem cartItem) {
        cartItemRepository.save(cartItem);
        return cartItem.getId();
    }

    public List<CartItem> findByCartID(Long id) {
        return cartItemRepository.findByCartId(id);
    }

    public void removeCartItem(Long id) {
        Optional<CartItem> temp = cartItemRepository.findById(id);
        cartItemRepository.delete(temp.get());
    }

    public Optional<CartItem> findByCartIdAndItemId(Long CartId, Long ItemId) {
        return cartItemRepository.findByCartIdAndItemId(CartId, ItemId);
    }
}
