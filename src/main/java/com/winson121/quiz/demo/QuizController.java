package com.winson121.quiz.demo;

import com.winson121.quiz.demo.entity.Question;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class QuizController {

    public TextArea question;

    @FXML
    public Button next;

    @FXML
    public TilePane tilepane;

    int counter = 0;
    static int correct = 0;
    static int wrong = 0;

    private List<Question> questions = HelloController.questions;

    private List<Integer> answers = new ArrayList<>();
    @FXML
    private void initialize () {
        loadQuestions();
    }

    private void loadQuestions() {
        Question q = HelloController.questions.get(counter);
        question.setText(counter+1 + ". " + q.getQuestion());

        ToggleGroup toggleGroup = new ToggleGroup();

        // remove toggles from previous questions
        tilepane.getChildren().clear();
        for (String s: q.getMcqChoices()) {
            RadioButton rad = new RadioButton(s);
            tilepane.getChildren().add(rad);

            // add radio button to toggle group
            rad.setToggleGroup(toggleGroup);

        }

        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                RadioButton selectedAnswer = (RadioButton) toggleGroup.getSelectedToggle();
                String answer = selectedAnswer.getText();
                if (checkAnswer(answer)) {
                    correct++;
                } else {
                    wrong++;
                }

                if (counter == HelloController.questions.size()-1) {
                    // close current quiz stage
                    Stage thisStage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
                    thisStage.close();

                    // load the result stage
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("result.fxml"));
                    try {
                        Scene scene = new Scene(fxmlLoader.load());
                        Stage stage = new Stage();
                        stage.setScene(scene);
                        stage.initStyle(StageStyle.TRANSPARENT);
                        scene.setFill(Color.TRANSPARENT);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else {
                    counter++;
                    loadQuestions();
                }
            }
        });

    }

    private boolean checkAnswer(String answer) {
        Question q = HelloController.questions.get(counter);
        String correctAnswer = q.getMcqChoices().get(q.getMcqAnswer());
        if (answer.equals(correctAnswer)) {
            return true;
        }

        return false;
    }

}
