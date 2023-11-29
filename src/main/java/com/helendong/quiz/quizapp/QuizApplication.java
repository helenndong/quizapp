package com.helendong.quiz.quizapp;

import com.helendong.quiz.quizapp.controller.UserController;
import com.helendong.quiz.quizapp.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.ResponseEntity;

import java.util.Scanner;

@SpringBootApplication
public class QuizApplication {

	private static UserController userController;

	@Autowired
	public QuizApplication(UserController userController) {
		this.userController = userController;
	}


	private static void registerUser() {
		System.out.println("Register a New User:");

		Scanner scanner = new Scanner(System.in);

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
			System.out.println("User registered successfully!");
		} else {
			System.out.println("User registration failed.");
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(QuizApplication.class, args);

		Scanner scanner = new Scanner(System.in);

		System.out.println("Welcome to Quiz Generator!");
		System.out.println("Enter 'q' to quit.");

		while (true) {
			System.out.println("\nMenu:");
			System.out.println("1. Register");
			System.out.println("2. Login");
			System.out.println("3. Quit");

			System.out.print("Enter your choice: ");
			String choice = scanner.nextLine().trim();

			switch (choice) {
				case "1":
					registerUser();
					break;
				case "2":
//					loginUser();
					break;
				case "3":
				case "q":
					System.out.println("Goodbye!");
					scanner.close();
					System.exit(0);
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}
	}

}
