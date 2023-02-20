package com.winson121.quiz.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.winson121.quiz.demo.entity.Question;
import javafx.event.ActionEvent;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class HelloController {
    @FXML
    private Label welcomeText;

    static List<Question> questions = new ArrayList<>();

    private Desktop desktop = Desktop.getDesktop();

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("This is going to be a quiz app soon!");
    }

    @FXML
    protected void onChooseQuizButtonClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();

        final FileChooser fileChooser = new FileChooser();
        configureFileChooser(fileChooser);
        fileChooser.setTitle("Open Resource File");
        File file = fileChooser.showOpenDialog(stage);

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            questions = objectMapper.readValue(file, new TypeReference<>() {});
        } catch (IOException e) {
            welcomeText.setText("The questions format is incorrect!");
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            welcomeText.setText("No file uploaded!");
            throw new IllegalArgumentException(e);
        }

        // close the hello stage
        Stage thisStage = (Stage) ((Button)actionEvent.getSource()).getScene().getWindow();
        thisStage.close();

        // load the home stage
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("home.fxml"));
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

    private static void configureFileChooser(
            final FileChooser fileChooser) {
        fileChooser.setTitle("View Questions");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("JSON", "*.json"),
                new FileChooser.ExtensionFilter("TXT", "*.txt")
        );
    }
}