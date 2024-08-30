package com.example.test2;

import java.util.List;

public class TravelQuestion {
    private String question;
    private List<String> answers;

    public TravelQuestion(String question, List<String> answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getAnswers() {
        return answers;
    }
}
