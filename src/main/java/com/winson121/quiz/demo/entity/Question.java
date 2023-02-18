package com.winson121.quiz.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Question {
    private String question;

    @JsonProperty("essay_answer")
    private List<String> essayAnswer;

    @JsonProperty("mcq_choices")
    private List<String> mcqChoices;

    @JsonProperty("mcq_answer")
    private int mcqAnswer;

    public Question() {}

    public Question(String question) {
        this.question = question;
    }

    public Question(String question, List<String> essayAnswer, List<String> mcqChoices, int mcqAnswer) {
        this.question = question;
        this.essayAnswer = essayAnswer;
        this.mcqChoices = mcqChoices;
        this.mcqAnswer = mcqAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getEssayAnswer() {
        return essayAnswer;
    }

    public List<String> getMcqChoices() {
        return mcqChoices;
    }

    public int getMcqAnswer() {
        return mcqAnswer;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public void setEssayAnswer(List<String> essayAnswer) {
        this.essayAnswer = essayAnswer;
    }

    public void setMcqChoices(List<String> mcqChoices) {
        this.mcqChoices = mcqChoices;
    }

    public void setMcqAnswer(int mcqAnswer) {
        this.mcqAnswer = mcqAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "question='" + question + '\'' +
                ", essayAnswer=" + essayAnswer +
                ", mcqChoices=" + mcqChoices +
                ", mcqAnswer=" + mcqAnswer +
                '}';
    }
}
