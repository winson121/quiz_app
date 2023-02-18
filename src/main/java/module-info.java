module com.winson121.quiz.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires java.desktop;


    requires com.fasterxml.jackson.core;
    requires com.fasterxml.jackson.annotation;
    requires com.fasterxml.jackson.databind;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;

    opens com.winson121.quiz.demo to javafx.fxml;
    exports com.winson121.quiz.demo;
    exports com.winson121.quiz.demo.entity to com.fasterxml.jackson.databind;
}