package com.helendong.quiz.quizapp.dto;

import jakarta.validation.constraints.NotBlank;

public class QuestionDTO {

    private Long id;
    @NotBlank(message = "Question text cannot be blank")
    private String text;
    @NotBlank(message = "Answer cannot be blank")
    private String answer;

    public QuestionDTO() {
    }

    public QuestionDTO(Long id, String text, String answer) {
        this.id = id;
        this.text = text;
        this.answer = answer;
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

}
