package com.winson121.quiz.demo;

import com.winson121.quiz.demo.entity.Question;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.*;

public class ReviewController {
    @FXML
    public AnchorPane anchorpane;

    @FXML
    public Button next;

    private Stack<String> nodeClassStack;

    private int counter;

    @FXML
    private void initialize () {
        counter = 0;
        nodeClassStack = new Stack<>();
        loadQuestions();
    }

    private void loadQuestions() {


        Question q = HelloController.questions.get(counter);

        if (q.getType().equals("MCQ")) {
            checkMCQ(q);
        } else if (q.getType().equals("FILLIN")) {
            checkFillin(q);
        }
    }

    private void checkMCQ(Question q) {
        while (!nodeClassStack.isEmpty()) {
            anchorpane.getChildren().remove(anchorpane.lookup("."+nodeClassStack.pop()));
        }
        TilePane tilepane1 = new TilePane();

        String tilepaneStyleClass = "tilepane1";

        tilepane1.setLayoutX(43.0);
        tilepane1.setLayoutY(14.0);
        tilepane1.prefHeight(142.0);
        tilepane1.prefWidth(494.0);
        tilepane1.setMaxWidth(1000.0);
        tilepane1.setOrientation(Orientation.HORIZONTAL);
        tilepane1.setTileAlignment(Pos.TOP_LEFT);
        tilepane1.getStyleClass().add(tilepaneStyleClass);
        Label question = new Label();
        question.setWrapText(true);
        question.setText(counter+1 + ". " + q.getQuestion());

        tilepane1.getChildren().add(question);

        anchorpane.getChildren().add(tilepane1);
        nodeClassStack.push(tilepaneStyleClass);

        tilepaneStyleClass = "tilepane2";

        TilePane tilepane2 = new TilePane();
        tilepane2.getStyleClass().add(tilepaneStyleClass);
        tilepane2.setLayoutX(43.0);
        tilepane2.setLayoutY(213.0);
        tilepane2.setOrientation(Orientation.VERTICAL);
        tilepane2.setPrefHeight(165.0);
        tilepane2.setPrefWidth(750.0);
        tilepane2.setTileAlignment(Pos.TOP_LEFT);
        tilepane2.setVgap(5.0);

        List<String> savedAnswerList = QuizController.savedAnswer.get(counter);
        for (String s: q.getAnswers()) {
            RadioButton rad = new RadioButton(s);
            for (String answer: savedAnswerList){
                if (q.getMcqAnswer().equals(s)) {
                    rad.setSelected(true);
                    Background background = new Background(new BackgroundFill(Paint.valueOf("#99ff99"), null, null));
                    rad.setBackground(background);
                } else if (answer.equals(s)) {
                    rad.setSelected(true);
                }

            }
            tilepane2.getChildren().add(rad);

        }
        anchorpane.getChildren().add(tilepane2);
        nodeClassStack.push(tilepaneStyleClass);
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

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

    private void checkFillin(Question q) {
        while (!nodeClassStack.isEmpty()) {
            anchorpane.getChildren().remove(anchorpane.lookup("."+nodeClassStack.pop()));
        }

        TextFlow textflow = new TextFlow();

        textflow.setLayoutX(43.0);
        textflow.setLayoutY(14.0);
        textflow.prefHeight(142.0);
        textflow.prefWidth(1000.0);
        textflow.setMaxWidth(1000.0);

        String textflowStyleClass = "textflow1";
        textflow.getStyleClass().add(textflowStyleClass);

        processFillinQuestionReview(q, textflow);

        anchorpane.getChildren().add(textflow);
        nodeClassStack.push(textflowStyleClass);

        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

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

    private void processFillinQuestionReview(Question q, TextFlow tf) {
        String qStr = q.getQuestion().strip();
        String[] qStrs = qStr.split("___", 0);
        int numBlanks = 0;
        for(String s: qStrs) {
            numBlanks += 1;
        }

        if (!qStr.endsWith("___")) {
            numBlanks--;
        }

        if (numBlanks != q.getAnswers().size()) {
            return;
        }

        Text questionNum = new Text();
        questionNum.setText(counter+1 + ". ");
        tf.getChildren().add(questionNum);

        List<String> savedAnswerStrings = QuizController.savedAnswer.get(counter);
        List<String> correctAnswers = q.getAnswers();
        int answerIndex = 0;
        for (String s: qStrs) {
            Text partQuestion = new Text();
            partQuestion.setText(s);
            tf.getChildren().add(partQuestion);
            if (numBlanks > 0) {
                List<Text> answerTexts = new ArrayList<>();
                if (savedAnswerStrings.get(answerIndex).strip().toLowerCase().equals(correctAnswers.get(answerIndex).strip().toLowerCase())) {
                    Text answerText = new Text(" ");
                    answerText.setText(savedAnswerStrings.get(answerIndex));
                    answerText.setFill(Color.GREEN);
                    answerTexts.add(answerText);
                } else if (!savedAnswerStrings.get(answerIndex).strip().toLowerCase().equals(correctAnswers.get(answerIndex).strip().toLowerCase())) {
                    Text wrongAnswerText = new Text();
                    String answerString = savedAnswerStrings.get(answerIndex);
                    if (answerString.isEmpty()) {
                        answerString = " ";
                    }
                    wrongAnswerText.setText(answerString);
                    wrongAnswerText.setFill(Color.LIGHTSALMON);;
                    wrongAnswerText.setStrikethrough(true);
                    answerTexts.add(wrongAnswerText);

                    Text delimText = new Text();
                    delimText.setText("/");
                    answerTexts.add(delimText);

                    Text correctAnswerText = new Text();
                    correctAnswerText.setText((correctAnswers.get(answerIndex)));
                    correctAnswerText.setFill((Color.GREEN));
                    answerTexts.add(correctAnswerText);
                }
                for (Text answer: answerTexts) {
                    tf.getChildren().add(answer);
                }
                numBlanks--;
                answerIndex++;
            }
        }

    }
}
