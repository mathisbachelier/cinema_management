package BDD;


import com.example.cinema_management.*;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public class Query {




    public ResultSet getEmployee() {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management","root","");
            String query = "SELECT * FROM `employee`";
            rs = conn.createStatement().executeQuery(query);



            return rs;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void AddEmployee(Employee employee) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management","root","");
            String query = "INSERT INTO employee (username, password, role) VALUES (?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, employee.getUsername());
            statement.setString(2, employee.getPassword());
            statement.setInt(3, employee.getRole());
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public ResultSet getEmployeeUsernames() {
        Connection conn = null;
        ResultSet rs = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management","root","");
            String query = "SELECT username FROM employee";
            rs = conn.createStatement().executeQuery(query);
            return rs;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean deleteEmployee(String username) {
        boolean isDeleted = false;

        try {
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");
            String query = "DELETE FROM employee WHERE username = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, username);
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected > 0) {
                isDeleted = true;
            }
        } catch (SQLException e) {
            e.printStackTrace(); 
        }

        return isDeleted;
    }
    public boolean addFilm(Film film) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");


            String checkQuery = "SELECT title FROM movie WHERE LOWER(title) = LOWER(?)";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setString(1, film.getTitle());
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {

                System.out.println("Movie with the same title already exists");
                return false;
            } else {

                String insertQuery = "INSERT INTO movie (title, director, description, duration, is_archived) VALUES (?, ?, ?, ?, ?)";
                PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
                insertStatement.setString(1, film.getTitle());
                insertStatement.setString(2, film.getDirector());
                insertStatement.setString(3, film.getDescription());
                insertStatement.setInt(4, film.getDuration());
                insertStatement.setInt(5, 0);
                insertStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean archivedFilm(String title){
        Connection conn = null;
        boolean isArchived = false;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management","root","");
            String query = "UPDATE movie SET is_archived = 1 WHERE title = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, title);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }
            isArchived = true;
            return isArchived;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean UnarchivedFilm(String title){
        Connection conn = null;
        boolean isUnarchived = false;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management","root","");
            String query = "UPDATE movie SET is_archived = 0 WHERE title = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, title);
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected == 0) {
                return false;
            }
            isUnarchived = true;
            return isUnarchived;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getMovieId(String title){
        Connection conn = null;
        int movieId = 0;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management","root","");
            String query = "SELECT id FROM movie WHERE title = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, title);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                movieId = rs.getInt("id");
            }
            return movieId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public  String getMovieById(int id){
        Connection conn = null;
        String movie = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management","root","");
            String query = "SELECT title FROM movie WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                movie = rs.getString("title");
            }
            return movie;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean getFilmIsArchived(int id) {

        Connection conn = null;
        boolean isArchived = false;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");

            String query = "SELECT is_archived FROM movie WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                isArchived = resultSet.getBoolean("is_archived");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving archived status: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }

        return isArchived;
    }
    public static int getRoomId(String roomNumber) {
        Connection conn = null;
        int roomId = 0;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management","root","");
            String query = "SELECT room_id FROM room WHERE room_number = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, roomNumber);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                roomId = rs.getInt("room_id");
            }
            return roomId;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public static int getDuration(String title) {
        Connection conn = null;
        int duration = 0;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management","root","");
            String query = "SELECT duration FROM movie WHERE title = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, title);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                duration = rs.getInt("duration");
            }
            return duration;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public boolean addScreening(Screening screening) throws ParseException {
        Connection conn = null;
        boolean added = false;
        PreparedStatement stmt = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management","root","");

            String checkQuery = "SELECT * FROM screening WHERE film_id = ? AND room_id = ? AND start_time = ? AND duration = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setInt(1, screening.getMovieId());
            checkStatement.setInt(2, screening.getRoomNumber());
            checkStatement.setString(3, screening.getStartTime());
            checkStatement.setInt(4, screening.getDuration());
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Screening already exists");
                return false;
            } else {
                String insertQuery = "INSERT INTO screening (film_id, room_id, start_time, start_hour, duration, available_seats, is_reserved, is_archived) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                stmt = conn.prepareStatement(insertQuery);

                stmt.setInt(1, screening.getMovieId());
                stmt.setInt(2, screening.getRoomNumber());
                stmt.setString(3, screening.getStartTime());
                stmt.setString(4, screening.getStartHour());
                stmt.setInt(5, screening.getDuration());
                stmt.setInt(6, 50);
                stmt.setInt(7, 0);
                stmt.setInt(8, 0);

                // Execute the query
                stmt.executeUpdate();
                System.out.println("Screening added successfully!");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding screening: " + e.getMessage());
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
        return added;
    }

    public boolean addRoom(Room room) {
        Connection conn = null;
        boolean added = false;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");

            String checkQuery = "SELECT room_number FROM room WHERE room_number = ?";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setString(1, room.getRoomNumber());
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Room with the same room number already exists");
                return false;
            } else {
                String insertQuery = "INSERT INTO room (room_number, capacity, is_archived) VALUES (?, ?, ?)";
                PreparedStatement insertStatement = conn.prepareStatement(insertQuery);
                insertStatement.setString(1, room.getRoomNumber());
                insertStatement.setInt(2, room.getCapacity());
                insertStatement.setInt(3, 0);
                insertStatement.executeUpdate();
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Error adding room: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }

        return added;

    }
    public boolean getRoomIsArchived(int id) {

        Connection conn = null;
        boolean isArchived = false;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");

            String query = "SELECT is_archived FROM room WHERE room_id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                isArchived = resultSet.getBoolean("is_archived");
            }
        } catch (SQLException e) {
            System.err.println("Error retrieving archived status: " + e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }

        return isArchived;
    }
    public boolean roomToArchive(String roomNumber) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");

            String updateQuery = "UPDATE room SET is_archived = 1 WHERE room_number = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, roomNumber);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            } else {
                System.out.println("Room with room number " + roomNumber + " not found");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error archiving room: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }
    public boolean roomToUnarchive(String roomNumber) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");

            String updateQuery = "UPDATE room SET is_archived = 0 WHERE room_number = ?";
            PreparedStatement updateStatement = conn.prepareStatement(updateQuery);
            updateStatement.setString(1, roomNumber);

            int rowsAffected = updateStatement.executeUpdate();

            if (rowsAffected > 0) {
                return true;
            } else {
                System.out.println("Room with room number " + roomNumber + " not found");
                return false;
            }
        } catch (SQLException e) {
            System.err.println("Error archiving room: " + e.getMessage());
            return false;
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                System.err.println("Error closing connection: " + e.getMessage());
            }
        }
    }



    public boolean AddClient(Client client) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management","root","");
            String query = "SELECT * FROM client WHERE email = ?";
            PreparedStatement checkStatement = conn.prepareStatement(query);
            checkStatement.setString(1, client.getEmail());
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {

                return false;
            } else {
                String insertQuery = "INSERT INTO client (firstname, lastname, email, code_postal) VALUES (?, ?, ?, ?)";
                PreparedStatement insertStatement = conn.prepareStatement(insertQuery);

                insertStatement.setString(1, client.getFirstname());
                insertStatement.setString(2, client.getLastname());
                insertStatement.setString(3, client.getEmail());
                insertStatement.setString(4, client.getCode_postal());
                insertStatement.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public String convertMinutesToHours(int durationInMinutes) {
        if (durationInMinutes < 0) {
            throw new IllegalArgumentException("La durée ne peut pas être négative.");
        }

        int hours = durationInMinutes / 60;
        int minutes = durationInMinutes % 60;

        String formattedTime = String.format("%2dh%02d", hours, minutes);
        return formattedTime;
    }

    public List<Screening> getAllScreenings() {
        List<Screening> screenings = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "")) {
            String query = "SELECT * FROM screening where is_archived = 0";
            PreparedStatement statement = conn.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int movieTitle = resultSet.getInt("film_id");
                String titreFilm = getMovieById(movieTitle);
                String date = resultSet.getString("start_time");
                int time = resultSet.getInt("duration");
                String timeString = convertMinutesToHours(time);
                String startHour = resultSet.getString("start_hour");

                Screening screening = new Screening();

                screening.setId(id);
                screening.setMovieTitle(titreFilm);
                screening.setStartTime(date);
                screening.setDurationString(timeString);
                screening.setStartHour(startHour);
                screenings.add(screening);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return screenings;
    }

    public boolean isSeatBooked(int screeningId, int seatId) {
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");

            String query = "SELECT * FROM bookings WHERE screening_id = ? AND seat_id = ?";
            PreparedStatement checkStatement = conn.prepareStatement(query);
            checkStatement.setInt(1, screeningId);
            checkStatement.setInt(2, seatId);
            ResultSet resultSet = checkStatement.executeQuery();


            if (resultSet.next() && resultSet.getInt(1) > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Seat> getAvailableSeats(int screeningId) {
        List<Seat> availableSeats = new ArrayList<>();
        Connection conn = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");

            String query = "SELECT * FROM seat WHERE seat_id NOT IN (SELECT seat_id FROM reservation WHERE screening_id = ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, screeningId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Seat seat = new Seat();
                seat.setSeatId(resultSet.getInt("seat_id"));
                seat.setRoomId(resultSet.getInt("room_id"));
                seat.setSeatNumber(resultSet.getInt("seat_number"));
                availableSeats.add(seat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


        return availableSeats;
    }
    public List<Seat> getSeatsByScreeningId(int screeningId) {
        List<Seat> seats = new ArrayList<>();
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");
            String query = "SELECT * FROM seat WHERE room_id IN (SELECT room_id FROM screening WHERE id = ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, screeningId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                Seat seat = new Seat();
                seat.setSeatId(resultSet.getInt("seat_id"));
                seat.setRoomId(resultSet.getInt("room_id"));
                seat.setSeatNumber(resultSet.getInt("seat_number"));
                seats.add(seat);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return seats;
    }

    public boolean MakeReservation(Reservation reservation) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");
            String query = "INSERT INTO reservation (client_email, screening_id, seat_id, reservation_time) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, reservation.getClientEmail());
            statement.setInt(2, reservation.getScreeningId());
            statement.setInt(3, reservation.getSeatId());
            statement.setString(4, String.valueOf(reservation.getReservationTime()));
            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean checkEmailExists(String email) {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost/cinema_management", "root", "");
            String query = "SELECT * FROM client WHERE email = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setString(1, email);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
