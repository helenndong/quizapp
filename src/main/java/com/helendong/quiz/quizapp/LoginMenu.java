package com.helendong.quiz.quizapp;

import java.util.Scanner;

import com.helendong.quiz.quizapp.management.QuizCreator;
import com.helendong.quiz.quizapp.management.QuizEditor;
import com.helendong.quiz.quizapp.management.QuizTaker;
import org.springframework.stereotype.Component;

@Component
public class LoginMenu {

    private String userName;
    private final Scanner scanner = new Scanner(System.in);

    private final QuizCreator quizCreator;
    private final QuizEditor quizEditor;
    private final QuizTaker quizTaker;

    public LoginMenu(QuizCreator quizCreator, QuizEditor quizEditor, QuizTaker quizTaker) {
        this.quizCreator = quizCreator;
        this.quizEditor = quizEditor;
        this.quizTaker = quizTaker;
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
            System.out.println("4. Logout");

            System.out.print("\nEnter your choice: ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    quizCreator.createNewQuiz();
                    break;
                case "2":
                    quizEditor.editQuiz();
                    break;
                case "3":
                    quizTaker.takeQuiz();
                    break;
                case "4":
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

}
