package com.winson121.quiz.demo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

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

    @FXML
    protected void onRestartButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();

        // close the result stage
        Stage thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        thisStage.close();

        // load the start stage
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));
        try {
            Scene scene = new Scene(fxmlLoader.load());
            stage = new Stage();
            stage.setScene(scene);
            stage.initStyle(StageStyle.TRANSPARENT);
            scene.setFill(Color.TRANSPARENT);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
