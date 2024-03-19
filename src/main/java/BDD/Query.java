package BDD;


import com.example.cinema_management.AdminController;
import com.example.cinema_management.Employee;
import com.example.cinema_management.Film;

import java.sql.*;

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

            // Check if the movie with the same title already exists
            String checkQuery = "SELECT title FROM movie WHERE LOWER(title) = LOWER(?)";
            PreparedStatement checkStatement = conn.prepareStatement(checkQuery);
            checkStatement.setString(1, film.getTitle());
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                // Movie with the same title already exists
                System.out.println("Movie with the same title already exists");
                return false;
            } else {
                // If the movie with the same title does not exist, proceed with insertion
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
            e.printStackTrace(); // Print the exception for debugging
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
                return false; // Movie doesn't exist
            }
            isArchived = true;
            return isArchived;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
