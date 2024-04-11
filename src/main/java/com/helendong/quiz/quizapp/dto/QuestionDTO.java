package com.helendong.quiz.quizapp.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class QuestionDTO {

    private Long id;
    @NotBlank(message = "Question text cannot be blank")
    private String text;
    @NotBlank(message = "Answer cannot be blank")
    private String answer;

    @NotNull(message = "Quiz ID cannot be null")
    private Long quizId;

    public QuestionDTO() {
    }

    public QuestionDTO(Long id, String text, String answer, Long quizId) {
        this.id = id;
        this.text = text;
        this.answer = answer;
        this.quizId = quizId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }
}
