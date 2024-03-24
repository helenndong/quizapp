package com.helendong.quiz.quizapp.utility;

import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class UserInputService {

    private final Scanner scanner = new Scanner(System.in);

    public String requestStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public boolean confirm(String prompt) {
        System.out.print(prompt + " (yes/no): ");
        String response = scanner.nextLine().trim();
        return "yes".equalsIgnoreCase(response);
    }
}
