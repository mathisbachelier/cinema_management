package com.example.cinema_management;

import BDD.Query;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import javax.swing.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.EventObject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class AdminController {
    @FXML
    private TextField newUsername;
    @FXML
    private TextField newPassword;
    @FXML
    private Label errorLabel;
    @FXML
    private TextField usernameToDelete;
    @FXML
    public Button deconnection;
    @FXML
    public Label deleteMessage;
    @FXML
    private  TextField FilmTitleAdd;
    @FXML
    private TextField FilmDirectorAdd;
    @FXML
    private TextField FilmDurationAdd;
    @FXML
    private TextArea FilmDescAdd;
    @FXML
    private TextField FilmTitleArchived;
    @FXML
    private Label FilmMessageArchived;
    @FXML
    private Label FilmMessageAdd;
    @FXML
    private TableView<Employee> employeeTable;
    private SortEvent<TableView<List<Employee>>> employees;

    @FXML
    public void OnDeconnection() throws IOException {
        EventObject actionEvent = new EventObject(deconnection);
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close(); // Close the current stage

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        Stage newStage = new Stage();
        newStage.setTitle("CinéGest Log In");
        newStage.setScene(scene);
        newStage.show();

    }

    public void OnAddEmployee() {
        String username = newUsername.getText();

        Query query = new Query();
        ResultSet rs = query.getEmployeeUsernames();
        if (newUsername.getText().isEmpty() || newPassword.getText().isEmpty()) {
            errorLabel.setText("Please fill in both fields.");
            errorLabel.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    errorLabel.setVisible(false);
                });
            });
        } else {
            try {
                while (rs.next()) {
                    String existingUsername = rs.getString("username");
                    if (existingUsername.equals(username)) {
                        System.out.println("Username already in use. Please choose a different username.");
                        errorLabel.setText("Usernane déjà utilisé. Veuillez en prendre un autre.");
                        errorLabel.setVisible(true);
                        CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                            SwingUtilities.invokeLater(() -> {
                                errorLabel.setVisible(false);
                            });
                        });
                        return;
                    }
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            errorLabel.setVisible(false);

            Employee newEmployee = new Employee();

            newEmployee.setUsername(newUsername.getText());
            newEmployee.setPassword(hashPassword(newPassword.getText()));
            int role = 2;
            newEmployee.setRole(role);

            String n = newEmployee.toString();
            System.out.println(n);

            query.AddEmployee(newEmployee);
            errorLabel.setText("Employee added successfully");
            errorLabel.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    errorLabel.setVisible(false);
                });
            });
        }
    }


    private String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder(2 * hash.length);
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to hash password", e);
        }
    }

    public void OnDeleteEmployee() {
        String username = usernameToDelete.getText();
        Query query = new Query();
        boolean isDeleted = query.deleteEmployee(username);

        if (isDeleted) {
            deleteMessage.setText("Successfully deleted");
        } else {
            deleteMessage.setText("Username inexistant");
        }
        deleteMessage.setVisible(true);

        CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
            SwingUtilities.invokeLater(() -> {
                deleteMessage.setVisible(false);
            });
        });

    }

    public void OnAddFilm() {
        Query query = new Query();
        Film film = new Film();
        String title = FilmTitleAdd.getText();
        String director = FilmDirectorAdd.getText();
        String duration = FilmDurationAdd.getText();
        String description = FilmDescAdd.getText();
        if (title.isEmpty() || director.isEmpty() || duration.isEmpty() || description.isEmpty()) {
            FilmMessageAdd.setText("Please fill in all fields.");
            FilmMessageAdd.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    FilmMessageAdd.setVisible(false);
                });
            });
        } else {

        film.setTitle(title);
        film.setDirector(director);
            try {
                int durationInt = Integer.parseInt(duration);
                film.setDuration(durationInt);
                film.setDescription(description);
                boolean isAdded = query.addFilm(film);

                if (isAdded) {
                    FilmMessageAdd.setText("Film added");
                } else {
                    FilmMessageAdd.setText("film already exists");
                }
                FilmMessageAdd.setVisible(true);
                CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                    SwingUtilities.invokeLater(() -> {
                        FilmMessageAdd.setVisible(false);
                    });
                });
            } catch (NumberFormatException e) {
                // Show error message for non-integer duration
                FilmMessageAdd.setText("Duration must be an integer");
                FilmMessageAdd.setVisible(true);
                CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                    SwingUtilities.invokeLater(() -> {
                        FilmMessageAdd.setVisible(false);
                    });
                });
            }
        }
    }
    public void OnArchivedFilm() {
        Query query = new Query();
        Film film = new Film();
        String title = FilmTitleArchived.getText();
        film.setTitle(title);
        boolean isArchived = query.archivedFilm(title);

        if (isArchived) {
            FilmMessageArchived.setText("Film archived");
        } else {
            FilmMessageArchived.setText("Film doesn't exist");
        }
        FilmMessageArchived.setVisible(true);

        CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
            SwingUtilities.invokeLater(() -> {
                FilmMessageArchived.setVisible(false);
            });
        });
    }




}
