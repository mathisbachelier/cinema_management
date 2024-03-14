package BDD;


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
}
