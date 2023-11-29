package com.helendong.quiz.quizapp.service;

import com.helendong.quiz.quizapp.model.User;
import com.helendong.quiz.quizapp.repository.UserRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User newUser) {
        try {
            validateUser(newUser);
            return userRepository.save(newUser);
        } catch (ValidationException ve) {
            throw new ValidationException("User registration validation failed: " + ve.getMessage());
        } catch (Exception e) {
            throw new RuntimeException("User registration failed: " + e.getMessage());
        }
    }

    private void validateUser(User newUser) {
        String username = newUser.getUsername();
        if (username == null || username.isEmpty()) {
            throw new ValidationException("Username cannot be empty");
        }

        String password = newUser.getPassword();
        if (password == null || password.isEmpty()) {
            throw new ValidationException("Password cannot be empty");
        }

        String email = newUser.getEmail();
        if (email == null || email.isEmpty() || !email.matches(".+@.+\\..+")) {
            throw new ValidationException("Invalid email address");
        }
    }

    public User getUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User updateUser(Long userId, User updatedUser) {
        if (userRepository.existsById(userId)) {
            updatedUser.setId(userId);
            return userRepository.save(updatedUser);
        } else {
            return null;
        }
    }

    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
