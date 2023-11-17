package com.helendong.quiz.quizapp.controller;

import com.helendong.quiz.quizapp.dto.QuizDTO;
import com.helendong.quiz.quizapp.model.Quiz;
import com.helendong.quiz.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("http://localhost:8080/quiz")

public class QuizController {
    private final QuizService quizService;

    @Autowired
    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @PostMapping
    public ResponseEntity<QuizDTO> createQuiz(@RequestBody QuizDTO quizDTO) {
        Quiz newQuiz = convertToEntity(quizDTO);
        Quiz createdQuiz = quizService.createQuiz(newQuiz);
        QuizDTO createdQuizDTO = convertToDto(createdQuiz);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuizDTO);
    }

    private Quiz convertToEntity(QuizDTO quizDTO) {
        Quiz quiz = new Quiz();
        quiz.setTitle(quizDTO.getTitle());
        quiz.setDescription(quizDTO.getDescription());
        return quiz;
    }

    private QuizDTO convertToDto(Quiz createdQuiz) {
        QuizDTO quizDTO = new QuizDTO();
        quizDTO.setTitle(createdQuiz.getTitle());
        quizDTO.setDescription(createdQuiz.getDescription());
        return quizDTO;
    }

}
