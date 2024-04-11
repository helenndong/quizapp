
package com.helendong.quiz.quizapp.cli;
import com.helendong.quiz.quizapp.model.Quiz;
import com.helendong.quiz.quizapp.service.QuizService;
import com.helendong.quiz.quizapp.utility.UserInputService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CLIQuizCreator {
    private final QuizService quizService;
    private final UserInputService userInputService;

    @Autowired
    public CLIQuizCreator(QuizService quizService, UserInputService userInputService) {
        this.quizService = quizService;
        this.userInputService = userInputService;
    }

    public void createNewQuiz() {
        System.out.println("Creating a New Quiz");

        String title = userInputService.requestStringInput("Enter Quiz Title: ");
        if (title.isEmpty()) {
            System.out.println("Quiz title cannot be empty.");
            return;
        }

        String description = userInputService.requestStringInput("Enter Quiz Description: ");
        if (description.isEmpty()) {
            System.out.println("Quiz description cannot be empty.");
            return;
        }

        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(title);
        newQuiz.setDescription(description);

        try {
            Quiz createdQuiz = quizService.createQuiz(newQuiz);
            System.out.println("New quiz created successfully with title: " + createdQuiz.getTitle());
        } catch (Exception e) {
            System.out.println("Failed to create quiz: " + e.getMessage());
        }
    }
}
