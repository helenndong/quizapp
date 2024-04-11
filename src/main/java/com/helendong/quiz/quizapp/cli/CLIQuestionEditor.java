package com.helendong.quiz.quizapp.cli;

import com.helendong.quiz.quizapp.model.Question;
import com.helendong.quiz.quizapp.model.Quiz;
import com.helendong.quiz.quizapp.service.QuestionService;
import com.helendong.quiz.quizapp.service.QuizService;
import com.helendong.quiz.quizapp.utility.UserInputService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class CLIQuestionEditor {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final UserInputService userInputService;

    @Autowired
    public CLIQuestionEditor(QuizService quizService, QuestionService questionService, UserInputService userInputService) {
        this.quizService = quizService;
        this.questionService = questionService;
        this.userInputService = userInputService;
    }

    public void addQuestionsToQuiz(Quiz createdQuiz) {
        boolean addingQuestions = true;
        while (addingQuestions) {
            String questionText = userInputService.requestStringInput("Enter Question Text: ");
            if (questionText.isEmpty()) {
                return;
            }
            String answer = userInputService.requestStringInput("Enter Answer: ");
            if (answer.isEmpty()) {
                return;
            }

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
        System.out.println("Question added successfully.");
    }


    public void editExistingQuestions(Long quizId) {
        Quiz quiz = quizService.getQuizWithQuestions(quizId);
        if (quiz == null) {
            System.out.println("Quiz not found.");
            return;
        }

        List<Question> questions = new ArrayList<>(quiz.getQuestions());
        if (questions.isEmpty()) {
            System.out.println("No questions available to edit.");
            return;
        }

        System.out.println("Select a question to edit:");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ": " + questions.get(i).getText());
        }

        int questionIndex = -1;
        try {
            questionIndex = Integer.parseInt(userInputService.requestStringInput("Enter the number of the question you want to edit: ")) - 1;
        } catch (NumberFormatException e) {
        }

        if (questionIndex < 0 || questionIndex >= questions.size()) {
            System.out.println("Invalid question selection.");
            return;
        }

        Question question = questions.get(questionIndex);
        String newText = userInputService.requestStringInput("Enter new question text: ");
        if (!newText.isEmpty()) {
            question.setText(newText);
        }

        String newAnswer = userInputService.requestStringInput("Enter new answer: ");
        if (!newAnswer.isEmpty()) {
            question.setAnswer(newAnswer);
        }

        questionService.updateQuestion(question.getId(), question);
        System.out.println("Question updated successfully.");
    }

    public void removeQuestionFromQuiz(Long quizId) {
        Quiz quiz = quizService.getQuizWithQuestions(quizId);
        if (quiz == null || quiz.getQuestions().isEmpty()) {
            System.out.println("No questions available to remove.");
            return;
        }

        System.out.println("Select a question to remove:");
        List<Question> questions = new ArrayList<>(quiz.getQuestions());
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ": " + questions.get(i).getText());
        }

        String questionNumberInput = userInputService.requestStringInput("Enter the number of the question you want to remove: ");

        int questionIndex;
        try {
            questionIndex = Integer.parseInt(questionNumberInput) - 1;
        } catch (NumberFormatException e) {
            return;
        }

        if (questionIndex < 0 || questionIndex >= questions.size()) {
            System.out.println("Invalid question selection.");
            return;
        }

        Question question = questions.get(questionIndex);
        questionService.deleteQuestion(question.getId());
        System.out.println("Question with id: " + question.getId() + " removed successfully.");
    }
}
