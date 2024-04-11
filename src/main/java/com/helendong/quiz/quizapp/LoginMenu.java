package com.helendong.quiz.quizapp;

import java.util.Scanner;

import com.helendong.quiz.quizapp.cli.CLIQuizCreator;
import com.helendong.quiz.quizapp.cli.CLIQuizEditor;
import com.helendong.quiz.quizapp.cli.CLIQuizTaker;
import org.springframework.stereotype.Component;

@Component
public class LoginMenu {

    private String userName;
    private final Scanner scanner = new Scanner(System.in);

    private final CLIQuizCreator CLIQuizCreator;
    private final CLIQuizEditor CLIQuizEditor;
    private final CLIQuizTaker CLIQuizTaker;

    public LoginMenu(CLIQuizCreator CLIQuizCreator, CLIQuizEditor CLIQuizEditor, CLIQuizTaker CLIQuizTaker) {
        this.CLIQuizCreator = CLIQuizCreator;
        this.CLIQuizEditor = CLIQuizEditor;
        this.CLIQuizTaker = CLIQuizTaker;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }
    public void loggedInMenu() {
        while (true) {
            System.out.println("\nWelcome Back, " + userName+ "!");
            System.out.println("1. Start a New Quiz");
            System.out.println("2. Edit a Quiz");
            System.out.println("3. Take a Quiz");
            System.out.println("4. Return to Main Menu");

            System.out.print("\nEnter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    CLIQuizCreator.createNewQuiz();
                    break;
                case "2":
                    CLIQuizEditor.editQuiz();
                    break;
                case "3":
                    CLIQuizTaker.takeQuiz();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
