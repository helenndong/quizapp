package com.helendong.quiz.quizapp.dto;

public class QuestionDTO {

    private Long id;
    private String text;
    private String answer;

    public QuestionDTO() {
    }

    public QuestionDTO(Long id, String text, String answer) {
        this.id = id;
        this.text = text;
        this.answer = answer;
    }

    // Getters and Setters
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
