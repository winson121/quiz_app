package com.winson121.quiz.demo;

import com.winson121.quiz.demo.entity.Question;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.*;


public class QuizController {

//    private TilePane tilepane;

//    private Label question;
    @FXML
    public AnchorPane anchorpane;

    @FXML
    public Button next;

//    @FXML
//    public TilePane tilepane;

    int counter;
    static int correct;
    static int wrong;

    private Stack<String> nodeClassStacks;

    static Map<Integer, List<String>> savedAnswer;

    private List<Question> questions = HelloController.questions;

    private List<Integer> answers = new ArrayList<>();
    @FXML
    private void initialize () {
        counter = 0;
        wrong = 0;
        correct = 0;
        savedAnswer = new HashMap<>();
        nodeClassStacks = new Stack<>();
        loadQuestions();
    }

    private void loadQuestions() {


        Question q = HelloController.questions.get(counter);

        if (q.getType().equals("MCQ")) {
            addMCQ(q);
        } else if (q.getType().equals("FILLIN")) {
            addFillin(q);
        }
    }

    private boolean checkAnswer(String answer) {
        boolean isTrue = false;
        Question q = HelloController.questions.get(counter);
        if (q.getType().equals("MCQ")) {
            String correctAnswer = q.getMcqAnswer();
            if (answer.equals(correctAnswer)) {
                isTrue = true;
            }
        } else if (q.getType().equals("FILLIN")) {

        }

        return isTrue;
    }

    private boolean checkAnswerV2(Pane pane) {
        boolean isTrue = true;
        Question q = HelloController.questions.get(counter);
        List answerList = new ArrayList();
        if (q.getType().equals("MCQ")) {
            RadioButton selectedRadio = (RadioButton) pane.lookup(".radio-button");
            ToggleGroup toggleGroup = selectedRadio.getToggleGroup();
            selectedRadio = (RadioButton) toggleGroup.getSelectedToggle();
            String answer = selectedRadio.getText();

            answerList.add(answer);
            savedAnswer.put(counter, answerList);

            String correctAnswer = q.getMcqAnswer();
            if (!answer.equals(correctAnswer)) {
                isTrue = false;
            }
        } else if (q.getType().equals("FILLIN")) {
            List<TextField> answerFields = new ArrayList<>();
            for( Node node: pane.getChildren()) {

                if( node instanceof TextField) {
                    answerFields.add((TextField) node);
                }

            }

            for (int i=0; i < answerFields.size(); i++) {
                answerList.add(answerFields.get(i).getText().strip());
                String strippedLowercaseAns = answerFields.get(i).getText().strip().toLowerCase();
                String strippedLowercaseCorrectAns = q.getAnswers().get(i).strip().toLowerCase();
                if (!strippedLowercaseAns.equals(strippedLowercaseCorrectAns)) {
                    isTrue = false;
                }
            }
            savedAnswer.put(counter, answerList);
        }

        return isTrue;
    }

    private void addFillin(Question q) {
        while (!nodeClassStacks.isEmpty()) {
            anchorpane.getChildren().remove(anchorpane.lookup("."+nodeClassStacks.pop()));
        }
//        anchorpane.getChildren().remove(anchorpane.lookup(".textflow1"));
//        anchorpane.getChildren().remove(anchorpane.lookup(".tilepane1"));
//        anchorpane.getChildren().remove(anchorpane.lookup(".tilepane2"));
        TextFlow textflow = new TextFlow();

        textflow.setLayoutX(43.0);
        textflow.setLayoutY(14.0);
        textflow.prefHeight(142.0);
        textflow.prefWidth(1000.0);
        textflow.setMaxWidth(1000.0);
//        textflow.setPrefWrapLength(1000.0);
//        textflow.setOrientation(Orientation.HORIZONTAL);

//        textflow.setVgap(3);
//        textflow.setHgap(1);
        String textflowStyleClass = "textflow1";
        textflow.getStyleClass().add(textflowStyleClass);

        processFillinQuestion(q, textflow);

        anchorpane.getChildren().add(textflow);
        nodeClassStacks.push(textflowStyleClass);

        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (checkAnswerV2(textflow)) {
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

    private void processFillinQuestion(Question q, TextFlow tf) {
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
        for (String s: qStrs) {
            Text partQuestion = new Text();
            partQuestion.setText(s);
//            partQuestion.setWrapText(true);
            tf.getChildren().add(partQuestion);
            if (numBlanks > 0) {
                TextField answerField = new TextField();
                answerField.setPadding(new Insets(1,1,1,1));
                tf.getChildren().add(answerField);
                numBlanks--;
            }
        }

    }
    private void addMCQ(Question q) {
        while (!nodeClassStacks.isEmpty()) {
            anchorpane.getChildren().remove(anchorpane.lookup("."+nodeClassStacks.pop()));
        }
//        anchorpane.getChildren().remove(anchorpane.lookup(".tilepane1"));
//        anchorpane.getChildren().remove(anchorpane.lookup(".tilepane2"));
//        anchorpane.getChildren().remove(anchorpane.lookup((".textflow1")));
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
        nodeClassStacks.push(tilepaneStyleClass);

        ToggleGroup toggleGroup = new ToggleGroup();

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

        // shuffle the multiple choices
        Collections.shuffle(q.getAnswers());

        for (String s: q.getAnswers()) {
            RadioButton rad = new RadioButton(s);
            tilepane2.getChildren().add(rad);

            // add radio button to toggle group
            rad.setToggleGroup(toggleGroup);

        }
        anchorpane.getChildren().add(tilepane2);
        nodeClassStacks.push(tilepaneStyleClass);
        next.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (checkAnswerV2(tilepane2)) {
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
}
