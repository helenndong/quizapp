package com.helendong.quiz.quizapp.management;
import com.helendong.quiz.quizapp.model.Question;
import com.helendong.quiz.quizapp.model.Quiz;
import com.helendong.quiz.quizapp.service.QuestionService;
import com.helendong.quiz.quizapp.service.QuizService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.Scanner;

@Component
public class QuizCreator {
    private final QuizService quizService;
    private final QuestionService questionService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public QuizCreator(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    public void createNewQuiz() {
        System.out.println("Creating a New Quiz");

        System.out.print("Enter Quiz Title: ");
        String title = scanner.nextLine().trim();

        System.out.print("Enter Quiz Description: ");
        String description = scanner.nextLine().trim();

        Quiz newQuiz = new Quiz();
        newQuiz.setTitle(title);
        newQuiz.setDescription(description);

        Quiz createdQuiz = quizService.createQuiz(newQuiz);

        if (createdQuiz != null) {
            System.out.println("New quiz created successfully with title: " + createdQuiz.getTitle());
            boolean addingQuestions = true;
            while (addingQuestions) {
                System.out.println("Add a new question to the quiz:");

                System.out.print("Enter Question Text: ");
                String questionText = scanner.nextLine().trim();

                System.out.print("Enter Answer: ");
                String answer = scanner.nextLine().trim();

                Question newQuestion = new Question();
                newQuestion.setText(questionText);
                newQuestion.setAnswer(answer);

                newQuestion.setQuiz(createdQuiz);

                try {
                    questionService.createQuestion(newQuestion);
                    System.out.println("Question added successfully.");
                } catch (ValidationException e) {
                    System.out.println("Failed to add question: " + e.getMessage());
                }

                System.out.print("\nDo you want to add another question? (yes/no): ");
                String response = scanner.nextLine().trim();
                if (!response.equalsIgnoreCase("yes")) {
                    addingQuestions = false;
                }
            }

            System.out.println("Quiz creation complete with questions added.");
        } else {
            System.out.println("Failed to create quiz.");
        }

    }

}