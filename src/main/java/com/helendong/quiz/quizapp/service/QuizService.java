package com.helendong.quiz.quizapp.service;

import com.helendong.quiz.quizapp.model.Quiz;
import com.helendong.quiz.quizapp.repository.QuizRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class QuizService {

    private QuizRepository quizRepository;

    @Autowired
    public QuizService(QuizRepository quizRepository) {
        this.quizRepository = quizRepository;
    }

    public Quiz createQuiz (Quiz newQuiz) {
        validateQuiz(newQuiz);
        Quiz createdQuiz = quizRepository.save(newQuiz);
        return createdQuiz;
    }

    public Optional<Quiz> getQuizById(Long quizId) {
        return quizRepository.findById(quizId);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Quiz updateQuiz(Long quizId, Quiz updatedQuiz) {
        Optional<Quiz> existingQuizOptional = quizRepository.findById(quizId);

        if (existingQuizOptional.isPresent()) {
            Quiz existingQuiz = existingQuizOptional.get();
            validateQuiz(updatedQuiz);
            existingQuiz.setTitle(updatedQuiz.getTitle());
            existingQuiz.setDescription(updatedQuiz.getDescription());
            return quizRepository.save(existingQuiz);
        } else {
            throw new ValidationException("Quiz not found with ID: " + quizId);
        }
    }

    public void deleteQuiz(Long quizId) {
        quizRepository.deleteById(quizId);
    }


    private void validateQuiz(Quiz newQuiz) {

        String title = newQuiz.getTitle();
        if (title == null || title.isEmpty()) {
            throw new ValidationException("Title cannot be empty");
        }

        String description = newQuiz.getDescription();
        if (description == null || description.isEmpty()) {
            throw new ValidationException("Description cannot be empty");
        }
    }


}
