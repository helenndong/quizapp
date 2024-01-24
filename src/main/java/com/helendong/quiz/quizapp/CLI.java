package com.helendong.quiz.quizapp;

import com.helendong.quiz.quizapp.controller.UserController;
import com.helendong.quiz.quizapp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class CLI implements CommandLineRunner {

    private final UserController userController;
    private final Scanner scanner = new Scanner(System.in);

    private final LoginMenu loginMenu;

    @Autowired
    public CLI(UserController userController, LoginMenu loginMenu) {
        this.userController = userController;
        this.loginMenu = loginMenu;
    }

    @Override
    public void run(String... args) throws Exception {
        startCommandLineLoop();
    }

    public void startCommandLineLoop() {
        System.out.println("Welcome to Quiz Generator!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Quit");

            System.out.print("\nEnter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    registerUser();
                    break;
                case "2":
                    if (loginUser()) {
                        loginMenu.loggedInMenu();
                    }
                    break;
                case "3":
                    System.out.println("Goodbye!");
                    System.exit(0);
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void registerUser() {
        System.out.println("Register a New User:");

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        System.out.print("Email: ");
        String email = scanner.nextLine().trim();

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);
        userDTO.setEmail(email);

        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("User registration successful!");
        } else {
            System.out.println(response.getBody().getErrorMessage());
        }
    }

    private boolean loginUser() {
        System.out.println("\nLogin:");

        System.out.print("Username: ");
        String username = scanner.nextLine().trim();

        System.out.print("Password: ");
        String password = scanner.nextLine().trim();

        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setPassword(password);

        ResponseEntity<String> response = userController.authenticateUser(userDTO);
        if (response.getStatusCode().is2xxSuccessful()) {
            System.out.println("Login successful!");
            loginMenu.setUserName(userDTO.getUsername());
            return true;
        } else {
            System.out.println(response.getBody());
            return false;
        }
    }

}
