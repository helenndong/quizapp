
package com.helendong.quiz.quizapp.management;
import com.helendong.quiz.quizapp.model.Question;
import com.helendong.quiz.quizapp.model.Quiz;
import com.helendong.quiz.quizapp.service.QuestionService;
import com.helendong.quiz.quizapp.service.QuizService;
import com.helendong.quiz.quizapp.utility.UserInputService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QuizCreator {
    private final QuizService quizService;
    private final QuestionService questionService;
    private final UserInputService userInputService;

    @Autowired
    public QuizCreator(QuizService quizService, QuestionService questionService, UserInputService userInputService) {
        this.quizService = quizService;
        this.questionService = questionService;
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
            addQuestionsToQuiz(createdQuiz);
        } catch (Exception e) {
            System.out.println("Failed to create quiz: " + e.getMessage());
        }
    }

    private void addQuestionsToQuiz(Quiz createdQuiz) {
        boolean addingQuestions = true;
        while (addingQuestions) {
            String questionText = userInputService.requestStringInput("Enter Question Text: ");
            String answer = userInputService.requestStringInput("Enter Answer: ");

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

            addingQuestions = userInputService.confirm("Do you want to add another question?");
        }
        System.out.println("Quiz creation complete with questions added.");
    }
}
