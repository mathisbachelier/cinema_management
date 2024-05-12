package com.example.cinema_management;
import BDD.Query;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.swing.*;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.EventObject;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public class IndexController {


    @FXML
    private Button deconnection;
    @FXML
    private TextField FirstnameClient;
    @FXML
    private TextField LastnameClient;
    @FXML
    private TextField EmailClient;
    @FXML
    private TextField postalClient;
    @FXML
    private Label errorClientAddLabel;
    @FXML
    private Tab IndexAccueil;
    @FXML
    private TableView<Screening> screeningTableView;
    @FXML
    private TableColumn<Screening, String> movieTitleColumn;
    @FXML
    private TableColumn<Screening, String> startTimeColumn;
    @FXML
    private TableColumn<Screening, Integer> durationColumn;
    @FXML
    private TableColumn<Screening, String> startHourColumn;
    @FXML
    private TableColumn<Screening, Void> actionColumn;
    @FXML
    private Tab reservationTab;
    @FXML
    private TabPane indexTabPane;

    private ObservableList<Screening> screenings;

    public void initialize() {
        Query query = new Query();

        movieTitleColumn.setCellValueFactory(new PropertyValueFactory<>("movieTitle"));
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        durationColumn.setCellValueFactory(new PropertyValueFactory<>("durationString"));
        startHourColumn.setCellValueFactory(new PropertyValueFactory<>("startHour"));

        IndexAccueil.setOnSelectionChanged(event -> {
            if (IndexAccueil.isSelected()) {
                List<Screening> allScreenings = query.getAllScreenings();
                screeningTableView.getItems().setAll(allScreenings);
            }
        });

        actionColumn.setCellFactory(param -> new TableCell<Screening, Void>() {
            private final Button reserveButton = new Button("Réserver");

            {
                reserveButton.setOnAction(event -> {
                    Screening selectedScreening = getTableView().getItems().get(getIndex());
                    int screeningId = selectedScreening.getId();


                    List<Seat> availableSeats = query.getAvailableSeats(screeningId);


                    VBox container = new VBox(10);
                    container.getChildren().addAll(
                            new Label("Movie Title: " + selectedScreening.getMovieTitle()),
                            new Label("Start Time: " + selectedScreening.getStartTime()),
                            new Label("Start Hour: " + selectedScreening.getStartHour()),
                            new Label("Duration: " + selectedScreening.getDurationString()),
                            new Label("Screening ID: " + screeningId),
                            new Label("Seats Available:")
                    );

                    GridPane seatsGrid = new GridPane();
                    seatsGrid.setHgap(5);
                    seatsGrid.setVgap(5);

                    List<Seat> AvailableSeats = query.getSeatsByScreeningId(screeningId);

                    int row = 0, column = 0;
                    for (Seat seat : AvailableSeats) {
                        Button seatButton = new Button("Seat " + seat.getSeatNumber());
                        if (availableSeats.contains(seat)) {

                            seatButton.setStyle("-fx-background-color: green;");
                            seatButton.setDisable(false);

                            seatButton.setOnAction(e -> {

                                System.out.println("Seat " + seat.getSeatNumber() + " reserved.");
                            });
                        } else {

                            seatButton.setStyle("-fx-background-color: red;");
                            seatButton.setDisable(true);
                        }
                        seatButton.setOnAction(e -> {

                            container.getChildren().removeIf(node -> node instanceof VBox);


                            VBox emailLayout = new VBox(10);
                            emailLayout.setAlignment(Pos.CENTER);
                            String seatNumber = String.valueOf(seat.getSeatNumber());
                            int seatId = seat.getSeatId();
                            System.out.println(seatNumber);
                            System.out.println(seatId);

                            Label emailLabel = new Label("Entrez l'email du client pour reserver le siège " + seatNumber + ":");
                            TextField emailTextField = new TextField();
                            emailTextField.setPromptText("Email");


                            Label messageLabel = new Label();
                            messageLabel.setVisible(false);
                            messageLabel.setTextFill(Color.RED);

                            Button reserveButton = new Button("Reserve");

                            emailLayout.getChildren().addAll(emailLabel, emailTextField, messageLabel, reserveButton);

                            container.getChildren().add(emailLayout);

                            reserveButton.setOnAction(ev -> {
                                String email = emailTextField.getText().trim();
                                messageLabel.setVisible(false);

                                if (email.isEmpty()) {

                                    messageLabel.setText("Veuillez remplir tous les champs.");
                                    messageLabel.setVisible(true);
                                } else {

                                    boolean emailExists = query.checkEmailExists(email);
                                    if (!emailExists) {

                                        messageLabel.setText("L'email n'existe pas ou est incorrecte.");
                                        messageLabel.setVisible(true);
                                    } else {

                                        Reservation reservation = new Reservation();

                                        reservation.setClientEmail(email);
                                        reservation.setSeatId(seatId);
                                        reservation.setScreeningId(screeningId);
                                        reservation.setReservationTime(LocalDateTime.now());
                                        query.MakeReservation(reservation);


                                        container.getChildren().remove(emailLayout);
                                    }
                                }
                            });
                        });

                        seatsGrid.add(seatButton, column, row);

                        column++;
                        if (column >= 10) {
                            column = 0;
                            row++;
                        }
                    }


                    container.getChildren().add(seatsGrid);


                    reservationTab.setContent(container);
                    indexTabPane.getSelectionModel().select(reservationTab);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                setGraphic(empty ? null : reserveButton);
            }
        });
    }

    public void OnDeconnection() throws IOException {
        EventObject actionEvent = new EventObject(deconnection);
        Node source = (Node) actionEvent.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("Login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1200, 800);
        Stage newStage = new Stage();
        newStage.setTitle("CinéGest Log In");
        newStage.setScene(scene);
        newStage.show();

    }

    public void OnAddClient() throws SQLException {
        Query query = new Query();
        String Firstname = FirstnameClient.getText();
        String Lastname = LastnameClient.getText();
        String email = EmailClient.getText();
        String code_postal = postalClient.getText();


        if (Firstname.isEmpty() || Lastname.isEmpty() || email.isEmpty() || code_postal.isEmpty() ) {
            errorClientAddLabel.setText("Veuillez remplir tous les champs");
            errorClientAddLabel.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    errorClientAddLabel.setVisible(false);
                });
            });
            return;

        }
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$")) {
            errorClientAddLabel.setText("L'adresse email n'est pas valide");
            errorClientAddLabel.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    errorClientAddLabel.setVisible(false);
                });
            });
            return;
        }
        if (!code_postal.matches("\\d+")) {
            errorClientAddLabel.setText("Le code postal n'est pas bon");
            errorClientAddLabel.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    errorClientAddLabel.setVisible(false);
                });
            });
            return;
        }


        Client client = new Client();

        client.setFirstname(Firstname);
        client.setLastname(Lastname);
        client.setEmail(email);
        client.setCode_postal(code_postal);

        boolean AddClient = query.AddClient(client);
        if (AddClient) {
            errorClientAddLabel.setText("Client ajouté !");
            errorClientAddLabel.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    errorClientAddLabel.setVisible(false);
                });
            });

        }else{
            errorClientAddLabel.setText("l'email est déja utilisé");
            errorClientAddLabel.setVisible(true);
            CompletableFuture.delayedExecutor(2, TimeUnit.SECONDS).execute(() -> {
                SwingUtilities.invokeLater(() -> {
                    errorClientAddLabel.setVisible(false);
                });
            });

        }
    }




}
