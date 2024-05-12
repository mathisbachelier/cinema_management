package com.example.cinema_management;

import BDD.Query;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MainController {
    @FXML
    private TextField username;
    @FXML
    private TextField password;


    @FXML
    public Boolean login() throws SQLException {

        ResultSet rl = new Query().getEmployee();
        boolean validLogin = false;

        // Replace with actual password input
        while (rl.next()) {
            String login_username = rl.getString("username");
            String login_password = rl.getString("password");
            int login_id = rl.getInt("id");

            if (login_username.equals(username.getText()) && login_password.equals(password.getText())) {
                if (login_id == 1) {
                    validLogin = true;
                    Stage stage = (Stage) username.getScene().getWindow();
                    FXMLLoader fxmlAdmin = new FXMLLoader(getClass().getResource("admin.fxml"));
                    try {
                        Scene scene = new Scene(fxmlAdmin.load(), 1200, 800);
                        stage.setScene(scene);
                        stage.setTitle("CinéGest");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    validLogin = true;

                    Stage stage = (Stage) username.getScene().getWindow();
                    FXMLLoader fxmlIndex = new FXMLLoader(getClass().getResource("index.fxml"));
                    try {
                        Scene scene = new Scene(fxmlIndex.load(), 1200, 800);
                        stage.setScene(scene);
                        stage.setTitle("CinéGest");


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }if(!validLogin) {
            System.out.println("didn't work");
        }

        return validLogin;
    }

}