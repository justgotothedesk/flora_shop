package com.example.flora_shop.service;

import com.example.flora_shop.domain.Normal_User;
import com.example.flora_shop.repository.NormalUserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NormalUserService {
    private final NormalUserRepository normalUserRepository;

    public NormalUserService(NormalUserRepository normalUserRepository) {
        this.normalUserRepository = normalUserRepository;
    }

    public Long create(Normal_User normal_user) {
        normalUserRepository.save(normal_user);
        return normal_user.getId();
    }

    public Optional<Normal_User> findbyIDandPassword(String id, String password) {
        return normalUserRepository.findByUsernameAndPassword(id, password);
    }

}
