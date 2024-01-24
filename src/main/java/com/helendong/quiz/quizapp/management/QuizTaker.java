package com.helendong.quiz.quizapp.management;

import com.helendong.quiz.quizapp.service.QuestionService;
import com.helendong.quiz.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Scanner;

public class QuizTaker {

    private final QuizService quizService;
    private final QuestionService questionService;
    private final Scanner scanner = new Scanner(System.in);

    @Autowired
    public QuizTaker(QuizService quizService, QuestionService questionService) {
        this.quizService = quizService;
        this.questionService = questionService;
    }

    public void takeQuiz() {

    }
}
