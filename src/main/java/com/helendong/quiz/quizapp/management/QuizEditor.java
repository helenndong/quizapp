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
public class QuizEditor {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public QuizEditor(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    public void editQuiz() {
        List<Quiz> quizzes = quizService.getAllQuizzes();
        if (quizzes.isEmpty()) {
            System.out.println("There are no quizzes available to edit.");
            return;
        }

        System.out.println("\nAvailable Quizzes:");
        for (int i = 0; i < quizzes.size(); i++) {
            System.out.println((i + 1) + ": " + quizzes.get(i).getTitle());
        }

        System.out.print("\nEnter the quiz ID you want to edit: ");
        int quizIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (quizIndex < 0 || quizIndex >= quizzes.size()) {
            System.out.println("Invalid quiz selection.");
            return;
        }

        Quiz selectedQuiz = quizzes.get(quizIndex);

        System.out.println("\nEditing Quiz: " + selectedQuiz.getTitle());
        System.out.println("1: Edit quiz details");
        System.out.println("2: Add a new question");
        System.out.println("3: Edit existing questions");
        System.out.println("4: Remove a question");
        System.out.println("5: Delete quiz");
        System.out.print("\nChoose an option: ");
        int choice = scanner.nextInt();
        scanner.nextLine();

        switch (choice) {
            case 1:
                editQuizDetails(selectedQuiz);
                break;
            case 2:
                addNewQuestionToQuiz(selectedQuiz);
                break;
            case 3:
                editExistingQuestions(selectedQuiz.getId());
                break;
            case 4:
                removeQuestionFromQuiz(selectedQuiz.getId());
                break;
            case 5:
                removeQuiz(selectedQuiz);
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }

    private String getInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    private void updateQuizDetails(Quiz quiz) {
        String newTitle = getInput("Enter new title (leave blank to keep current): ");
        if (!newTitle.isEmpty()) {
            quiz.setTitle(newTitle);
        }

        String newDescription = getInput("Enter new description (leave blank to keep current): ");
        if (!newDescription.isEmpty()) {
            quiz.setDescription(newDescription);
        }
    }

    private void editQuizDetails(Quiz quiz) {
        System.out.println("Editing Quiz Details");
        updateQuizDetails(quiz);
        quizService.updateQuiz(quiz.getId(), quiz);
        System.out.println("Quiz details updated successfully.");
    }



    private void addNewQuestionToQuiz(Quiz quiz) {
        System.out.println("Adding a New Question to the Quiz");
        Question newQuestion = new Question();

        newQuestion.setText(getInput("Enter Question Text: "));
        newQuestion.setAnswer(getInput("Enter Answer: "));
        newQuestion.setQuiz(quiz);

        questionService.createQuestion(newQuestion);
        System.out.println("Question added successfully.");
    }
    @Transactional
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

        String input = getInput("Enter the number of the question you want to edit: ");
        int questionIndex;
        try {
            questionIndex = Integer.parseInt(input) - 1;
        } catch (NumberFormatException e) {
            System.out.println("Invalid input. Please enter a number.");
            return;
        }

        if (questionIndex < 0 || questionIndex >= questions.size()) {
            System.out.println("Invalid question selection.");
            return;
        }

        Question question = questions.get(questionIndex);

        String newText = getInput("Enter new question text (leave blank to keep current): ");
        if (!newText.isEmpty()) {
            question.setText(newText);
        }

        String newAnswer = getInput("Enter new answer (leave blank to keep current): ");
        if (!newAnswer.isEmpty()) {
            question.setAnswer(newAnswer);
        }

        questionService.updateQuestion(question.getId(), question);
        System.out.println("Question updated successfully.");
    }

    @Transactional
    private void removeQuestionFromQuiz(Long quizId) {
        Quiz quiz = quizService.getQuizWithQuestions(quizId);

        List<Question> questions = new ArrayList<>(quiz.getQuestions());
        if (questions.isEmpty()) {
            System.out.println("No questions available to remove.");
            return;
        }

        System.out.println("Select a question to remove:");
        for (int i = 0; i < questions.size(); i++) {
            System.out.println((i + 1) + ": " + questions.get(i).getText());
        }

        int questionIndex = scanner.nextInt() - 1;
        scanner.nextLine();

        if (questionIndex < 0 || questionIndex >= questions.size()) {
            System.out.println("Invalid question selection.");
            return;
        }

        Question question = questions.get(questionIndex);
        questionService.deleteQuestion(question.getId());
        System.out.println("Question removed successfully.");
    }

    private void removeQuiz(Quiz quiz) {
        if (quiz == null) {
            System.out.println("No quiz selected to remove.");
            return;
        }

        System.out.println("Are you sure you want to remove the quiz: " + quiz.getTitle() + "? (yes/no)");
        String confirmation = scanner.nextLine().trim();

        if ("yes".equalsIgnoreCase(confirmation)) {
            quizService.deleteQuiz(quiz.getId());
            System.out.println("Quiz deleted successfully.");
        } else {
            System.out.println("Quiz deletion canceled.");
        }
    }


}
