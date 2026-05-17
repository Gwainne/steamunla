package com.steamunla.steamunla.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.steamunla.steamunla.model.User;
import com.steamunla.steamunla.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void registerUser(User user) {
        user.setRole("USER");
        user.setActive(true);
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);
    }
    public User login(String username, String password) {

    User user = userRepository.findByUsername(username)
            .orElse(null);

    if (user == null) {
        return null;
    }

    if (!user.getPassword().equals(password)) {
        return null;
    }

    return user;
}
}