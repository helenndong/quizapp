package com.helendong.quiz.quizapp.controller;

import com.helendong.quiz.quizapp.dto.QuizDTO;
import com.helendong.quiz.quizapp.model.Quiz;
import com.helendong.quiz.quizapp.service.QuizService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/quiz")

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

    @PutMapping("/{id}")
    public ResponseEntity<QuizDTO> updateQuiz(@PathVariable Long id, @RequestBody QuizDTO quizDTO) {
        Quiz updatedQuiz = convertToEntity(quizDTO);
        try {
            Quiz savedQuiz = quizService.updateQuiz(id, updatedQuiz);
            QuizDTO updatedQuizDTO = convertToDto(savedQuiz);
            return ResponseEntity.ok(updatedQuizDTO);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<QuizDTO> getQuizById(@PathVariable Long id) {
        Optional<Quiz> quiz = quizService.getQuizById(id);

        if (quiz.isPresent()) {
            QuizDTO quizDTO = convertToDto(quiz.get());
            return ResponseEntity.ok(quizDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping
    public ResponseEntity<List<QuizDTO>> getAllQuizzes() {
        List<Quiz> allQuizzes = quizService.getAllQuizzes();
        List<QuizDTO> quizDTOs = new ArrayList<>();

        if (!allQuizzes.isEmpty()) {
            for (Quiz quiz : allQuizzes) {
                quizDTOs.add(convertToDto(quiz));
            }
            return ResponseEntity.ok(quizDTOs);
        } else {
            return ResponseEntity.ok(Collections.emptyList());
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuiz(@PathVariable Long id) {
        try {
            quizService.deleteQuiz(id);
            return ResponseEntity.ok().build();
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
