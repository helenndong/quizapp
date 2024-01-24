package com.helendong.quiz.quizapp;

import java.util.Scanner;
import org.springframework.stereotype.Component;

@Component
public class Menu {

    private String userName;

    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void loggedInMenu() {
        while (true) {
            System.out.println("\nWelcome Back, " + userName+ "!\n");
            System.out.println("1. Start a New Quiz");
            System.out.println("2. Edit a Quiz");
            System.out.println("3. Take a Quiz");
            System.out.println("4. Logout");

            System.out.print("Enter your choice: ");
            Scanner scanner = new Scanner(System.in);
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    startNewQuiz();
                    break;
                case "2":
                    editQuiz();
                    break;
                case "3":
                    takeQuiz();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private void takeQuiz() {
    }

    private void editQuiz() {
    }

    private void startNewQuiz() {
    }

}
