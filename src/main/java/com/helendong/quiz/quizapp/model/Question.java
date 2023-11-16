package com.helendong.quiz.quizapp.model;

import jakarta.persistence.*;

@Entity

public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;
    private String answer;

    @ManyToOne
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    public Question() {

    }
    public Question(String text, String answer, Quiz quiz) {
        this.text = text;
        this.answer = answer;
        this.quiz = quiz;
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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
}
