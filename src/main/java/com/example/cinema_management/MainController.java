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
        Boolean check = false;

        // Replace with actual password input
        while (rl.next()) {
            String login_username = rl.getString("username");
            String login_password = rl.getString("password");
            int login_id = rl.getInt("employee_id");
            // Check if the username and password are valid
            if (login_username.equals(username.getText())) {
                if (login_password.equals(password.getText())) {
                    // Successful login, add your logic here
                    System.out.println("worked!");
                    check = true;
                    Stage stage = (Stage) username.getScene().getWindow();
                    FXMLLoader fxmlIndex = new FXMLLoader(getClass().getResource("index.fxml"));
                    try {
                        Scene scene = new Scene(fxmlIndex.load(), 800, 600);
                        stage.setScene(scene);
                        stage.setTitle("CinéGest");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return check;

                } else {
                    System.out.println("didn't work");
                    return check;
                }
            } else {
                System.out.println("didn't work");
                return check;


            }
        }
        return check;

    }
    public String getUsername() {
        return username.getText();
    }

}