import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DatabaseConnector {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/mydatabase";
    private static final String DB_USER = "username";
    private static final String DB_PASSWORD = "password";

    public static void main(String[] args) {
        String name = "John Doe"; // Example name
        String email = "johndoe@example.com"; // Example email

        try (Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // Prepare the SQL query
            String sql = "INSERT INTO users (name, email) VALUES (?, ?)";
            
            // Create a PreparedStatement with the query
            PreparedStatement statement = connection.prepareStatement(sql);
            
            // Set the values for the placeholders in the query
            statement.setString(1, name);
            statement.setString(2, email);
            
            // Execute the update and insert the data into the database
            statement.executeUpdate();
            
            System.out.println("Data stored successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
