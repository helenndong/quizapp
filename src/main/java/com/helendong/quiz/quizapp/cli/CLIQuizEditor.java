package com.helendong.quiz.quizapp.cli;

import com.helendong.quiz.quizapp.model.Quiz;
import com.helendong.quiz.quizapp.service.QuizService;
import com.helendong.quiz.quizapp.utility.UserInputService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class CLIQuizEditor {

    private final QuizService quizService;
    private final CLIQuestionEditor CLIQuestionEditor;
    private final UserInputService userInputService;

    @Autowired
    public CLIQuizEditor(QuizService quizService, CLIQuestionEditor CLIQuestionEditor, UserInputService userInputService) {
        this.quizService = quizService;
        this.CLIQuestionEditor = CLIQuestionEditor;
        this.userInputService = userInputService;
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

        int quizIndex = -1;
        try{
            quizIndex = Integer.parseInt(userInputService.requestStringInput("\nEnter the quiz ID you want to edit: ")) - 1;
        } catch (NumberFormatException e) {
        }

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

        int choice = -1;
        try {
            choice = Integer.parseInt(userInputService.requestStringInput("\nChoose an option: "));
        } catch (NumberFormatException e) {
        }


        switch (choice) {
            case 1 -> editQuizDetails(selectedQuiz);
            case 2 -> CLIQuestionEditor.addQuestionsToQuiz(selectedQuiz);
            case 3 -> CLIQuestionEditor.editExistingQuestions(selectedQuiz.getId());
            case 4 -> CLIQuestionEditor.removeQuestionFromQuiz(selectedQuiz.getId());
            case 5 -> removeQuiz(selectedQuiz);
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    @Transactional
    private void editQuizDetails(Quiz quiz) {
        System.out.println("Editing Quiz Details");
        String newTitle = userInputService.requestStringInput("Enter new title: ");
        if (!newTitle.isEmpty()) {
            quiz.setTitle(newTitle);
        } else {
            return;
        }

        String newDescription = userInputService.requestStringInput("Enter new description: ");
        if (!newDescription.isEmpty()) {
            quiz.setDescription(newDescription);
        }

        quizService.updateQuiz(quiz.getId(), quiz);
        System.out.println("Quiz details updated successfully.");
    }

    private void removeQuiz(Quiz quiz) {
        boolean confirmation = userInputService.confirm("Are you sure you want to remove the quiz: " + quiz.getTitle() + "?");
        if (confirmation) {
            quizService.deleteQuiz(quiz.getId());
            System.out.println("Quiz deleted successfully.");
        } else {
            System.out.println("Quiz deletion canceled.");
        }
    }

}
