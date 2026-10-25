package com.example.covid_19_system;


import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
   @FXML
   public Button admins;
   @FXML
   public Button citizens;
   @Override
   public void initialize(URL location, ResourceBundle resources) {

   }

   public void admins(ActionEvent actionEvent) throws IOException {
      Parent root = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("Admin/AdminLoginPage.fxml")));
      Stage window = (Stage) admins.getScene().getWindow();
      window.setTitle("Admin Login Page");
      window.setResizable(false);
      window.setScene(new Scene(root, 1550, 800));
   }

   public void citizens(ActionEvent actionEvent) throws IOException {
      Parent root = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("User/UserLoginPage.fxml")));
      Stage window = (Stage) citizens.getScene().getWindow();
      window.setTitle("User Login Page");
      window.setResizable(false);
      window.setScene(new Scene(root, 1550, 800));
   }
}