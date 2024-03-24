package com.helendong.quiz.quizapp.management;
import com.helendong.quiz.quizapp.model.Question;
import com.helendong.quiz.quizapp.model.Quiz;
import com.helendong.quiz.quizapp.service.QuestionService;
import com.helendong.quiz.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
@Component
public class QuizTaker {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public QuizTaker(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    @Transactional
    public void takeQuiz() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        if (quizzes.isEmpty()) {
            System.out.println("No quizzes available.");
            return;
        }

        System.out.println("Available Quizzes:");
        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ": " + quizzes.get(i).getTitle());
        }

        System.out.print("\nChoose a quiz to take by number: ");
        System.out.println();
        int quizIndex;
        try {
            quizIndex = Integer.parseInt(scanner.nextLine()) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        if (quizIndex < 0 || quizIndex >= quizzes.size()) {
            System.out.println("Invalid quiz selection.");
            return;
        }

        Long selectedQuizId = quizzes.get(quizIndex).getId();
        Quiz selectedQuiz = quizService.getQuizWithQuestions(selectedQuizId);

        if (selectedQuiz == null) {
            System.out.println("Quiz not found.");
            return;
        }

        List<Question> questions = new ArrayList<>(selectedQuiz.getQuestions());

        int score = 0;
        for (Question question : questions) {
            System.out.println(question.getText());
            System.out.print("Your answer: ");
            String userAnswer = scanner.nextLine();

            if (userAnswer.equalsIgnoreCase(question.getAnswer())) {
                System.out.println("Correct!\n");
                score++;
            } else {
                System.out.println("Wrong. The correct answer is: " + question.getAnswer() + "\n");
            }
        }

        System.out.println("\nYour score: " + score + " out of " + questions.size());
        double percentage = (double) score / questions.size() * 100;
        if (percentage >= 80) {
            System.out.println("Good Job!");
        }

    }

}
