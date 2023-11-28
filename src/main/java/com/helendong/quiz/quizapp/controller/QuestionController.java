package com.helendong.quiz.quizapp.controller;

import com.helendong.quiz.quizapp.dto.QuestionDTO;
import com.helendong.quiz.quizapp.model.Question;
import com.helendong.quiz.quizapp.service.QuestionService;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/questions")
public class QuestionController {

    private final QuestionService questionService;

    @Autowired
    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @PostMapping
    public ResponseEntity<QuestionDTO> createQuestion(@RequestBody QuestionDTO questionDTO) {
        Question newQuestion = convertToEntity(questionDTO);
        Question createdQuestion = questionService.createQuestion(newQuestion);
        QuestionDTO createdQuestionDTO = convertToDto(createdQuestion);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdQuestionDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id) {
        Optional<Question> question = questionService.getQuestionById(id);
        return question.map(value -> ResponseEntity.ok(convertToDto(value)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<QuestionDTO>> getAllQuestions() {
        List<Question> allQuestions = questionService.getAllQuestions();
        List<QuestionDTO> questionDTOs = new ArrayList<>();
        for (Question question : allQuestions) {
            questionDTOs.add(convertToDto(question));
        }
        return ResponseEntity.ok(questionDTOs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO) {
        Question updatedQuestion = convertToEntity(questionDTO);
        try {
            Question savedQuestion = questionService.updateQuestion(id, updatedQuestion);
            QuestionDTO updatedQuestionDTO = convertToDto(savedQuestion);
            return ResponseEntity.ok(updatedQuestionDTO);
        } catch (ValidationException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        try {
            questionService.deleteQuestion(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private Question convertToEntity(QuestionDTO questionDTO) {
        Question question = new Question();
        question.setId(questionDTO.getId());
        question.setText(questionDTO.getText());
        question.setAnswer(questionDTO.getAnswer());
        return question;
    }


    private QuestionDTO convertToDto(Question question) {
        QuestionDTO questionDTO = new QuestionDTO();
        questionDTO.setId(question.getId());
        questionDTO.setText(question.getText());
        questionDTO.setAnswer(question.getAnswer());
        return questionDTO;
    }

}
