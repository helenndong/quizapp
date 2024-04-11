package com.helendong.quiz.quizapp.service;

import com.helendong.quiz.quizapp.model.Question;
import com.helendong.quiz.quizapp.repository.QuestionRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Autowired
    public QuestionService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    @Transactional
    public Question createQuestion(Question newQuestion) {
        validateQuestion(newQuestion);
        Question createdQuestion = questionRepository.save(newQuestion);
        return createdQuestion;
    }

    public Optional<Question> getQuestionById(Long questionId) {
        return questionRepository.findById(questionId);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }
    @Transactional
    public Question updateQuestion(Long questionId, Question updatedQuestion) {
        Optional<Question> existingQuestionOptional = questionRepository.findById(questionId);
        if (existingQuestionOptional.isPresent()) {
            Question existingQuestion = existingQuestionOptional.get();
            validateQuestion(updatedQuestion);
            existingQuestion.setText(updatedQuestion.getText());
            existingQuestion.setAnswer(updatedQuestion.getAnswer());
            return questionRepository.save(existingQuestion);
        } else {
            throw new ValidationException("Question not found with ID: " + questionId);
        }
    }

    @Transactional
    public void deleteQuestion(Long questionId) {
        if (!questionRepository.existsById(questionId)) {
            System.out.println("Question with ID " + questionId + " not found");
            return;
        }
        questionRepository.deleteById(questionId);
    }

    private void validateQuestion(Question newQuestion) {

            String text = newQuestion.getText();
            if (text == null || text.isEmpty()) {
                throw new ValidationException("Text cannot be empty");
            }

            String answer = newQuestion.getAnswer();
            if (answer == null || answer.isEmpty()) {
                throw new ValidationException("Description cannot be empty");
            }
        }

}
