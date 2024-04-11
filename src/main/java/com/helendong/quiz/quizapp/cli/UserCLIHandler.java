package com.helendong.quiz.quizapp.cli;

import com.helendong.quiz.quizapp.LoginMenu;
import com.helendong.quiz.quizapp.controller.UserController;
import com.helendong.quiz.quizapp.dto.UserDTO;
import com.helendong.quiz.quizapp.utility.UserInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class UserCLIHandler {

    private final UserController userController;
    private final UserInputService userInputService;
    private final LoginMenu loginMenu;

    @Autowired
    public UserCLIHandler(UserController userController, UserInputService userInputService, LoginMenu loginMenu) {
        this.userController = userController;
        this.userInputService = userInputService;
        this.loginMenu = loginMenu;
    }

    public void handleUserRegistration() {
        System.out.println("Register a New User:");
        String username = userInputService.requestStringInput("Username: ");
        String password = userInputService.requestStringInput("Password: ");
        String email = userInputService.requestStringInput("Email: ");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setEmail(email);

        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("User registration successful!");
        } else {
            System.out.println("Registration failed.");
        }
    }

    public boolean handleUserLogin() {
        System.out.println("\nLogin:");
        String username = userInputService.requestStringInput("Username: ");
        String password = userInputService.requestStringInput("Password: ");

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);

        ResponseEntity<String> response = userController.authenticateUser(userDTO);
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Login successful!");
            loginMenu.setUserName(userDTO.getUsername());
            return true;
        } else {
            System.out.println("Login failed.");
            return false;
        }
    }
}
