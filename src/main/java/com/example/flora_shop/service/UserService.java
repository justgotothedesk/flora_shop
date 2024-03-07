package com.example.flora_shop.service;

import com.example.flora_shop.domain.User;
import com.example.flora_shop.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findByIDandPassword(String username, String password) {
        return userRepository.findByUsernameAndPassword(username, password);
    }

    public Long create(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public Optional<User> findByID(Long id) {
        return userRepository.findById(id);
    }
}
