package com.winson121.quiz.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Question {

    @JsonProperty("question")
    private String question;

    @JsonProperty("answers")
    private List<String> answers;

    @JsonProperty("mcq_answer")
    private String mcqAnswer;

    @JsonProperty("question_type")
    private String type;

    public Question() {}

    public Question(String question) {
        this.question = question;
    }

    public Question(String question, List<String> answers, String mcqAnswer, String type) {
        this.question = question;
        this.answers = answers;
        this.mcqAnswer = mcqAnswer;
        this.type = type;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getMcqAnswer() {
        return mcqAnswer;
    }

    public void setMcqAnswer(String mcqAnswer) {
        this.mcqAnswer = mcqAnswer;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                ", mcqAnswer=" + mcqAnswer +
                ", type='" + type + '\'' +
                '}';
    }
}
