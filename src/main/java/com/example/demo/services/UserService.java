package com.example.demo.services;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.User;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

public Optional<User> getUserById(String id) {
    ObjectId objId;
    try {
        objId = new ObjectId(id);
    } catch (IllegalArgumentException e) {
        // invalid id format
        return Optional.empty();
    }
    return userRepository.findById(objId);
}

public void deleteUser(String id) {
    ObjectId objId;
    try {
        objId = new ObjectId(id);
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid user id");
    }
    userRepository.deleteById(objId);
}
}
