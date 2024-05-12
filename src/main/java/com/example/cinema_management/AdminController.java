package com.example.cinema_management;

import BDD.Query;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;


import javax.swing.*;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import javafx.event.EventTarget;

import java.time.format.DateTimeParseException;
import java.util.Date;
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
    private TextField FilmToUnarchive;
    @FXML
    private Label FilmUnarchiveMessage;
    @FXML
    private TextField SceanceFilmTitleAdd;
    @FXML
    private DatePicker SceanceHeureAdd;
    @FXML
    private TextField sceancehouradd;
    @FXML
    private TextField SceanceRoomAdd;
    @FXML
    private TextField RoomNumberAdd;
    @FXML
    private TextField RoomCapacityAdd;
    @FXML
    private Label RoomMessageAdd;
    @FXML
    private Label SceanceMessageAdd;
    @FXML
    private TextField RoomToArchive;
    @FXML
    private Label RoomMessageArchived;
    @FXML
    private TextField RoomToUnarchive;
    @FXML
    private Label RoomMessageUnarchived;
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
            errorLabel.setText("Veuillez remplir tous les champs");
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
            errorLabel.setText("Employer ajouté avec succès");
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
            deleteMessage.setText("Employer supprimé avec succès");
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
            FilmMessageAdd.setText("Veuillez remplir tous les champs");
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
        if (title.isEmpty()) {
            FilmMessageArchived.setText("Please enter a film title");
            FilmMessageArchived.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    FilmMessageArchived.setVisible(false);
                });
            });
            return;
        }
        film.setTitle(title);
        boolean isArchived = query.archivedFilm(title);

        if (isArchived) {
            FilmMessageArchived.setText("Film archived");
        } else {
            FilmMessageArchived.setText("Film not found or error occurred");
        }
        FilmMessageArchived.setVisible(true);

        CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
            SwingUtilities.invokeLater(() -> {
                FilmMessageArchived.setVisible(false);
            });
        });
    }
    public void OnFilmToUnarchive() {
        Query query = new Query();
        Film film = new Film();
        String title = FilmToUnarchive.getText();
        if (title.isEmpty()) {
            FilmUnarchiveMessage.setText("Please enter a film title");
            FilmUnarchiveMessage.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    FilmUnarchiveMessage.setVisible(false);
                });
            });
            return;
        }
        film.setTitle(title);
        boolean isArchived = query.UnarchivedFilm(title);

        if (isArchived) {
            FilmUnarchiveMessage.setText("Film Unarchived");
        } else {
            FilmUnarchiveMessage.setText("Film not found or error occurred");
        }
        FilmUnarchiveMessage.setVisible(true);

        CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
            SwingUtilities.invokeLater(() -> {
                FilmUnarchiveMessage.setVisible(false);
            });
        });
    }

    public void OnAddSceance() throws ParseException {
        Query query = new Query();
        Screening sceance = new Screening();
        String title = SceanceFilmTitleAdd.getText();
        LocalDate date = SceanceHeureAdd.getValue();
        String hour = sceancehouradd.getText();
        String room = SceanceRoomAdd.getText();



        if (title.isEmpty() || date == null || room.isEmpty() || hour.isEmpty()) {

            SceanceMessageAdd.setText("veuillez remplir tout les champs");
            SceanceMessageAdd.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    SceanceMessageAdd.setVisible(false);
                });
            });
            return;
        }
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime time = LocalTime.parse(hour, formatter);
            String hourFormat = time.toString();
        } catch (DateTimeParseException e) {
            SceanceMessageAdd.setText("L'heure rentrée n'est pas valide");
            SceanceMessageAdd.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    SceanceMessageAdd.setVisible(false);
                });
            });
            return;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime time = LocalTime.parse(hour, formatter);
        String hourFormat = time.toString();

        sceance.setMovieId(Query.getMovieId(title));
        sceance.setRoomNumber(Query.getRoomId(room));
        sceance.setStartTime(date.toString());
        sceance.setStartHour(hourFormat);
        sceance.setDuration(Query.getDuration(title));

        if (query.getFilmIsArchived(sceance.getMovieId())) {

            SceanceMessageAdd.setText("can't add screening, Film is archived");
            SceanceMessageAdd.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    SceanceMessageAdd.setVisible(false);
                });
            });
            return;
        }
        if (query.getRoomIsArchived(sceance.getRoomNumber())) {

            SceanceMessageAdd.setText("can't add screening, room is archived");
            SceanceMessageAdd.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    SceanceMessageAdd.setVisible(false);
                });
            });
            return;
        }

        boolean isAdded = query.addScreening(sceance);

        if (isAdded) {

            SceanceMessageAdd.setText("sceance added");
            SceanceMessageAdd.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    SceanceMessageAdd.setVisible(false);
                });
            });
        }
        else {

            SceanceMessageAdd.setText("sceance existe déjà ou informations erronées");
            SceanceMessageAdd.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    SceanceMessageAdd.setVisible(false);
                });
            });
        }
    }

    public void OnAddRoom() {
        Query query = new Query();
        Room room = new Room();
        String roomNumber = RoomNumberAdd.getText();
        String capacity = RoomCapacityAdd.getText();

        if (roomNumber.isEmpty() || capacity.isEmpty()) {
            RoomMessageAdd.setText("veuillez remplir tout les champs");
            RoomMessageAdd.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    RoomMessageAdd.setVisible(false);
                });
            });
            return;
        }

        room.setRoomNumber(roomNumber);
        room.setCapacity(Integer.parseInt(capacity));
        boolean roomAdded = query.addRoom(room);

        if (roomAdded) {

            RoomMessageAdd.setText("La salle a été ajoutée");
            RoomMessageAdd.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    RoomMessageAdd.setVisible(false);
                });
            });
        } else {
            RoomMessageAdd.setText("La salle existe déjà ou informations erronées");
            RoomMessageAdd.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    RoomMessageAdd.setVisible(false);
                });
            });
        }
    }
    public void onRoomToArchive() {
        Query query = new Query();
        String roomNumber = RoomToArchive.getText();

        if (roomNumber.isEmpty()) {
            RoomMessageArchived.setText("Veuillez entrer le numero de la salle");
            RoomMessageArchived.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    RoomMessageArchived.setVisible(false);
                });
            });
            return;
        }

        boolean roomArchived = query.roomToArchive(roomNumber);

        if (roomArchived) {

            RoomMessageArchived.setText("Salle archivée");
            RoomMessageArchived.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    RoomMessageArchived.setVisible(false);
                });
            });
        } else {
            RoomMessageArchived.setText("La salle n'existe pas ou informations erronées");
            RoomMessageArchived.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    RoomMessageArchived.setVisible(false);
                });
            });
        }
    }
    public void onRoomToUnarchive() {
        Query query = new Query();
        String roomNumber = RoomToUnarchive.getText();

        if (roomNumber.isEmpty()) {
            RoomMessageUnarchived.setText("Veuillez entrer le numero de la salle");
            RoomMessageUnarchived.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    RoomMessageUnarchived.setVisible(false);
                });
            });
            return;
        }

        boolean roomUnarchived = query.roomToUnarchive(roomNumber);

        if (roomUnarchived) {

            RoomMessageUnarchived.setText("Salle non archivée");
            RoomMessageUnarchived.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    RoomMessageUnarchived.setVisible(false);
                });
            });
        } else {
            RoomMessageUnarchived.setText("La salle n'existe pas ou informations erronées");
            RoomMessageUnarchived.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    RoomMessageUnarchived.setVisible(false);
                });
            });
        }
    }




}
