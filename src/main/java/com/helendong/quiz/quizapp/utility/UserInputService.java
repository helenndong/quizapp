
package com.helendong.quiz.quizapp.utility;

import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class UserInputService {

    private final Scanner scanner = new Scanner(System.in);

    public String requestStringInput(String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (input.isBlank()) {
                System.out.println("Input cannot be blank. Please try again or type '0' to return to the main menu.");
            } else if ("0".equals(input)) {
                return "";
            } else {
                break;
            }
        }
        return input;
    }


    public boolean confirm(String prompt) {
        String response;
        do {
            System.out.print(prompt + " (yes/no): ");
            response = scanner.nextLine().trim();
            if (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no")) {
                System.out.println("Please answer 'yes' or 'no'.");
            }
        } while (!response.equalsIgnoreCase("yes") && !response.equalsIgnoreCase("no"));
        return "yes".equalsIgnoreCase(response);
    }
}
