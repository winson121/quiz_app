package com.winson121.quiz.demo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class ResultController {

    @FXML
    public Label remark, marks, markstext, correcttext;

    @FXML
    public ProgressIndicator correctchart;

    @FXML
    private void initialize() {
        correcttext.setText("Correct Answers: " + String.valueOf(QuizController.correct));

        marks.setText(String.valueOf(QuizController.correct) + "/" + String.valueOf(QuizController.correct + QuizController.wrong));

        float correctf = (float) QuizController.correct/(QuizController.correct + QuizController.wrong);
        correctchart.setProgress(correctf);

        markstext.setText(String.valueOf(correctf * 100) + "% Scored");
    }
}
