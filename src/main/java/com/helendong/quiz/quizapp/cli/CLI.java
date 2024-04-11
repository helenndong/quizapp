package com.helendong.quiz.quizapp.cli;
import com.helendong.quiz.quizapp.LoginMenu;
import com.helendong.quiz.quizapp.utility.UserInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CLI implements CommandLineRunner {

    private final UserInputService userInputService;
    private final LoginMenu loginMenu;
    private final UserCLIHandler userCLIHandler;

    @Autowired
    public CLI(LoginMenu loginMenu, UserInputService userInputService, UserCLIHandler userCLIHandler) {
        this.userInputService = userInputService;
        this.loginMenu = loginMenu;
        this.userCLIHandler = userCLIHandler;
    }

    @Override
    public void run(String... args) {
        startCommandLineLoop();
    }

    public void startCommandLineLoop() {
        System.out.println("\nWelcome to Qurious!");

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Quit");

            String choice = userInputService.requestStringInput("\nEnter your choice: ");

            switch (choice) {
                case "1" -> userCLIHandler.handleUserRegistration();
                case "2" -> {
                    if (userCLIHandler.handleUserLogin()) {
                        loginMenu.loggedInMenu();
                    }
                }
                case "3" -> {
                    System.out.println("Goodbye!");
                    System.exit(0);
                    return;
                }
                default -> System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}